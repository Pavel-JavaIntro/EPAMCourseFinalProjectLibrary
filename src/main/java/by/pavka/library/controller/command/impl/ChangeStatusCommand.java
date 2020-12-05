package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.util.ConstantManager;

import javax.servlet.http.HttpServletRequest;

public class ChangeStatusCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter();
    String user = request.getParameter(USER);
    String role = request.getParameter(STATUS);
    if (user != null && role != null) {
      int userId = Integer.parseInt(user);
      int roleId = ConstantManager.getRoleId(role);
      LibraryService service = LibraryService.getInstance();
      try {
        service.changeStatus(userId, roleId);
      } catch (ServiceException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("ChangeStatusCommand hasn't completed");
      }
    }
    return pageRouter;
  }
}