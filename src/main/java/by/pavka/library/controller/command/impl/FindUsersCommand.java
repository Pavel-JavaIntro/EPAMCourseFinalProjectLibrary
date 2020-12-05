package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.impl.User;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindUsersCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter();
    HttpSession session = request.getSession();
    String surname = request.getParameter(USER_SURNAME);
    String name = request.getParameter(USER_NAME);
    if (surname != null && name != null) {
      session.removeAttribute(USERS);
      session.removeAttribute(RESULT);
      LibraryService service = LibraryService.getInstance();
      try {
        List<User> users = service.findUsers(surname, name);
        session.setAttribute(USER_SURNAME, surname);
        session.setAttribute(USER_NAME, name);
        if (!users.isEmpty()) {
          session.setAttribute(USERS, users);
        }
        session.setAttribute(USER_ADDITION, PageRouter.USER_ADDITION);
      } catch (ServiceException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("FindUsersCommand hasn't completed");
      }
    }
    return pageRouter;
  }
}