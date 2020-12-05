package by.pavka.library.entity;

import by.pavka.library.entity.criteria.EntityField;

import java.io.Serializable;
import java.util.Objects;

public abstract class LibraryEntity implements Serializable {
  private final EntityField<?>[] fields;
  private int id;

  public LibraryEntity() {
    fields = listFields();
  }

  public abstract EntityField[] listFields();

  public EntityField[] getFields() {
    return fields;
  }

  public EntityField fieldForName(String name) throws LibraryEntityException{
    for (EntityField field : fields) {
      if (field.getName().equals(name)) {
        return field;
      }
    }
    throw new LibraryEntityException("EntityField for name " + name + " doesn't exist");
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public <T> void setValue(String name, T value) {
    for (EntityField field : fields) {
      if (field.getName().equals(name)) {
        fillField(field, value);
        break;
      }
    }
  }

  private <T> void fillField(EntityField<T> field, T value) {
    field.setValue(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    LibraryEntity that = (LibraryEntity) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id);
    result = 31 * result + getClass().hashCode();
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(getClass().getSimpleName() + " {\n");
    builder.append("id=").append(id).append('\n');
    for (EntityField field : fields) {
      builder.append(field.getName()).append("=").append(field.getValue()).append('\n');
    }
    builder.append('}');
    return builder.toString();
  }
}
