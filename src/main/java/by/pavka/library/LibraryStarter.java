package by.pavka.library;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.entity.order.OrderHolder;
import by.pavka.library.model.DBConnectorPool;
import by.pavka.library.model.util.ConstantManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ResourceBundle;

public class LibraryStarter implements ServletContextListener {
  private static final Logger LOGGER = LogManager.getLogger(LibraryStarter.class.getName());

  public void contextInitialized(ServletContextEvent servletContextEvent) {
    LOGGER.info("STARTED");
    DBConnectorPool.getInstance();
    ConstantManager.getLocationById(0);
    OrderHolder.getInstance();
    ServletContext context = servletContextEvent.getServletContext();
    ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
    String email = resourceBundle.getString("email");
    context.setAttribute(ActionCommand.APP_ATTRIBUTE_EMAIL, email);
  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    LOGGER.info("FINISHED");
//    DBConnectionPool.getInstance().disconnect();
    DBConnectorPool.getInstance().disconnect();
  }
}
