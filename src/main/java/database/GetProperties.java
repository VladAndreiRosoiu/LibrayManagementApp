package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {
    public static Properties getDBProperties(File propertiesFile){
        Properties properties=new Properties();
        try(InputStream inputStream = new FileInputStream(propertiesFile)){
            properties.load(inputStream);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return properties;
    }
}
