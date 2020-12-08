package by.pavka.library.controller.validator;

/**
 * LibValidator
 * <p>
 * This is a primitive validator to preliminary validate the user's input for authentication.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class LibValidator {
  private LibValidator() {
  }

  public static boolean validateLogin(String surname, String name, String pass) {
    return (surname != null
        && name != null
        && pass != null
        && !surname.isEmpty()
        && !name.isEmpty()
        && pass.length() > 4);
  }
}
