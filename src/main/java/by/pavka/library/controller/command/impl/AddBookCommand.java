package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.LibraryEntityException;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.model.service.ProcessBookService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.LibServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * AddBookCommand
 * <p>
 * This command is executed while a librarian adds a new book copy of and existing edition
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class AddBookCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    HttpSession session = request.getSession();
    PageRouter pageRouter = new PageRouter(PageRouter.PROCESS_BOOKS);
    String code = (String) session.getAttribute(CODE);
    session.removeAttribute(CODE);
    String loc = request.getParameter(BOOK_LOCATION);
    int locationId = loc.isEmpty() ? 0 : Integer.parseInt(loc);
    List<Book> books = (List<Book>) session.getAttribute(DECOMMISSION);
    int editionId = 0;
    if (books != null && !books.isEmpty()) {
      Book book = books.get(0);
      try {
        editionId = (int) book.fieldForName(Book.EDITION_ID).getValue();
        if (locationId == 0) {
          locationId = (int) book.fieldForName(Book.STANDARD_LOCATION_ID).getValue();
          session.removeAttribute(DECOMMISSION);
        }
      } catch (LibraryEntityException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("AddBookCommand hasn't completed");
        return pageRouter;
      }
    }
    if (code != null) {
      Book newBook = new Book();
      ProcessBookService service = LibServiceFactory.getProcessBookService();
      try {
        if (editionId == 0) {
          editionId = service.editionIdByCode(code);
        }
        if (locationId != 0) {
          newBook.fieldForName(Book.LOCATION_ID).setValue(locationId);
          newBook.fieldForName(Book.STANDARD_LOCATION_ID).setValue(locationId);
        }
        newBook.fieldForName(Book.EDITION_ID).setValue(editionId);
        service.addBook(newBook);
        session.setAttribute(RESULT, PageRouter.RESULT_SUCCESS);
      } catch (LibraryEntityException | ServiceException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("AddBookCommand hasn't completed");
      }
    }
    return pageRouter;
  }
}
