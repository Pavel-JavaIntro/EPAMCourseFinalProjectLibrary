package by.pavka.library.entity.impl;

import by.pavka.library.entity.LibraryEntity;
import by.pavka.library.entity.criteria.EntityField;

public class Author extends LibraryEntity {
  public static final String SURNAME = "surname";
  public static final String FIRST_NAME = "firstName";
  public static final String SECOND_NAME = "secondName";

  @Override
  public EntityField[] listFields() {
    EntityField<String> surname = new EntityField<>(SURNAME);
    EntityField<String> firstName = new EntityField<>(FIRST_NAME);
    EntityField<String> secondName = new EntityField<>(SECOND_NAME);
    return new EntityField[] {surname, firstName, secondName};
  }
}
