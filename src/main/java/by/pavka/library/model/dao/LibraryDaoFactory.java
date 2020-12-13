package by.pavka.library.model.dao;

import by.pavka.library.entity.LibraryEntity;
import by.pavka.library.entity.impl.Author;
import by.pavka.library.entity.impl.Edition;
import by.pavka.library.model.dao.impl.LibraryDaoImpl;
import by.pavka.library.model.dao.impl.ManyToManyDaoImpl;
import by.pavka.library.model.util.TableEntityMapper;

/**
 * simple factory returning a new instance
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class LibraryDaoFactory {

  public static <T extends LibraryEntity> LibraryDao<T> getLibraryDao(TableEntityMapper mapper, DBConnector connector) throws DaoException {
    return new LibraryDaoImpl<>(mapper, connector);
  }

  public static ManyToManyDao<Edition, Author> getManyToManyDao(DBConnector connector) throws DaoException {
    return new ManyToManyDaoImpl(connector);
  }
}
