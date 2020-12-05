package by.pavka.library.entity.impl;

import by.pavka.library.entity.LibraryEntity;
import by.pavka.library.entity.criteria.EntityField;

import java.time.LocalDate;

public class Book extends LibraryEntity {
  public static final String EDITION_ID = "editionId";
  public static final String LOCATION_ID = "locationId";
  public static final String READER_ID = "readerId";
  public static final String STANDARD_LOCATION_ID = "standardLocationId";
  public static final String RESERVED = "reserved";
  public static final String DUE_DATE = "dueDate";

  @Override
  public EntityField[] listFields() {
    EntityField<Integer> editionId = new EntityField<>(EDITION_ID);
    EntityField<Integer> locationId = new EntityField<>(LOCATION_ID);
    EntityField<Integer> readerId = new EntityField<>(READER_ID);
    EntityField<Integer> standardLocationId = new EntityField<>(STANDARD_LOCATION_ID);
    EntityField<Integer> reserved = new EntityField<>(RESERVED);
    EntityField<LocalDate> dueDate = new EntityField<>(DUE_DATE);
    return new EntityField[] {editionId, reserved, locationId, readerId, dueDate,
            standardLocationId};
  }

}
