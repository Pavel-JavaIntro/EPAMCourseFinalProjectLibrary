package by.pavka.library.controller;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.ActionFactory;
import by.pavka.library.controller.command.PageRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LibraryServlet extends HttpServlet {
  private static final Logger LOGGER = LogManager.getLogger(LibraryServlet.class);
  private static final String WELCOME = "welcome";

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    process(request, response);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (request.getParameter(ActionCommand.COMMAND).equals(WELCOME)) {
      process(request, response);
    } else {
      response.sendError(403);
    }
  }

  private void process(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ActionCommand command = ActionFactory.defineCommand(request);
    LOGGER.info("Execution: " + command.getClass().getSimpleName());
    PageRouter pageRouter = command.executeCommand(request);
    String page = pageRouter.getPage();
    if (pageRouter.getType() == PageRouter.TransitionType.FORWARD) {
      RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
      requestDispatcher.forward(request, response);
    } else {
      response.sendRedirect(page);
    }
  }
}
