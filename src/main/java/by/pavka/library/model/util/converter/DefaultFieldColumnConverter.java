package by.pavka.library.model.util.converter;

import by.pavka.library.entity.criteria.EntityField;

public class DefaultFieldColumnConverter implements FieldColumnConverter {

  @Override
  public String formColumnName(EntityField<?> field) {
    String fieldName = field.getName();
    String regex = "([a-z0-9])([A-Z]+)";
    String replacement = "$1_$2";

    return fieldName.replaceAll(regex, replacement).toLowerCase();
  }

  @Override
  public String formFieldName(String column) {
    while (column.contains("_")) {
      String replacement =
          String.valueOf(Character.toUpperCase(column.charAt(column.indexOf("_") + 1)));
      column = column.replaceFirst("_[a-z]", replacement);
    }
    return column;
  }
}
