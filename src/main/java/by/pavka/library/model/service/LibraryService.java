package by.pavka.library.model.service;

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
import by.pavka.library.model.dao.DBConnector;
import by.pavka.library.model.dao.DaoException;
import by.pavka.library.model.dao.LibraryDao;
import by.pavka.library.model.dao.ManyToManyDao;
import by.pavka.library.model.dao.impl.LibraryDaoImpl;
import by.pavka.library.model.dao.impl.ManyToManyDaoImpl;
import by.pavka.library.model.util.ConstantManager;
import by.pavka.library.model.util.TableEntityMapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class LibraryService implements WelcomeServiceInterface {
  private static final LibraryService INSTANCE = new LibraryService();

  private LibraryService() {}

  public static LibraryService getInstance() {
    return INSTANCE;
  }

  public <T extends SimpleListEntity> void initConstants(
      Map<Integer, String> constants, TableEntityMapper constant) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<T> dao = new LibraryDaoImpl<>(constant, connector);
      List<T> list = dao.read();
      for (T entity : list) {
        constants.put(entity.getId(), entity.getDescription());
      }
    } catch (DaoException e) {
      throw new ServiceException("Cannot initialize constants", e);
    }
  }

  public int countBooks() throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
      return bookDao.read().size();
    } catch (DaoException e) {
      throw new ServiceException("Cannot count the books", e);
    }
  }

  public int countUsers() throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = new LibraryDaoImpl<>(TableEntityMapper.USER, connector);
      return userDao.read().size();
    } catch (DaoException e) {
      throw new ServiceException("Cannot count the users", e);
    }
  }

  @Override
  public User auth(String surname, String name, String password) throws ServiceException {
    int hashPass = password.hashCode();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = new LibraryDaoImpl<>(TableEntityMapper.USER, connector);
      Criteria criteria = new Criteria();
      EntityField<String> surnameField = new EntityField<>(User.SURNAME);
      surnameField.setValue(surname);
      EntityField<String> nameField = new EntityField<>(User.NAME);
      nameField.setValue(name);
      EntityField<Integer> passField = new EntityField<>(User.PASSWORD);
      passField.setValue(hashPass);
      criteria.addConstraints(surnameField, nameField, passField);
      List<User> users = userDao.read(criteria, true);
      if (users.size() > 0) {
        return users.get(0);
      } else {
        return null;
      }
    } catch (DaoException e) {
      throw new ServiceException("Cannot authorize the user", e);
    }
  }

  @Override
  public List<Book> findBooksByEditionCode(String code) throws ServiceException {
    List<Book> books;
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Edition> editionDao = new LibraryDaoImpl<>(TableEntityMapper.EDITION, connector);
      EntityField<String> field = new EntityField<>(Edition.STANDARD_NUMBER);
      field.setValue(code);
      Criteria criteria = new Criteria();
      criteria.addConstraint(field);
      List<Edition> editions = editionDao.read(criteria, true);
      int editionId = 0;
      if (!editions.isEmpty()) {
        editionId = editions.get(0).getId();
      }
      books = findBooksByEdition(editionId);
    } catch (DaoException e) {
      throw new ServiceException("Cannot find editions", e);
    }
    return books;
  }

  @Override
  public List<Book> findBooksByEdition(int id) throws ServiceException {
    List<Book> result = new ArrayList<>();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> dao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
      Criteria criteria = new Criteria();
      EntityField<Integer> edId = new EntityField<>(Book.EDITION_ID);
      edId.setValue(id);
      criteria.addConstraint(edId);
      result.addAll(dao.read(criteria, true));
    } catch (DaoException e) {
      throw new ServiceException("Cannot find books", e);
    }
    return result;
  }

  @Override
  public List<Edition> findEditions(String title, String author) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      ManyToManyDao<Edition, Author> dao = new ManyToManyDaoImpl(connector);
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
        try (LibraryDao<Author> authorDao = new LibraryDaoImpl<>(TableEntityMapper.AUTHOR, connector)) {
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
  public Book findFreeBookByEdition(int id) throws ServiceException {
    Book book = null;
    try {
      List<Book> result = findBooksByEdition(id);
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

  public void bindAuthors(EditionInfo info, DBConnector connector) throws DaoException, LibraryEntityException {
    ManyToManyDao<Edition, Author> editionDao = new ManyToManyDaoImpl(connector);
    LibraryDao<Author> authorDao = new LibraryDaoImpl<>(TableEntityMapper.AUTHOR, connector);
    Set<Author> authors = new HashSet<>();
    Set<Integer> authorIds = editionDao.getSecond(info.getEdition().getId());
    for (int id : authorIds) {
      authors.add(authorDao.get(id));
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (Author a : authors) {
      stringBuilder.append(a.fieldForName(Author.SURNAME).getValue()).append(" ");
    }
    info.setAuthors(stringBuilder.toString());
  }

  @Override
  public void bindAuthors(EditionInfo info) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
//      ManyToManyDao<Edition, Author> editionDao = new ManyToManyDaoImpl(connector);
//      LibraryDao<Author> authorDao = new LibraryDaoImpl<>(TableEntityMapper.AUTHOR, connector);
//      Set<Author> authors = new HashSet<>();
//      Set<Integer> authorIds = editionDao.getSecond(info.getEdition().getId());
//      for (int id : authorIds) {
//        authors.add(authorDao.get(id));
//      }
//      StringBuilder stringBuilder = new StringBuilder();
//      for (Author a : authors) {
//        stringBuilder.append(a.fieldForName(Author.SURNAME).getValue()).append(" ");
//      }
//      info.setAuthors(stringBuilder.toString());
      bindAuthors(info, connector);

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

  @Override
  public void addCode(String code) throws ServiceException {}

  @Override
  public int editionIdByCode(String code) throws ServiceException {
    int editionId = 0;
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Edition> editionDao = new LibraryDaoImpl<>(TableEntityMapper.EDITION, connector);
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
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
      bookDao.add(book);
    } catch (DaoException e) {
      throw new ServiceException("Cannot add a book", e);
    }
  }

  @Override
  public int addEdition(Edition edition) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Edition> editionDao = new LibraryDaoImpl<>(TableEntityMapper.EDITION, connector);
      return editionDao.add(edition);
    } catch (DaoException e) {
      throw new ServiceException("Cannot add an edition code", e);
    }
  }

  @Override
  public int addAuthor(Author author) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Author> authorDao = new LibraryDaoImpl<>(TableEntityMapper.AUTHOR, connector);
      return authorDao.add(author);
    } catch (DaoException e) {
      throw new ServiceException("Cannot add an author", e);
    }
  }

  @Override
  public void bindEditionAndAuthors(int editionId, int[] authorsId) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      ManyToManyDao<Edition, Author> dao = new ManyToManyDaoImpl(connector);
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
  public List<Author> findAuthors(Criteria criterion) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Author> authorDao = new LibraryDaoImpl<>(TableEntityMapper.AUTHOR, connector);
      return authorDao.read(criterion, true);
    } catch (DaoException e) {
      throw new ServiceException("Cannot find authors", e);
    }
  }

  public List<Author> findAuthors(Author author) throws ServiceException {
    List<Author> result = new ArrayList<>();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Author> authorDao = new LibraryDaoImpl<>(TableEntityMapper.AUTHOR, connector);
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
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
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
  public List<User> findUsers(String surname, String name) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = new LibraryDaoImpl<>(TableEntityMapper.USER, connector);
      EntityField<String> surnameField = new EntityField<>(User.SURNAME);
      surnameField.setValue(surname);
      EntityField<String> nameField = new EntityField<>(User.NAME);
      nameField.setValue(name);
      Criteria criteria = new Criteria();
      criteria.addConstraints(surnameField, nameField);
      List<User> users = userDao.read(criteria, true);
      return users;
    } catch (DaoException e) {
      throw new ServiceException("Cannot find users", e);
    }
  }

  @Override
  public void addUser(User user) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = new LibraryDaoImpl<>(TableEntityMapper.USER, connector);
      userDao.add(user);
    } catch (DaoException e) {
      throw new ServiceException("Cannot add users", e);
    }
  }

  @Override
  public void changeStatus(int userId, int roleId) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = new LibraryDaoImpl<>(TableEntityMapper.USER, connector);
      EntityField<Integer> field = new EntityField<>(User.ROLE_ID);
      field.setValue(roleId);
      userDao.update(userId, field);
    } catch (DaoException e) {
      throw new ServiceException("Cannot change user status", e);
    }
  }

  @Override
  public void orderBook(BookOrder bookOrder) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
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

  @Override
  public void prepareOrder(BookOrder bookOrder) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
      for (EditionInfo editionInfo : bookOrder.getEditionInfoSet()) {
        Book book = editionInfo.getBook();
        int bookId = book.getId();
//        EntityField<Integer> locField = new EntityField<>(Book.LOCATION_ID);
//        locField.setValue(ConstantManager.LOCATION_READING_HALL_RESERVE);
        EntityField<Integer> reserveField = new EntityField<>(Book.RESERVED);
        reserveField.setValue(ConstantManager.PREPARED);
        try {
          System.out.println("WHILE PREPARING: " + editionInfo.getLocationId() + " " + book.fieldForName(Book.LOCATION_ID).getValue());
        } catch (LibraryEntityException e) {
          e.printStackTrace();
        }
        try {
          connector.suspendAutoCommit();
          //bookDao.update(bookId, locField);
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

  public void denyOrder(BookOrder bookOrder) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
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
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
      for (EditionInfo editionInfo : dispatchedOrder.getEditionInfoSet()) {
        Book book = editionInfo.getBook();
        int bookId = book.getId();
        EntityField<Integer> locField = new EntityField<>(Book.LOCATION_ID);
        locField.setValue(ConstantManager.LOCATION_ON_HAND);
        EntityField<Integer> reserveField = new EntityField<>(Book.RESERVED);
        reserveField.setValue(ConstantManager.ISSUED);
        int locationId = (int)book.fieldForName(Book.LOCATION_ID).getValue();
        System.out.println("LOCATION ID = " + locationId);
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
          System.out.println("ROLLING BACK");
          connector.rollback();
        } finally {
          connector.restoreAutoCommit();
        }
        int editionId = editionInfo.getEdition().getId();
        LibraryDao<Edition> editionDao = new LibraryDaoImpl<>(TableEntityMapper.EDITION, connector);
        Edition edition = editionDao.get(editionId);
        int deliveries = (int) edition.fieldForName(Edition.DELIVERIES).getValue();
        deliveries++;
        EntityField<Integer> deliveryField = new EntityField<>(Edition.DELIVERIES);
        deliveryField.setValue(deliveries);
        editionDao.update(editionId, deliveryField);
      }
    } catch (DaoException | SQLException | LibraryEntityException e) {
      System.out.println(e.getMessage());
      throw new ServiceException("Cannot prepare book", e);
    }
  }

  @Override
  public Book findBookById(int bookId) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
      return bookDao.get(bookId);
    } catch (DaoException e) {
      throw new ServiceException("Cannot find book", e);
    }
  }

  @Override
  public void fixReturn(Book book) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
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
  public Collection<BookOrder> getPlacedOrders() throws ServiceException {
    return getOrder(ConstantManager.RESERVED);
  }

  @Override
  public Collection<BookOrder> getPreparedOrders() throws ServiceException {
    return getOrder(ConstantManager.PREPARED);
  }

  @Override
  public List<Book> findDeskBooksOnHands() throws ServiceException {
    List<Book> booksOnHands = new ArrayList<>();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
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

  private Collection<BookOrder> getOrder(int type) throws ServiceException {
    Collection<BookOrder> orders = new ArrayList<>();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> bookDao = new LibraryDaoImpl<>(TableEntityMapper.BOOK, connector);
      LibraryDao<Edition> editionDao = new LibraryDaoImpl<>(TableEntityMapper.EDITION, connector);
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
        bindAuthors(editionInfo, connector);
        BookOrder bookOrder = new BookOrder(userId, editionInfo);
        orders.add(bookOrder);
      }
    } catch (DaoException | LibraryEntityException e) {
      throw new ServiceException("Cannot get orders", e);
    }
    return orders;
  }
}
