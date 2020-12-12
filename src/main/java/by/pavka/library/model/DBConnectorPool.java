package by.pavka.library.model;

import by.pavka.library.model.dao.DBConnector;
import by.pavka.library.model.dao.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Pull of connectors to the database including sql connection and a wrapper
 *
 * @version 1.0
 * @uthor Pavel Kassitchev
 */
public class DBConnectorPool {
  private static final Logger LOGGER = LogManager.getLogger(DBConnectorPool.class);
  private static final DBConnectorPool INSTANCE = new DBConnectorPool();
  private static final int TIMEOUT = 10;

  private BlockingQueue<DBConnector> connections;
  private BlockingQueue<DBConnector> usedConnections;
  private int maxSize;
  private String url;
  private String login;
  private String password;

  private DBConnectorPool() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
    String driver = resourceBundle.getString("driver");
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      LOGGER.fatal("Database Driver not found");
      throw new LibraryFatalException("Database Driver not found", e);
    }
    url = resourceBundle.getString("url");
    login = resourceBundle.getString("user");
    password = resourceBundle.getString("pass");
    String num = resourceBundle.getString("connections");
    String maxNum = resourceBundle.getString("max_connections");
    int connectionsNumber;
    try {
      connectionsNumber = Integer.parseInt(num);
    } catch (NumberFormatException e) {
      LOGGER.error("Integer parcing failed");
      connectionsNumber = 5;
    }
    try {
      maxSize = Integer.parseInt(maxNum);
    } catch (NumberFormatException e) {
      LOGGER.error("Max size connection pool not parsed");
      maxSize = connectionsNumber;
    }
    connections = new ArrayBlockingQueue<>(maxSize);
    usedConnections = new ArrayBlockingQueue<>(maxSize);
    for (int i = 0; i < connectionsNumber; i++) {
      connections.add(createConnection(url, login, password));
    }
  }

  public static DBConnectorPool getInstance() {
    return INSTANCE;
  }

  private DBConnector createConnection(String url, String login, String password) {
    try {
      Connection connection = DriverManager.getConnection(url, login, password);
      return new DBConnector(connection);
    } catch (SQLException | DaoException e) {
      LOGGER.fatal("Database Connection not created");
      throw new LibraryFatalException("Database Connection not created", e);
    }
  }

  private synchronized boolean addConnection() {
    if (connections.size() >= maxSize) {
      return false;
    }
    try {
      Connection connection = DriverManager.getConnection(url, login, password);
      DBConnector connector = new DBConnector(connection);
      connections.add(connector);
    } catch (SQLException | DaoException e) {
      LOGGER.error("Can't add a connection");
      return false;
    }
    return true;
  }

  public DBConnector obtainConnector() {
    DBConnector connector = pickConnector();
    if (connector == null) {
      if (addConnection()) {
        connector = pickConnector();
      }
    }
    if (connector != null) {
      usedConnections.offer(connector);
    }
    return connector;
  }

  private DBConnector pickConnector() {
    DBConnector connector = null;
    try {
      connector = connections.poll(TIMEOUT, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return connector;
  }

  public boolean releaseConnector(DBConnector connector) {
    if (connector != null) {
      connections.offer(connector);
      return usedConnections.remove(connector);
    }
    return false;
  }

  public void disconnect() {
    for (DBConnector connector : usedConnections) {
      releaseConnector(connector);
    }
    for (DBConnector connector : connections) {
      try {
        connector.getConnection().close();
      } catch (SQLException ex) {
        LOGGER.error("Connection not closed while destroying the app");
      }
    }
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    Driver driver;
    while (drivers.hasMoreElements()) {
      try {
        driver = drivers.nextElement();
        DriverManager.deregisterDriver(driver);
      } catch (SQLException ex) {
        LOGGER.error("Driver not deregistered while destroying the app");
      }
    }
  }
}
