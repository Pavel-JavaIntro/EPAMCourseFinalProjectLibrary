package by.pavka.library.model.util.converter;

import by.pavka.library.entity.criteria.EntityField;

/**
 * Default implementation of FieldColumnConvertor converting a camelcase field name into underscore-case column name
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class DefaultFieldColumnConverter implements FieldColumnConverter {

  @Override
  public String formColumnName(EntityField<?> field) {
    String fieldName = field.getName();
    String regex = "([a-z0-9])([A-Z]+)";
    String replacement = "$1_$2";

    return fieldName.replaceAll(regex, replacement).toLowerCase();
  }
}
