package niffler.jupiter.extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import niffler.model.UserJson;
import niffler.test.LoginTest;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.io.IOException;

public class ClassPathUserToConverter implements ArgumentConverter {

    private ClassLoader cl = ClassPathUserToConverter.class.getClassLoader();

    private static ObjectMapper om = new ObjectMapper();
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
            throw new ArgumentConversionException("Only string source supported");
        }
    }
}
