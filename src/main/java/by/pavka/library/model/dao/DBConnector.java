package by.pavka.library.model.dao;

import by.pavka.library.model.DBConnectorPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector implements AutoCloseable {
  private static final Logger LOGGER = LogManager.getLogger(DBConnector.class);
  private final Connection connection;

  public DBConnector(Connection connection) throws DaoException {
    this.connection = connection;
    if (connection == null) {
      throw new DaoException("Connection Wrapper cannot get connection");
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public PreparedStatement obtainPreparedStatement(String sql) throws DaoException {
    if (connection != null) {
      try {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      } catch (SQLException e) {
        throw new DaoException("Not obtained statement", e);
      }
    }
    throw new DaoException("Connection is null");
  }

  public void closeStatement(Statement statement) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        LOGGER.error("Statement not closed");
      }
    }
  }

  public void suspendAutoCommit() throws SQLException {
    connection.setAutoCommit(false);
  }

  public void restoreAutoCommit() throws SQLException {
    connection.setAutoCommit(true);
  }

  public void commit() throws SQLException {
    connection.commit();
  }

  public void rollback() throws SQLException {
    connection.rollback();
  }

  @Override
  public void close() {
    DBConnectorPool.getInstance().releaseConnector(this);
  }
}
