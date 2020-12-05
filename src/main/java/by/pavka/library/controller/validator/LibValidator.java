package by.pavka.library.controller.validator;

public class LibValidator {
  private LibValidator() {}

  public static boolean validateLogin(String surname, String name, String pass) {
    return (surname != null
        && name != null
        && pass != null
        && !surname.isEmpty()
        && !name.isEmpty()
        && pass.length() > 4);
  }
}
