package test.pavka.library.model.util.converter;

import by.pavka.library.entity.criteria.EntityField;
import by.pavka.library.model.util.converter.ConverterFactory;
import by.pavka.library.model.util.converter.FieldColumnConverter;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test.pavka.library.TestListener;

@Listeners(TestListener.class)
public class DefaultFieldColumnConverterTest {
  private FieldColumnConverter converter;

  @BeforeTest
  public void setUp() {
    converter = ConverterFactory.getInstance().getConverter();
  }

  @Test
  public void converterTest() {
    String fieldName = "justLowerCamelCase";
    EntityField<Integer> field = new EntityField<>(fieldName);
    String actual = converter.formColumnName(field);
    String expected = "just_lower_camel_case";
    Assert.assertEquals(actual, expected);
  }

  @AfterTest
  public void clean() {
    converter = null;
  }
}
