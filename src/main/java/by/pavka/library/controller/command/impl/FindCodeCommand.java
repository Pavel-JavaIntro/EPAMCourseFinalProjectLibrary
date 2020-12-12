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
import java.util.List;

/**
 * FindCodeCommand
 * <p>
 * This command is executed when a librarian wants to add a new book to the library firstly checking if the edition
 * code already exists.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class FindCodeCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.PROCESS_BOOKS);
    String code = request.getParameter(CODE);
    if (code != null) {
      ProcessBookService service = LibServiceFactory.getProcessBookService();
      HttpSession session = request.getSession();
      session.setAttribute(CODE, code);
      session.removeAttribute(DECOMMISSION);
      session.removeAttribute(RESULT);
      try {
        int editionId = service.editionIdByCode(code);
        if (editionId == 0) {
          session.setAttribute(ADDITION, PageRouter.EDITION_ADDITION);
        } else {
          List<Book> books = service.findBooksByEditionCode(code);
          System.out.println("LIST LENGTH IS " + books.size());
          session.setAttribute(ADDITION, PageRouter.BOOK_ADDITION);
          session.setAttribute(DECOMMISSION, books);
        }
      } catch (ServiceException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("FindCodeCommand hasn't completed");
      }
    }
    return pageRouter;
  }
}
