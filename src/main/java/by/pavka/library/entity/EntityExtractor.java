package by.pavka.library.entity;

/**
 * Interface that produces an object of a LibraryEntity class from a ResultSet
 *
 * @param <T> is a concrete LibraryEntity class
 * @author Pavel Kassitchev
 * @version 1.0
 */
public interface EntityExtractor<T extends LibraryEntity> {
  T extractEntity();
}
