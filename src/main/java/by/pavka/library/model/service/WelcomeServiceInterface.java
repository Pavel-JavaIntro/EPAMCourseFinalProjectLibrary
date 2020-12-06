package by.pavka.library.model.service;

import by.pavka.library.entity.SimpleListEntity;
import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.impl.Author;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.entity.impl.Edition;
import by.pavka.library.entity.impl.User;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.model.util.TableEntityMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface WelcomeServiceInterface {
  <T extends SimpleListEntity> void initConstants(
      Map<Integer, String> constants, TableEntityMapper constant) throws ServiceException;

  int countBooks() throws ServiceException;

  int countUsers() throws ServiceException;

  User auth(String surname, String name, String password) throws ServiceException;

  List<Book> findBooksByEditionCode(String code) throws ServiceException;

  List<Book> findBooksByEdition(int id) throws ServiceException;

  List<Edition> findEditions(String title, String author) throws ServiceException;

  Book findFreeBookByEdition(int id) throws ServiceException;

  void bindAuthors(EditionInfo info) throws ServiceException;

  void bindBookAndLocation(EditionInfo info) throws ServiceException;

  int editionIdByCode(String code) throws ServiceException;

  void addBook(Book book) throws ServiceException;

  int addEdition(Edition edition) throws ServiceException;

  int addAuthor(Author author) throws ServiceException;

  void bindEditionAndAuthors(int editionId, int[] authorsId) throws ServiceException;

  List<Author> findAuthors(Author author) throws ServiceException;

  void decommissionBook(int bookId) throws ServiceException;

  List<User> findUsers(String surname, String name) throws ServiceException;

  void addUser(User user) throws ServiceException;

  void changeStatus(int userId, int roleId) throws ServiceException;

  void orderBook(BookOrder bookOrder) throws ServiceException;

  void prepareOrder(BookOrder bookOrder) throws ServiceException;

  void fulfillOrder(BookOrder dispatchedOrder) throws ServiceException;

  Book findBookById(int bookId) throws ServiceException;

  void fixReturn(Book book) throws ServiceException;

  Collection<BookOrder> getPlacedOrders() throws ServiceException;

  Collection<BookOrder> getPreparedOrders() throws ServiceException;

  List<Book> findDeskBooksOnHands() throws ServiceException;
}
