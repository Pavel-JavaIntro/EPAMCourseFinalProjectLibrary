package by.pavka.library.model.dao;

import by.pavka.library.entity.LibraryEntity;

import java.util.Set;

public interface ManyToManyDao <E extends LibraryEntity, V extends LibraryEntity> extends LibraryDao<E> {
  Set<Integer> getFirst(int idV) throws DaoException;
  Set<Integer> getSecond(int idE) throws DaoException;
  void bind(int idE, int idV) throws DaoException;
}
