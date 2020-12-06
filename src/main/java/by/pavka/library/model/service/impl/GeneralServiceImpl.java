package by.pavka.library.model.service.impl;

import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.criteria.EntityField;
import by.pavka.library.entity.impl.User;
import by.pavka.library.model.DBConnectorPool;
import by.pavka.library.model.dao.DBConnector;
import by.pavka.library.model.dao.DaoException;
import by.pavka.library.model.dao.LibraryDao;
import by.pavka.library.model.dao.LibraryDaoFactory;
import by.pavka.library.model.service.GeneralService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.util.TableEntityMapper;

import java.util.List;

public class GeneralServiceImpl implements GeneralService {

  GeneralServiceImpl() {}

  @Override
  public User auth(String surname, String name, String password) throws ServiceException {
    int hashPass = password.hashCode();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<User> userDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.USER, connector);
      Criteria criteria = new Criteria();
      EntityField<String> surnameField = new EntityField<>(User.SURNAME);
      surnameField.setValue(surname);
      EntityField<String> nameField = new EntityField<>(User.NAME);
      nameField.setValue(name);
      EntityField<Integer> passField = new EntityField<>(User.PASSWORD);
      passField.setValue(hashPass);
      criteria.addConstraints(surnameField, nameField, passField);
      List<User> users = userDao.read(criteria, true);
      if (users.size() > 0) {
        return users.get(0);
      } else {
        return null;
      }
    } catch (DaoException e) {
      throw new ServiceException("Cannot authorize the user", e);
    }
  }
}
