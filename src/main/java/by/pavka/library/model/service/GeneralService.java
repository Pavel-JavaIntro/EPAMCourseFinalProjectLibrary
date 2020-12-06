package by.pavka.library.model.service;

import by.pavka.library.entity.impl.User;

public interface GeneralService {
  User auth(String surname, String name, String password) throws ServiceException;
}
