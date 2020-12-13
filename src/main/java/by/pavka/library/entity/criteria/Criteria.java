package by.pavka.library.entity.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents search criteria constraining database queries.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class Criteria implements Serializable {
  private final List<EntityField<?>> constraints = new ArrayList<>();

  public EntityField getConstraint(int index) {
    return constraints.get(index);
  }

  public void addConstraint(EntityField field) {
    constraints.add(field);
  }

  public void addConstraints(EntityField... fields) {
    for (EntityField field : fields) {
      constraints.add(field);
    }
  }

  public int size() {
    return constraints.size();
  }

  @Override
  public String toString() {
    return constraints.toString();
  }
}
