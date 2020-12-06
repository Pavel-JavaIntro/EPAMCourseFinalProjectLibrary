package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.entity.order.OrderHolder;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Queue;

public class DispatchCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.SHOW_BOOKS);
    LibraryService service = LibraryService.getInstance();
    OrderHolder orderHolder = OrderHolder.getInstance();
    Queue<BookOrder> preparedOrders = orderHolder.getPreparedOrders();
    int bookId = 0;
    if (request.getParameter(BOOK) != null) {
      bookId = Integer.parseInt(request.getParameter(BOOK));
    }
    for (BookOrder bookOrder : preparedOrders) {
      for (EditionInfo editionInfo : bookOrder.getEditionInfoSet()) {
        if (editionInfo.getBook() != null && editionInfo.getBook().getId() == bookId) {
          BookOrder dispatchedOrder = bookOrder.passBook(editionInfo);
          orderHolder.fulfillOrder(dispatchedOrder);
          try {
            service.fulfillOrder(dispatchedOrder);
          } catch (ServiceException e) {
            pageRouter.setPage(PageRouter.ERROR);
            LOGGER.error("DispatchCommand hasn't completed");
          }
        }
      }
    }
    return pageRouter;
  }
}
