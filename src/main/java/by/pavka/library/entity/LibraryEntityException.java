package by.pavka.library.entity;

public class LibraryEntityException extends Exception{
  public LibraryEntityException() {
  }

  public LibraryEntityException(String message) {
    super(message);
  }

  public LibraryEntityException(String message, Throwable cause) {
    super(message, cause);
  }
}
