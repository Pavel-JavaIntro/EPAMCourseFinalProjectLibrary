package by.pavka.library.model.service;

/**
 * Exception
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ServiceException extends Exception {
  public ServiceException() {
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
