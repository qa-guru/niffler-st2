package niffler.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {

    private final Properties property;
    private final String endPoint;

    public PropertyHandler(String endPoint) {
        property = new Properties();
        this.endPoint = endPoint;
    }

    public String get(String name){
        try (FileInputStream fis = new FileInputStream(endPoint)){
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) property.get(name);
    }

}
