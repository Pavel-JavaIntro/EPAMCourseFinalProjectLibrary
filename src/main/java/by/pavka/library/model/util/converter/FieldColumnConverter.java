package by.pavka.library.model.util.converter;

import by.pavka.library.entity.criteria.EntityField;

/**
 * Interface converting an entity field into a table column name
 *
 * @author Pavel Kassitchev
 * @version 1,0
 */
public interface FieldColumnConverter {
  String formColumnName(EntityField<?> field);
}
