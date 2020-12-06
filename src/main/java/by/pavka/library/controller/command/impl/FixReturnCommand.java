package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ProcessBookService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.LibServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FixReturnCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    HttpSession session = request.getSession();
    PageRouter pageRouter = new PageRouter(PageRouter.RETURN_BOOKS);
    Book book = (Book) session.getAttribute(RETURNING);
    ProcessBookService service = LibServiceFactory.getProcessBookService();
    if (book != null) {
      try {
        service.fixReturn(book);
        session.removeAttribute(RETURNING);
        session.setAttribute(RESULT, PageRouter.RESULT_SUCCESS);
      } catch (ServiceException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("FixReturnCommand hasn't completed");
      }
    }
    return pageRouter;
  }
}
