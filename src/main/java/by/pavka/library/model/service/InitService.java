package by.pavka.library.model.service;

import by.pavka.library.entity.SimpleListEntity;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.model.util.TableEntityMapper;

import java.util.Collection;
import java.util.Map;

public interface InitService {
  <T extends SimpleListEntity> void initConstants(
      Map<Integer, String> constants, TableEntityMapper constant) throws ServiceException;

  int countBooks() throws ServiceException;

  int countUsers() throws ServiceException;

  Collection<BookOrder> getPlacedOrders() throws ServiceException;

  Collection<BookOrder> getPreparedOrders() throws ServiceException;
}
