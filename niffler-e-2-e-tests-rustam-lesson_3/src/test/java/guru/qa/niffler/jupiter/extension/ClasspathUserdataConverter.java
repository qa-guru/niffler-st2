package guru.qa.niffler.jupiter.extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.niffler.model.UserJson;
import java.io.IOException;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

public class ClasspathUserdataConverter implements ArgumentConverter {

  private static ObjectMapper om = new ObjectMapper();
  private ClassLoader cl = ClasspathUserdataConverter.class.getClassLoader();

  @Override
  public UserJson convert(Object source, ParameterContext context)
      throws ArgumentConversionException {
    if (source instanceof String stringSource) {
      try {
        return om.readValue(cl.getResourceAsStream(stringSource), UserJson.class);
      } catch (IOException e) {
        throw new ArgumentConversionException(e.getMessage());
      }
    } else {
      throw new ArgumentConversionException("Only string source supported!");
    }

  }
}
