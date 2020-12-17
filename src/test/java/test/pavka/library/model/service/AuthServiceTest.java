package test.pavka.library.model.service;

import by.pavka.library.entity.impl.User;
import by.pavka.library.model.service.AuthService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.AuthServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test.pavka.library.TestListener;

@Listeners(TestListener.class)
public class AuthServiceTest {
  private AuthService authService = Mockito.mock(AuthService.class);

  @Test
  public void createUser() {
    try {
      User actual = authService.auth("Some", "some", "something");
      Assert.assertNull(actual);
    } catch (ServiceException e) {
      Assert.fail();
    }
  }
}
