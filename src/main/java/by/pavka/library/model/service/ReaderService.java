package by.pavka.library.model.service;

import by.pavka.library.entity.impl.Edition;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.entity.order.EditionInfo;

import java.util.List;

/**
 * Interface providing reader level methods
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public interface ReaderService {
  List<Edition> findEditions(String title, String author) throws ServiceException;

  void bindAuthors(EditionInfo info) throws ServiceException;

  void bindBookAndLocation(EditionInfo info) throws ServiceException;

  void orderBook(BookOrder bookOrder) throws ServiceException;
}
