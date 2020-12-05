package by.pavka.library.model.util.converter;

public class ConverterFactory {
  private static final ConverterFactory INSTANCE = new ConverterFactory();
  private FieldColumnConverter converter = new DefaultFieldColumnConverter();

  private ConverterFactory() {
  }

  public static ConverterFactory getInstance() {
    return INSTANCE;
  }

  public FieldColumnConverter getConverter() {
    return converter;
  }
}
