package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class propertyGenerator {


    private static Properties prop = new Properties();

    static {
        InputStream in = Object.class.getResourceAsStream("/attribute.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key){
        return prop.getProperty(key);
    }
}
