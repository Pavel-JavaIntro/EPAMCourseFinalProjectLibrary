package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.client.AppClient;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.entity.order.OrderHolder;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderBookCommand implements ActionCommand {
  private static final String UNSELECT = "unselect";

  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.ORDER);
    HttpSession session = request.getSession();
    AppClient client = (AppClient)session.getAttribute(SESSION_ATTRIBUTE_CLIENT);
    String action = request.getParameter(ORDER);
    if(action.equals(UNSELECT)) {
      int editionId = 0;
      if (request.getParameter(BOOK) != null) {
        editionId = Integer.parseInt(request.getParameter(BOOK));
      }
      for (EditionInfo ei : client.getEditionInfos()) {
        if (ei.getEdition().getId() == editionId) {
          client.removeEditionInfo(ei);
          break;
        }
      }
      return pageRouter;
    } else {
      BookOrder bookOrder = new BookOrder(client);
      LibraryService service = LibraryService.getInstance();
      try {
        service.orderBook(bookOrder);
        OrderHolder.getInstance().addOrder(bookOrder);
        pageRouter.setPage(PageRouter.RESERVE);
      } catch (ServiceException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("OrderBookCommand hasn't completed");
      }
      return pageRouter;
    }
  }
}
