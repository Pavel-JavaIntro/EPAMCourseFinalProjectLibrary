package by.pavka.library.model.dao;

/**
 * Exception
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class DaoException extends Exception {
  public DaoException(String message) {
    super(message);
  }

  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }
}
