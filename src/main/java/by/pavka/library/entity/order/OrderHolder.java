package by.pavka.library.entity.order;

import by.pavka.library.model.LibraryFatalException;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderHolder {
  private static final Logger LOGGER = LogManager.getLogger(OrderHolder.class);
  private static final OrderHolder instance = new OrderHolder();
  private Queue<BookOrder> placedOrders;
  private Queue<BookOrder> preparedOrders;

  private OrderHolder() {
    LibraryService service = LibraryService.getInstance();
    placedOrders = new ConcurrentLinkedQueue<>();
    preparedOrders = new ConcurrentLinkedQueue<>();
    try {
      Collection<BookOrder> oldPlacedOrders = service.getPlacedOrders();
      placedOrders.addAll(oldPlacedOrders);
      Collection<BookOrder> oldPreparedOrders = service.getPreparedOrders();
      preparedOrders.addAll(oldPreparedOrders);
    } catch (ServiceException e) {
      LOGGER.fatal("Cannot initialize order status");
      throw new LibraryFatalException("Cannot initialize order status");
    }
  }

  public static OrderHolder getInstance() {
    return instance;
  }

  public Queue<BookOrder> getPlacedOrders() {
    return placedOrders;
  }

  public Queue<BookOrder> getPreparedOrders() {
    return preparedOrders;
  }

  public void addOrder(BookOrder order) {
    placedOrders.add(order);
  }

  public void denyOrder(BookOrder bookOrder) {
    placedOrders.remove(bookOrder);
  }

  public void prepareOrder(BookOrder order) {
    preparedOrders.add(order);
  }

  public void fulfillOrder(BookOrder order) {
    preparedOrders.remove(order);
  }
}
