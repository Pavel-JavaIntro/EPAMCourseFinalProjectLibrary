package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.entity.order.OrderHolder;
import by.pavka.library.model.service.ProcessBookService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.LibServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Queue;

/**
 * PrepareBookCommand
 * <p>
 * This command is executed when a librarian either prepare a book from a reader's order (transfer it to another location)
 * or deny the order.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class PrepareBookCommand implements ActionCommand {
  public static final String PREPARE = "prepare";

  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.SHOW_ORDERS);
    String action = request.getParameter(PREPARATION);
    OrderHolder orderHolder = OrderHolder.getInstance();
    Queue<BookOrder> placedOrders = orderHolder.getPlacedOrders();
    int bookId = Integer.parseInt(request.getParameter(BOOK));
    ProcessBookService service = LibServiceFactory.getProcessBookService();
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
