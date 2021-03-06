package by.pavka.library.model.service;

import by.pavka.library.entity.impl.Author;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.entity.impl.Edition;
import by.pavka.library.entity.order.BookOrder;

import java.util.List;

/**
 * Interface providing librarian level methods related to books and book orders
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */

public interface ProcessBookService {

  int editionIdByCode(String code) throws ServiceException;

  void addBook(Book book) throws ServiceException;

  int addEdition(Edition edition) throws ServiceException;

  int addAuthor(Author author) throws ServiceException;

  void bindEditionAndAuthors(int editionId, int[] authorsId) throws ServiceException;

  List<Author> findAuthors(Author author) throws ServiceException;

  void decommissionBook(int bookId) throws ServiceException;

  void prepareOrder(BookOrder bookOrder) throws ServiceException;

  void denyOrder(BookOrder bookOrder) throws ServiceException;

  void fulfillOrder(BookOrder dispatchedOrder) throws ServiceException;

  Book findBookById(int bookId) throws ServiceException;

  void fixReturn(Book book) throws ServiceException;

  List<Book> findDeskBooksOnHands() throws ServiceException;

  List<Book> findBooksByEditionCode(String code) throws ServiceException;
}
