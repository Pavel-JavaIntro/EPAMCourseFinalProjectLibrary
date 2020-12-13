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
import by.pavka.library.model.service.ProcessBookService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.util.ConstantManager;
import by.pavka.library.model.util.TableEntityMapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the relevant interface
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ProcessBookServiceImpl implements ProcessBookService {

  ProcessBookServiceImpl() {}

  @Override
  public int editionIdByCode(String code) throws ServiceException {
    int editionId = 0;
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Edition> editionDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.EDITION, connector);
      EntityField<String> field = new EntityField<>(Edition.STANDARD_NUMBER);
      field.setValue(code);
      Criteria criteria = new Criteria();
      criteria.addConstraint(field);
      List<Edition> editions = editionDao.read(criteria, true);
      if (!editions.isEmpty()) {
        editionId = editions.get(0).getId();
      }
    } catch (DaoException e) {
      throw new ServiceException("Cannot identify edition standard number", e);
    }
    return editionId;
  }

  @Override
  public void addBook(Book book) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      bookDao.add(book);
    } catch (DaoException e) {
      throw new ServiceException("Cannot add a book", e);
    }
  }

  @Override
  public int addEdition(Edition edition) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Edition> editionDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.EDITION, connector);
      return editionDao.add(edition);
    } catch (DaoException e) {
      throw new ServiceException("Cannot add an edition code", e);
    }
  }

  @Override
  public int addAuthor(Author author) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Author> authorDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.AUTHOR, connector);
      return authorDao.add(author);
    } catch (DaoException e) {
      throw new ServiceException("Cannot add an author", e);
    }
  }

  @Override
  public void bindEditionAndAuthors(int editionId, int[] authorsId) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      ManyToManyDao<Edition, Author> dao = LibraryDaoFactory.getManyToManyDao(connector);
      for (int id : authorsId) {
        if (id != 0) {
          dao.bind(editionId, id);
        }
      }
    } catch (DaoException e) {
      throw new ServiceException("Cannot bind editions and authors", e);
    }
  }

  @Override
  public List<Author> findAuthors(Author author) throws ServiceException {
    List<Author> result = new ArrayList<>();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Author> authorDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.AUTHOR, connector);
      if (author != null) {
        Criteria criteria = new Criteria();
        EntityField<String> surname = new EntityField<>(Author.SURNAME);
        surname.setValue((String)author.fieldForName(Author.SURNAME).getValue());
        criteria.addConstraint(surname);
        String fname = (String)author.fieldForName(Author.FIRST_NAME).getValue();
        EntityField<String> firstname = null;
        if (fname != null) {
          firstname = new EntityField<>(Author.FIRST_NAME);
          firstname.setValue(fname);
          criteria.addConstraint(firstname);
        }
        String sname = (String)author.fieldForName(Author.SECOND_NAME).getValue();
        if (sname != null) {
          EntityField<String> secondname = new EntityField<>(Author.SECOND_NAME);
          secondname.setValue(sname);
          criteria.addConstraint(secondname);
        }
        return authorDao.read(criteria, true);
      }
    } catch (DaoException | LibraryEntityException e) {
      throw new ServiceException("Cannot find authors", e);
    }
    return result;
  }

  @Override
  public void decommissionBook(int bookId) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      EntityField<Integer> field = new EntityField<>(Book.LOCATION_ID);
      field.setValue(ConstantManager.LOCATION_DECOMMISSIONED);
      EntityField<Integer> reserved = new EntityField<>(Book.RESERVED);
      reserved.setValue(ConstantManager.DECOM);
      try {
        connector.suspendAutoCommit();
        bookDao.update(bookId, field);
        bookDao.update(bookId, reserved);
      } catch (SQLException throwables) {
        connector.rollback();
      } finally {
        connector.restoreAutoCommit();
      }
    } catch (DaoException | SQLException e) {
      throw new ServiceException("Cannot decommission a book", e);
    }
  }

  @Override
  public void prepareOrder(BookOrder bookOrder) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      for (EditionInfo editionInfo : bookOrder.getEditionInfoSet()) {
        Book book = editionInfo.getBook();
        int bookId = book.getId();
        EntityField<Integer> reserveField = new EntityField<>(Book.RESERVED);
        reserveField.setValue(ConstantManager.PREPARED);
        try {
          connector.suspendAutoCommit();
          bookDao.update(bookId, reserveField);
          connector.commit();
        } catch (SQLException throwables) {
          connector.rollback();
        } finally {
          connector.restoreAutoCommit();
        }
      }
    } catch (DaoException | SQLException e) {
      throw new ServiceException("Cannot prepare book", e);
    }
  }

  @Override
  public void denyOrder(BookOrder bookOrder) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      for (EditionInfo editionInfo : bookOrder.getEditionInfoSet()) {
        Book book = editionInfo.getBook();
        int bookId = book.getId();
        EntityField<Integer> reserveField = new EntityField<>(Book.RESERVED);
        reserveField.setValue(ConstantManager.NOT_RESERVED);
        EntityField<Integer> userField = new EntityField<>(Book.READER_ID);
        userField.setValue(null);
        EntityField<Integer> standardLocationField = book.fieldForName(Book.STANDARD_LOCATION_ID);
        int locationId = standardLocationField.getValue();
        EntityField<Integer> locationField = new EntityField<>(Book.LOCATION_ID);
        locationField.setValue(locationId);
        try {
          connector.suspendAutoCommit();
          bookDao.update(bookId, reserveField);
          bookDao.update(bookId, userField);
          bookDao.update(bookId, locationField);
          connector.commit();
        } catch (SQLException throwables) {
          connector.rollback();
        } finally {
          connector.restoreAutoCommit();
        }
      }
    } catch (DaoException | SQLException | LibraryEntityException e) {
      throw new ServiceException("Cannot deny book", e);
    }
  }

  @Override
  public void fulfillOrder(BookOrder dispatchedOrder) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      for (EditionInfo editionInfo : dispatchedOrder.getEditionInfoSet()) {
        Book book = editionInfo.getBook();
        int bookId = book.getId();
        EntityField<Integer> locField = new EntityField<>(Book.LOCATION_ID);
        locField.setValue(ConstantManager.LOCATION_ON_HAND);
        EntityField<Integer> reserveField = new EntityField<>(Book.RESERVED);
        reserveField.setValue(ConstantManager.ISSUED);
        int locationId = (int)book.fieldForName(Book.LOCATION_ID).getValue();
        EntityField<LocalDate> dateField = new EntityField<>(Book.DUE_DATE);
        LocalDate dueDate = LocalDate.now().plusDays(ConstantManager.DESK_DISPATCH_TERM);
        dateField.setValue(dueDate);
        try {
          connector.suspendAutoCommit();
          bookDao.update(bookId, locField);
          bookDao.update(bookId, reserveField);
          if (locationId == ConstantManager.LOCATION_DELIVERY_DESK_RESERVE) {
            bookDao.update(bookId, dateField);
          }
          connector.commit();
        } catch (SQLException throwables) {
          connector.rollback();
        } finally {
          connector.restoreAutoCommit();
        }
        int editionId = editionInfo.getEdition().getId();
        LibraryDao<Edition> editionDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.EDITION, connector);
        Edition edition = editionDao.get(editionId);
        int deliveries = (int) edition.fieldForName(Edition.DELIVERIES).getValue();
        deliveries++;
        EntityField<Integer> deliveryField = new EntityField<>(Edition.DELIVERIES);
        deliveryField.setValue(deliveries);
        editionDao.update(editionId, deliveryField);
      }
    } catch (DaoException | SQLException | LibraryEntityException e) {
      throw new ServiceException("Cannot prepare book", e);
    }
  }

  @Override
  public Book findBookById(int bookId) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      return bookDao.get(bookId);
    } catch (DaoException e) {
      throw new ServiceException("Cannot find book", e);
    }
  }

  @Override
  public void fixReturn(Book book) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      int bookId = book.getId();
      EntityField<Integer> userField = new EntityField<>(Book.READER_ID);
      userField.setValue(null);
      EntityField<Integer> standardLocationField = book.fieldForName(Book.STANDARD_LOCATION_ID);
      int locationId = standardLocationField.getValue();
      EntityField<Integer> locationField = new EntityField<>(Book.LOCATION_ID);
      locationField.setValue(locationId);
      EntityField<Integer> reserveField = new EntityField<>(Book.RESERVED);
      reserveField.setValue(ConstantManager.NOT_RESERVED);
      EntityField<LocalDate> dateField = new EntityField<>(Book.DUE_DATE);
      dateField.setValue(null);
      try {
        connector.suspendAutoCommit();
        bookDao.update(bookId, userField);
        bookDao.update(bookId, locationField);
        bookDao.update(bookId, reserveField);
        bookDao.update(bookId, dateField);
        connector.commit();
      } catch (SQLException throwables) {
        connector.rollback();
      } finally {
        connector.restoreAutoCommit();
      }
    } catch (DaoException | LibraryEntityException | SQLException e) {
      throw new ServiceException("Cannot return book", e);
    }
  }

  @Override
  public List<Book> findDeskBooksOnHands() throws ServiceException {
    List<Book> booksOnHands = new ArrayList<>();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      Criteria criteria = new Criteria();
      EntityField<Integer> locationId = new EntityField<>(Book.LOCATION_ID);
      locationId.setValue(ConstantManager.LOCATION_ON_HAND);
      criteria.addConstraint(locationId);
      booksOnHands.addAll(bookDao.read(criteria, true));
    } catch (DaoException e) {
      throw new ServiceException("Cannot find handced books", e);
    }
    return booksOnHands;
  }

  @Override
  public List<Book> findBooksByEditionCode(String code) throws ServiceException {
    List<Book> books;
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Edition> editionDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.EDITION, connector);
      EntityField<String> field = new EntityField<>(Edition.STANDARD_NUMBER);
      field.setValue(code);
      Criteria criteria = new Criteria();
      criteria.addConstraint(field);
      List<Edition> editions = editionDao.read(criteria, true);
      int editionId = 0;
      if (!editions.isEmpty()) {
        editionId = editions.get(0).getId();
      }
      UtilService utilService = LibServiceFactory.getUtilService();
      books = utilService.findBooksByEdition(editionId);
    } catch (DaoException e) {
      throw new ServiceException("Cannot find editions", e);
    }
    return books;
  }
}
