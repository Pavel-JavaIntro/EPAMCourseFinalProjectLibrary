package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.client.AppClient;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.model.util.ConstantManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class SelectBookCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String idS = request.getParameter(EDITION);
    String desk = request.getParameter(DESK);
    int id = Integer.parseInt(idS);
    AppClient client = (AppClient) session.getAttribute(SESSION_ATTRIBUTE_CLIENT);
    Set<EditionInfo> editionInfos = (Set<EditionInfo>) session.getAttribute(EDITIONS);
    if (editionInfos != null) {
      for (EditionInfo info : editionInfos) {
        if (id == info.getEdition().getId()) {
          client.addEditionInfo(info);
          if (desk != null) {
            info.setLocationId(ConstantManager.LOCATION_DELIVERY_DESK_RESERVE);
          } else {
            info.setLocationId(ConstantManager.LOCATION_READING_HALL_RESERVE);
          }
        }
      }
    }
    return new PageRouter(PageRouter.SEARCH);
  }
}
