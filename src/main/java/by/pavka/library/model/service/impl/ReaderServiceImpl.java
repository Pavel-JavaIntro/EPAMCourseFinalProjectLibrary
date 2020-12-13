package by.pavka.library.model.service.impl;

import by.pavka.library.entity.LibraryEntityException;
import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.criteria.EntityField;
import by.pavka.library.entity.impl.Author;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.entity.impl.Edition;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.model.DBConnectorPool;
import by.pavka.library.model.dao.*;
import by.pavka.library.model.service.ReaderService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.util.ConstantManager;
import by.pavka.library.model.util.TableEntityMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the relevant interface
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ReaderServiceImpl implements ReaderService {

  ReaderServiceImpl() {}

  @Override
  public List<Edition> findEditions(String title, String author) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      ManyToManyDao<Edition, Author> dao = LibraryDaoFactory.getManyToManyDao(connector);
      if (title.isEmpty() && author.isEmpty()) {
        return dao.read();
      }
      List<Edition> titleEditions = null;
      List<Edition> authorEditions = null;

      if (!title.isEmpty()) {
        Criteria criteriaT = new Criteria();
        EntityField<String> titleField = new EntityField<>(Edition.TITLE);
        titleField.setValue(title);
        criteriaT.addConstraint(titleField);
        titleEditions = dao.read(criteriaT, false);
        if (titleEditions.isEmpty()) {
          return new ArrayList<>();
        }
      }

      List<Author> authorList = null;
      if (!author.isEmpty()) {
        try (LibraryDao<Author> authorDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.AUTHOR, connector)) {
          Criteria criteriaA = new Criteria();
          EntityField<String> authorField = new EntityField<>(Author.SURNAME);
          authorField.setValue(author);
          criteriaA.addConstraint(authorField);
          authorList = authorDao.read(criteriaA, false);
          if (authorList.isEmpty()) {
            return new ArrayList<>();
          }
          Set<Integer> editionIds = new HashSet<>();
          for (Author a : authorList) {
            editionIds.addAll(dao.getFirst(a.getId()));
          }
          if (editionIds.isEmpty()) {
            return new ArrayList<>();
          }
          authorEditions = new ArrayList<>();
          for (int i : editionIds) {
            authorEditions.add(dao.get(i));
          }
          if (authorEditions.isEmpty()) {
            return new ArrayList<>();
          }
        }
      }
      List<Edition> finalEditions = null;
      if (titleEditions == null) {
        finalEditions = authorEditions;
      }
      if (authorEditions == null) {
        finalEditions = titleEditions;
      }
      if (titleEditions != null && authorEditions != null) {
        finalEditions = new ArrayList<>();
        for (Edition edition : titleEditions) {
          if (authorEditions.contains(edition)) {
            finalEditions.add(edition);
          }
        }
        if (finalEditions.isEmpty()) {
          return new ArrayList<>();
        }
      }
      return finalEditions;
    } catch (DaoException e) {
      throw new ServiceException("Cannot find edition", e);
    }
  }

  @Override
  public void bindAuthors(EditionInfo info) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      UtilService utilService = LibServiceFactory.getUtilService();
      utilService.bindAuthors(info, connector);
    } catch (DaoException | LibraryEntityException e) {
      throw new ServiceException("Cannot find relevant books", e);
    }
  }

  @Override
  public void bindBookAndLocation(EditionInfo info) throws ServiceException {
    try {
      Book book = findFreeBookByEdition(info.getEdition().getId());
      info.setBook(book);
      if (book != null) {
        int locationId = (int)book.fieldForName(Book.LOCATION_ID).getValue();
        info.setLocationId(locationId);
        int standardLocationId = (int)book.fieldForName(Book.STANDARD_LOCATION_ID).getValue();
        info.setStandardLocationId(standardLocationId);
      }
    } catch (ServiceException | LibraryEntityException e) {
      throw new ServiceException("Cannot find relevant books", e);
    }
  }

  private Book findFreeBookByEdition(int id) throws ServiceException {
    Book book = null;
    try {
      UtilService utilService = LibServiceFactory.getUtilService();
      List<Book> result = utilService.findBooksByEdition(id);
      for (Book b : result) {
        System.out.println(b.fieldForName(Book.RESERVED).getValue());
        if (!b.fieldForName(Book.LOCATION_ID)
            .getValue()
            .equals(ConstantManager.LOCATION_DECOMMISSIONED)
            && !b.fieldForName(Book.LOCATION_ID).getValue().equals(ConstantManager.LOCATION_ON_HAND)
            && !b.fieldForName(Book.RESERVED).getValue().equals(ConstantManager.RESERVED)
            && !b.fieldForName(Book.RESERVED).getValue().equals(ConstantManager.PREPARED)) {
          book = b;
          break;
        }
      }
    } catch (LibraryEntityException e) {
      throw new ServiceException("Cannot find books", e);
    }
    return book;
  }

  @Override
  public void orderBook(BookOrder bookOrder) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      int userId = bookOrder.getUserId();
      Set<EditionInfo> editionInfoSet = new HashSet<>(bookOrder.getEditionInfoSet());
      for (EditionInfo editionInfo : editionInfoSet) {
        Book book = editionInfo.getBook();
        int bookId = book.getId();
        EntityField<Integer> userField = new EntityField<>(Book.READER_ID);
        userField.setValue(userId);
        EntityField<Integer> reserveField = new EntityField<>(Book.RESERVED);
        reserveField.setValue(ConstantManager.RESERVED);
        System.out.println("EDITION LOCATION " + editionInfo.getLocationId());
        EntityField<Integer> locationField = new EntityField<>(Book.LOCATION_ID);
        locationField.setValue(editionInfo.getLocationId());
        book.fieldForName(Book.LOCATION_ID).setValue(editionInfo.getLocationId());
        try {
          connector.suspendAutoCommit();
          Book dBook = bookDao.get(bookId);
          if (dBook.fieldForName(Book.RESERVED).getValue().equals(ConstantManager.NOT_RESERVED)) {
            bookDao.update(bookId, userField);
            bookDao.update(bookId, reserveField);
            bookDao.update(bookId, locationField);
            System.out.println("DIRECTLY: " + bookDao.get(bookId).fieldForName(Book.LOCATION_ID).getValue());
          } else {
            int editionId = editionInfo.getEdition().getId();
            Book nBook = findFreeBookByEdition(editionId);
            if (nBook != null) {
              editionInfo.setBook(nBook);
              bookId = nBook.getId();
              bookDao.update(bookId, userField);
              bookDao.update(bookId, reserveField);
              bookDao.update(bookId, locationField);
            } else {
              bookOrder.passBook(editionInfo);
            }
          }
          connector.commit();
        } catch (SQLException | LibraryEntityException throwables) {
          connector.rollback();
        } finally {
          connector.restoreAutoCommit();
        }
      }
    } catch (DaoException | SQLException | LibraryEntityException e) {
      throw new ServiceException("Cannot order book", e);
    }
  }
}
