package by.pavka.library.entity;

import by.pavka.library.entity.criteria.EntityField;

public abstract class SimpleListEntity extends LibraryEntity {
  public static final String DESCRIPTION = "description";

  @Override
  public EntityField<String>[] listFields() {
    EntityField<String> description = new EntityField<>(DESCRIPTION);
    return new EntityField[]{description};
  }

  public String getDescription() {
    return (String)(getFields()[0].getValue());
  }
}
