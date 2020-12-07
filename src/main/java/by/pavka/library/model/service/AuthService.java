package by.pavka.library.model.service;

import by.pavka.library.entity.impl.User;

public interface AuthService {
  User auth(String surname, String name, String password) throws ServiceException;
}
