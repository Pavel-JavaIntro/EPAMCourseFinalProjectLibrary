package by.pavka.library.model.dao;

public class DaoException extends Exception {
  public DaoException(String message) {
    super(message);
  }

  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }
}
