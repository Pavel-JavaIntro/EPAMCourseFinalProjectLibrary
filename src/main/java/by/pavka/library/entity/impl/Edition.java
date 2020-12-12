package by.pavka.library.entity.impl;

import by.pavka.library.entity.LibraryEntity;
import by.pavka.library.entity.criteria.EntityField;

/**
 * Extension of LibraryEntity
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class Edition extends LibraryEntity {
  public static final String STANDARD_NUMBER = "standardNumber";
  public static final String TITLE = "title";
  public static final String YEAR = "year";
  public static final String GENRE_ID = "genreId";
  public static final String DELIVERIES = "deliveries";

  @Override
  public EntityField[] listFields() {
    EntityField<String> standardNumber = new EntityField<>(STANDARD_NUMBER);
    EntityField<String> title = new EntityField<>(TITLE);
    EntityField<Integer> year = new EntityField<>(YEAR);
    EntityField<Integer> genreId = new EntityField<>(GENRE_ID);
    EntityField<Integer> deliveries = new EntityField<>(DELIVERIES);
    return new EntityField[] {standardNumber, title, year, genreId, deliveries};
  }
}
