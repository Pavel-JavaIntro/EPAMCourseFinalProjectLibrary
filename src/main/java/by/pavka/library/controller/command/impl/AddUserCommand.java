package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.email.ConfirmationMailSender;
import by.pavka.library.entity.impl.User;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ProcessUserService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.LibServiceFactory;
import by.pavka.library.model.util.ConstantManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddUserCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.PROCESS_USERS);
    HttpSession session = request.getSession();
    String surname = (String)session.getAttribute(USER_SURNAME);
    String name = (String)session.getAttribute(USER_NAME);
    if (surname == null || name == null) {
      return pageRouter;
    }
    String address = request.getParameter(USER_ADDRESS);
    String phone = request.getParameter(USER_PHONE);
    String email = request.getParameter(USER_EMAIL);
    String role = request.getParameter(USER_ROLE);
    int roleId = ConstantManager.getRoleId(role);
    User user = new User();
    user.setValue(User.ROLE_ID, roleId);
    user.setValue(User.SURNAME, surname);
    user.setValue(User.NAME, name);
    user.setValue(User.ADDRESS, address);
    if (!phone.isEmpty()) {
      user.setValue(User.PHONE, phone);
    }
    if (!email.isEmpty()) {
      user.setValue(User.EMAIL, email);
      int password = ConfirmationMailSender.sendInvitation(email).hashCode();
      user.setValue(User.PASSWORD, password);
    }
    ProcessUserService service = LibServiceFactory.getProcessUserService();
    try {
      service.addUser(user);
      session.setAttribute(RESULT, PageRouter.RESULT_SUCCESS);
      session.removeAttribute(USERS);
      session.removeAttribute(USER_NAME);
      session.removeAttribute(USER_SURNAME);
    } catch (ServiceException e) {
      pageRouter.setPage(PageRouter.ERROR);
      LOGGER.error("AddUserCommand hasn't completed");
    }
    return pageRouter;
  }
}
