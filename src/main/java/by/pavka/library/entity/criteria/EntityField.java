package by.pavka.library.entity.criteria;

import java.io.Serializable;

public class EntityField<T> implements Serializable {
  private final String name;
  private T value;

  public EntityField(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("%s : %s", name, value);
  }
}
