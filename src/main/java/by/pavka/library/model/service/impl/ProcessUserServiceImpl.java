package by.pavka.library.model.service.impl;

import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.criteria.EntityField;
import by.pavka.library.entity.impl.User;
import by.pavka.library.model.DBConnectorPool;
import by.pavka.library.model.dao.DBConnector;
import by.pavka.library.model.dao.DaoException;
import by.pavka.library.model.dao.LibraryDao;
import by.pavka.library.model.dao.LibraryDaoFactory;
import by.pavka.library.model.service.ProcessUserService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.util.TableEntityMapper;

import java.util.List;

/**
 * Implementation of the relevant interface
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ProcessUserServiceImpl implements ProcessUserService {

  ProcessUserServiceImpl() {}

  @Override
  public List<User> findUsers(String surname, String name) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.USER, connector);
      EntityField<String> surnameField = new EntityField<>(User.SURNAME);
      surnameField.setValue(surname);
      EntityField<String> nameField = new EntityField<>(User.NAME);
      nameField.setValue(name);
      Criteria criteria = new Criteria();
      criteria.addConstraints(surnameField, nameField);
      return userDao.read(criteria, true);
    } catch (DaoException e) {
      throw new ServiceException("Cannot find users", e);
    }
  }

  @Override
  public void addUser(User user) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.USER, connector);
      userDao.add(user);
    } catch (DaoException e) {
      throw new ServiceException("Cannot add users", e);
    }
  }

  @Override
  public void changeStatus(int userId, int roleId) throws ServiceException {
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.USER, connector);
      EntityField<Integer> field = new EntityField<>(User.ROLE_ID);
      field.setValue(roleId);
      userDao.update(userId, field);
    } catch (DaoException e) {
      throw new ServiceException("Cannot change user status", e);
    }
  }
}
