package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.model.service.ProcessBookService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.LibServiceFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * DecommissionBookCommand
 * <p>
 * This command is executed when a librarian decommission a book
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class DecommissionBookCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.PROCESS_BOOKS);
    String bookS = request.getParameter(DECOM);
    if (bookS != null && !bookS.isEmpty()) {
      int bookId = Integer.parseInt(bookS);
      ProcessBookService service = LibServiceFactory.getProcessBookService();
      try {
        service.decommissionBook(bookId);
      } catch (ServiceException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("DecommissionBookCommand hasn't completed");
      }
    }
    return pageRouter;
  }
}
