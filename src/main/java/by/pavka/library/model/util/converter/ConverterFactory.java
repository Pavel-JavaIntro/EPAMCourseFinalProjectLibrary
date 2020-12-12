package by.pavka.library.model.util.converter;

/**
 * Simple factory returning a singleton instance
 *
 * @author Pavel Kassitchev
 * @version 1,0
 */
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
