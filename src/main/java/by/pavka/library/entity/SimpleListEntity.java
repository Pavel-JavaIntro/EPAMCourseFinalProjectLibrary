package by.pavka.library.entity;

import by.pavka.library.entity.criteria.EntityField;

/**
 * Extension of LibraryEntity class with only two fields - int id and String description
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public abstract class SimpleListEntity extends LibraryEntity {
  public static final String DESCRIPTION = "description";

  @Override
  public EntityField<String>[] listFields() {
    EntityField<String> description = new EntityField<>(DESCRIPTION);
    return new EntityField[]{description};
  }

  public String getDescription() {
    return (String) (getFields()[0].getValue());
  }
}
