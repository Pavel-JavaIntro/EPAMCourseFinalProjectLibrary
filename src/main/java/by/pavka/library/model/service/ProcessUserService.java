package by.pavka.library.model.service;

import by.pavka.library.entity.impl.User;

import java.util.List;

/**
 * Interface providing librarian level methods related to users processing
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public interface ProcessUserService {
  List<User> findUsers(String surname, String name) throws ServiceException;

  void addUser(User user) throws ServiceException;

  void changeStatus(int userId, int roleId) throws ServiceException;
}
