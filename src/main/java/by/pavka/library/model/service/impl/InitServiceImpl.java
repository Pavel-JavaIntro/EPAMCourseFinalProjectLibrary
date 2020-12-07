package by.pavka.library.model.service.impl;

import by.pavka.library.entity.LibraryEntityException;
import by.pavka.library.entity.SimpleListEntity;
import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.criteria.EntityField;
import by.pavka.library.entity.impl.Author;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.entity.impl.Edition;
import by.pavka.library.entity.impl.User;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.model.DBConnectorPool;
import by.pavka.library.model.dao.*;
import by.pavka.library.model.service.InitService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.util.ConstantManager;
import by.pavka.library.model.util.TableEntityMapper;

import java.util.*;

public class InitServiceImpl implements InitService {

  InitServiceImpl() {}

  @Override
  public <T extends SimpleListEntity> void initConstants(
      Map<Integer, String> constants, TableEntityMapper constant) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<T> dao = LibraryDaoFactory.getLibraryDao(constant, connector);
      List<T> list = dao.read();
      for (T entity : list) {
        constants.put(entity.getId(), entity.getDescription());
      }
    } catch (DaoException e) {
      throw new ServiceException("Cannot initialize constants", e);
    }
  }

  @Override
  public int countBooks() throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      return bookDao.read().size();
    } catch (DaoException e) {
      throw new ServiceException("Cannot count the books", e);
    }
  }

  @Override
  public int countUsers() throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.USER, connector);
      return userDao.read().size();
    } catch (DaoException e) {
      throw new ServiceException("Cannot count the users", e);
    }
  }

  @Override
  public Collection<BookOrder> getPlacedOrders() throws ServiceException {
    return getOrder(ConstantManager.RESERVED);
  }

  @Override
  public Collection<BookOrder> getPreparedOrders() throws ServiceException {
    return getOrder(ConstantManager.PREPARED);
  }

  private Collection<BookOrder> getOrder(int type) throws ServiceException {
    Collection<BookOrder> orders = new ArrayList<>();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      LibraryDao<Edition> editionDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.EDITION, connector);
      Criteria criteria = new Criteria();
      EntityField<Integer> reservedField = new EntityField<>(Book.RESERVED);
      reservedField.setValue(type);
      criteria.addConstraint(reservedField);
      List<Book> reservedBooks = bookDao.read(criteria, true);
      for (Book book : reservedBooks) {
        int userId = (int)book.fieldForName(Book.READER_ID).getValue();
        EditionInfo editionInfo = new EditionInfo();
        editionInfo.setBook(book);
        int editionId = (int)book.fieldForName(Book.EDITION_ID).getValue();
        Edition edition = editionDao.get(editionId);
        editionInfo.setEdition(edition);
        int locationId = (int)book.fieldForName(Book.LOCATION_ID).getValue();
        editionInfo.setLocationId(locationId);
        int standardLocationId = (int)book.fieldForName(Book.STANDARD_LOCATION_ID).getValue();
        editionInfo.setStandardLocationId(standardLocationId);
        UtilService utilService = LibServiceFactory.getUtilService();
        utilService.bindAuthors(editionInfo, connector);
        BookOrder bookOrder = new BookOrder(userId, editionInfo);
        orders.add(bookOrder);
      }
    } catch (DaoException | LibraryEntityException e) {
      throw new ServiceException("Cannot get orders", e);
    }
    return orders;
  }
}
