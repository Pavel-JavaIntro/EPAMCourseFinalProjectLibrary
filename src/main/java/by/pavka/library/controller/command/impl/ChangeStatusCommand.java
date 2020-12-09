package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ProcessUserService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.LibServiceFactory;
import by.pavka.library.model.util.ConstantManager;

import javax.servlet.http.HttpServletRequest;

/**
 * ChangeStatusCommand
 * <p>
 * This command is executed when a librarian (admin) changes a status of another user among "guest", "reader", "subscriber"
 * (and "librarian").
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ChangeStatusCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.PROCESS_USERS);
    String user = request.getParameter(USER);
    String role = request.getParameter(STATUS);
    if (user != null && role != null) {
      int userId = Integer.parseInt(user);
      int roleId = ConstantManager.getRoleId(role);
      ProcessUserService service = LibServiceFactory.getProcessUserService();
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
