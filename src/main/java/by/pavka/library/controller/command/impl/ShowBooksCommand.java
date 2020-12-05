package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.order.OrderHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Queue;

public class ShowBooksCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    Queue orders = OrderHolder.getInstance().getPreparedOrders();
    HttpSession session = request.getSession();
    session.setAttribute(PREPARED, orders);
    return new PageRouter(PageRouter.SHOW_BOOKS);
  }
}
