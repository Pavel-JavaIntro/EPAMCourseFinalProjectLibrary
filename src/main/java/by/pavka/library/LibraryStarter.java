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

/**
 * LibraryStarter
 * <p>
 * ServletContextListener initializing the database connection pool, downloading constants from the database and initializing
 * lists of users' book orders.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class LibraryStarter implements ServletContextListener {
  private static final Logger LOGGER = LogManager.getLogger(LibraryStarter.class.getName());

  @Override
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

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    LOGGER.info("FINISHED");
    DBConnectorPool.getInstance().disconnect();
  }
}
