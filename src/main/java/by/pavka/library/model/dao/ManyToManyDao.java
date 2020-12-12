package by.pavka.library.model.dao;

import by.pavka.library.entity.LibraryEntity;

import java.util.Set;

/**
 * The interface serves relation Many-toMany
 *
 * @param <E>
 * @param <V>
 * @author Pavel Kassitchev
 * @version 1.0
 */
public interface ManyToManyDao<E extends LibraryEntity, V extends LibraryEntity> extends LibraryDao<E> {
  Set<Integer> getFirst(int idV) throws DaoException;

  Set<Integer> getSecond(int idE) throws DaoException;

  void bind(int idE, int idV) throws DaoException;
}
