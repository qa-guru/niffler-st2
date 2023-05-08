package niffler.jupiter.annotation;

import niffler.jupiter.extension.ClassPathUserToConverter;
import org.junit.jupiter.params.converter.ConvertWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ConvertWith(ClassPathUserToConverter.class)
public @interface ClassPathUser {

}
