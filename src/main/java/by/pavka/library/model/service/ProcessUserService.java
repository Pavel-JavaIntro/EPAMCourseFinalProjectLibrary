package by.pavka.library.model.service;

import by.pavka.library.entity.impl.User;

import java.util.List;

public interface ProcessUserService {
  List<User> findUsers(String surname, String name) throws ServiceException;

  void addUser(User user) throws ServiceException;

  void changeStatus(int userId, int roleId) throws ServiceException;
}
