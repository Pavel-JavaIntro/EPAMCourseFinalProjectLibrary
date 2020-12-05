package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ReturnCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter();
    HttpSession session = request.getSession();
    session.removeAttribute(RESULT);
    int bookId = Integer.parseInt(request.getParameter(RETURN));
    LibraryService service = LibraryService.getInstance();
    Book book = null;
    try {
      book = service.findBookById(bookId);
    } catch (ServiceException e) {
      pageRouter.setPage(PageRouter.ERROR);
      LOGGER.error("ReturnCommand hasn't completed");
    }
    session.setAttribute(RETURNING, book);
    return pageRouter;
  }
}
