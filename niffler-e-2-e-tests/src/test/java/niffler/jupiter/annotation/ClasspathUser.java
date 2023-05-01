package niffler.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import niffler.jupiter.extension.ClasspathUserUpdateConverter;
import org.junit.jupiter.params.converter.ConvertWith;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ConvertWith(ClasspathUserUpdateConverter.class)
public @interface ClasspathUser {

}
