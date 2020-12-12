package by.pavka.library.model;

/**
 * Runtime exception thrown if the application cannot run at all
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class LibraryFatalException extends RuntimeException {
  public LibraryFatalException(String message) {
    super(message);
  }

  public LibraryFatalException(String message, Throwable cause) {
    super(message, cause);
  }
}
