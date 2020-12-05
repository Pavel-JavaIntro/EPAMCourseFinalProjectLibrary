package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.order.OrderHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Queue;

public class ShowOrdersCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    Queue orders = OrderHolder.getInstance().getPlacedOrders();
    HttpSession session = request.getSession();
    session.setAttribute(ORDERS, orders);
    return new PageRouter(PageRouter.SHOW_ORDERS);
  }
}
