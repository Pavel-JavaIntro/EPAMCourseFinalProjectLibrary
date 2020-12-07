package by.pavka.library.model.service.impl;

import by.pavka.library.entity.LibraryEntityException;
import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.criteria.EntityField;
import by.pavka.library.entity.impl.Author;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.entity.impl.Edition;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.model.DBConnectorPool;
import by.pavka.library.model.dao.*;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.util.TableEntityMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class UtilService {

  UtilService() {}

    void bindAuthors(EditionInfo info, DBConnector connector) throws DaoException, LibraryEntityException {
    ManyToManyDao<Edition, Author> editionDao = LibraryDaoFactory.getManyToManyDao(connector);
    LibraryDao<Author> authorDao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.AUTHOR, connector);
    Set<Author> authors = new HashSet<>();
    Set<Integer> authorIds = editionDao.getSecond(info.getEdition().getId());
    for (int id : authorIds) {
      authors.add(authorDao.get(id));
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (Author a : authors) {
      stringBuilder.append(a.fieldForName(Author.SURNAME).getValue()).append(" ");
    }
    info.setAuthors(stringBuilder.toString());
  }

  List<Book> findBooksByEdition(int id) throws ServiceException {
    List<Book> result = new ArrayList<>();
    try (DBConnector connector = DBConnectorPool.getInstance().obtainConnector()) {
      LibraryDao<Book> dao = LibraryDaoFactory.getLibraryDao(TableEntityMapper.BOOK, connector);
      Criteria criteria = new Criteria();
      EntityField<Integer> edId = new EntityField<>(Book.EDITION_ID);
      edId.setValue(id);
      criteria.addConstraint(edId);
      result.addAll(dao.read(criteria, true));
    } catch (DaoException e) {
      throw new ServiceException("Cannot find books", e);
    }
    return result;
  }
}
