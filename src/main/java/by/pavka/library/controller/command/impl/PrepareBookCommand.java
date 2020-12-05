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

public class PrepareBookCommand implements ActionCommand {
  public static final String PREPARE = "prepare";

  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter();
    String action = request.getParameter(PREPARATION);
    OrderHolder orderHolder = OrderHolder.getInstance();
    Queue<BookOrder> placedOrders = orderHolder.getPlacedOrders();
    int bookId = Integer.parseInt(request.getParameter(BOOK));
    LibraryService service = LibraryService.getInstance();
    for (BookOrder bookOrder : placedOrders) {
      for (EditionInfo editionInfo : bookOrder.getEditionInfoSet()) {
        if (editionInfo.getBook() != null && editionInfo.getBook().getId() == bookId) {
          BookOrder passedOrder = bookOrder.passBook(editionInfo);
          if (action.equals(PREPARE)) {
            orderHolder.prepareOrder(passedOrder);
            try {
              service.prepareOrder(passedOrder);
            } catch (ServiceException e) {
              pageRouter.setPage(PageRouter.ERROR);
              LOGGER.error("PrepareBookCommand hasn't completed");
            }
          } else {
            orderHolder.denyOrder(passedOrder);
            try {
              service.denyOrder(passedOrder);
            } catch (ServiceException e) {
              pageRouter.setPage(PageRouter.ERROR);
              LOGGER.error("PrepareBookCommand hasn't completed");
            }
          }
        }
      }
    }
    return pageRouter;
  }
}