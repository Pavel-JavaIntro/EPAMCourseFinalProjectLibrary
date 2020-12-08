package by.pavka.library.controller.command;

/**
 * PageRouter
 * <p>
 * The class presents a return value of ActionCommand execution and contains the JSP address and transition type to
 * to forward or to redirect to.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class PageRouter {
  public static final String WELCOME = "/jsp/welcome.jsp";
  public static final String ERROR = "/jsp/error.jsp";
  public static final String SEARCH = "/jsp/search.jsp";
  public static final String LOGIN = "block/login.jsp";
  public static final String LOGOUT = "block/logout.jsp";
  public static final String ADMIN = "block/admin.jsp";
  public static final String LATIN = "block/latin.jsp";
  public static final String READER_SELECT = "block/reader_select.jsp";
  public static final String SUBSCRIBER_SELECT = "block/subscriber_select.jsp";
  public static final String BASKET = "block/basket.jsp";
  public static final String ADMIN_ASSIGN = "admin_assign.jsp";
  public static final String LIBRARIAN_ASSIGN = "librarian_assign.jsp";
  public static final String INDEX = "/index.jsp";
  public static final String ORDER = "/jsp/order.jsp";
  public static final String RESERVE = "/jsp/reserve.jsp";
  public static final String SHOW_ORDERS = "/jsp/show_orders.jsp";
  public static final String SHOW_BOOKS = "/jsp/show_books.jsp";
  public static final String PROCESS_BOOKS = "/jsp/process_books.jsp";
  public static final String EDITION_ADDITION = "block/edition_addition.jsp";
  public static final String BOOK_ADDITION = "block/book_addition.jsp";
  public static final String PROCESS_USERS = "/jsp/process_users.jsp";
  public static final String USER_ADDITION = "block/user_addition.jsp";
  public static final String RETURN_BOOKS = "/jsp/return_books.jsp";
  public static final String RESULT_SUCCESS = "block/result_success.jsp";
  public static final String OVERDUE_BOOKS = "/jsp/overdue_books.jsp";

  public enum TransitionType {
    FORWARD,
    REDIRECT
  }

  private String page;
  private final TransitionType type;

  public PageRouter(String page, TransitionType type) {
    this.page = page;
    this.type = type;
  }

  public PageRouter(String page) {
    this(page, TransitionType.FORWARD);
  }

  public PageRouter() {
    this("");
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public TransitionType getType() {
    return type;
  }
}
