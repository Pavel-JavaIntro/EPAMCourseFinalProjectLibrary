package by.pavka.library.model.dao;

import by.pavka.library.entity.LibraryEntity;
import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.criteria.EntityField;

import java.util.List;

public interface LibraryDao<T extends LibraryEntity> extends AutoCloseable {
  int add(T entity) throws DaoException;
  List<T> read(Criteria criteria, boolean strict) throws DaoException;
  List<T> read() throws DaoException;
  T get(int id) throws DaoException;
  void remove(int id) throws DaoException;
  void update(int id, EntityField<?> fields) throws DaoException;
  boolean contains(Criteria criteria, boolean strict)throws DaoException;
  @Override
  void close();


}
