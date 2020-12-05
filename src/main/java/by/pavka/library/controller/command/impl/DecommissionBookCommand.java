package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class DecommissionBookCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter();
    String bookS = request.getParameter(DECOM);
    if (bookS != null && !bookS.isEmpty()) {
      int bookId = Integer.parseInt(bookS);
      LibraryService service = LibraryService.getInstance();
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
