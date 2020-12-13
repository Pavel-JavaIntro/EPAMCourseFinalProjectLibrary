package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.model.service.ProcessBookService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.LibServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ReturnCommand
 * <p>
 * This command is executed when a librarian input a book ID. It despays the book data.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ReturnCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.RETURN_BOOKS);
    HttpSession session = request.getSession();
    session.removeAttribute(RESULT);
    int bookId = Integer.parseInt(request.getParameter(RETURN));
    ProcessBookService service = LibServiceFactory.getProcessBookService();
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
