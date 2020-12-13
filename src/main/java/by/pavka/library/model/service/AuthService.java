package by.pavka.library.model.service;

import by.pavka.library.entity.impl.User;

/**
 * Interface providing authentication method that returns null if the authentication failed.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public interface AuthService {
  User auth(String surname, String name, String password) throws ServiceException;
}
