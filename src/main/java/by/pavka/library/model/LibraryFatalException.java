package by.pavka.library.model;

public class LibraryFatalException extends RuntimeException {
  public LibraryFatalException(String message) {
    super(message);
  }

  public LibraryFatalException(String message, Throwable cause) {
    super(message, cause);
  }
}
