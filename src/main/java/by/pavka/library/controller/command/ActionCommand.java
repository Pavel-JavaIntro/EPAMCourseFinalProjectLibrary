package by.pavka.library.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ActionCommand
 * <p>
 * Basing interface for the application commands. Contains a default method called by the servlet.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public interface ActionCommand {
  Logger LOGGER = LogManager.getLogger(ActionCommand.class);
  String SESSION_ATTRIBUTE_CLIENT = "client";
  String SESSION_ATTRIBUTE_LANGUAGE = "lan";
  String SESSION_ATTRIBUTE_PAGE = "page";
  String APP_ATTRIBUTE_BOOKS = "books";
  String APP_ATTRIBUTE_USERS = "users";
  String APP_ATTRIBUTE_EMAIL = "email";
  String COMMAND = "command";
  String WRONG_ACTION = "wrongAction";
  String SURNAME = "surname";
  String NAME = "name";
  String PASSWORD = "password";
  String ERROR_LOGIN_PASS = "errorLoginPassMessage";
  String TITLE = "title";
  String AUTHOR = "author";
  String EDITIONS = "editions";
  String LANGUAGE = "lan";
  String EDITION = "edition";
  String DESK = "desk";
  String ORDER = "order";
  String BOOK = "book";
  String ORDERS = "orders";
  String PREPARATION = "preparation";
  String PREPARED = "prepared";
  String CODE = "code";
  String ADDITION = "addition";
  String DECOMMISSION = "decommission";
  String BOOK_LOCATION = "booklocation";
  String BOOK_TITLE = "booktitle";
  String BOOK_YEAR = "bookyear";
  String GENRE = "genre";
  String DECOM = "decom";
  String USER_SURNAME = "surname";
  String USER_NAME = "name";
  String USERS = "users";
  String USER_ADDITION = "user_addition";
  String USER_ADDRESS = "address";
  String USER_PHONE = "phone";
  String USER_EMAIL = "email";
  String USER_ROLE = "role";
  String USER = "user";
  String STATUS = "status";
  String RETURN = "return";
  String RETURNING = "returning";
  String RESULT = "result";
  String OVERDUE = "overdue";

  /**
   * Wrapper method over execute(HttpServletRequest)
   *
   * @param request HttpServletRequest
   * @return PageRouter
   */
  default PageRouter executeCommand(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String page = (String) session.getAttribute(SESSION_ATTRIBUTE_PAGE);
    LOGGER.info("Current Page is " + page);
    PageRouter pageRouter = execute(request);
    String nextPage = pageRouter.getPage();
    if (nextPage.isEmpty()) {
      pageRouter.setPage(page);
    } else {
      session.setAttribute(SESSION_ATTRIBUTE_PAGE, nextPage);
    }
    LOGGER.info("Next Page is " + session.getAttribute(SESSION_ATTRIBUTE_PAGE));
    return pageRouter;
  }

  PageRouter execute(HttpServletRequest request);
}
