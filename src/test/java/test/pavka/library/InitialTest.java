package test.pavka.library;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
@Test
public class InitialTest {
  public void simple() {
    String actual = "haha";
    String expected = "ha" + "ha";
    Assert.assertEquals(actual, expected);
  }

  public void complex() {
    String actual = new String("haha").intern();
    String expected = "ha" + "ha";
    Assert.assertSame(actual, expected);
  }
}
