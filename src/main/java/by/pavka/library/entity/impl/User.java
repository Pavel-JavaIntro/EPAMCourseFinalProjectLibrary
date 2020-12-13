package by.pavka.library.entity.impl;

import by.pavka.library.entity.LibraryEntity;
import by.pavka.library.entity.LibraryEntityException;
import by.pavka.library.entity.criteria.EntityField;

/**
 * Extension of LibraryEntity
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class User extends LibraryEntity {
  public static final String ROLE_ID = "roleId";
  public static final String SURNAME = "surname";
  public static final String NAME = "name";
  public static final String ADDRESS = "address";
  public static final String PHONE = "phone";
  public static final String EMAIL = "email";
  public static final String PASSWORD = "password";

  @Override
  public EntityField[] listFields() {
    EntityField<Integer> roleId = new EntityField<>(ROLE_ID);
    EntityField<String> surname = new EntityField<>(SURNAME);
    EntityField<String> name = new EntityField<>(NAME);
    EntityField<String> address = new EntityField<>(ADDRESS);
    EntityField<String> phone = new EntityField<>(PHONE);
    EntityField<String> email = new EntityField<>(EMAIL);
    EntityField<Integer> password = new EntityField<>(PASSWORD);
    return new EntityField[] {roleId, surname, name, address, phone, email, password};
  }

  public int getRoleId() throws LibraryEntityException {
    EntityField<Integer> roleId = fieldForName(ROLE_ID);
    return roleId.getValue();
  }
  public String getSurname() throws LibraryEntityException {
    EntityField<String> surname = fieldForName(SURNAME);
    return surname.getValue();
  }
  public String getName() throws LibraryEntityException {
    EntityField<String> name = fieldForName(NAME);
    return name.getValue();
  }
  public String getEmail() throws LibraryEntityException {
    EntityField<String> email = fieldForName(EMAIL);
    return email.getValue();
  }
}
