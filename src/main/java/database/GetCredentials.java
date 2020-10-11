package database;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class GetCredentials {

    public static Map<String, String> readCredentials(File credentialsFile) throws ParseException, IOException {
        Map<String, String> dbCredentials = new HashMap<>();
        JSONParser jsonParser = new JSONParser();
        Reader reader = new FileReader(credentialsFile);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        dbCredentials.put("serverURL", jsonObject.get("serverURL").toString());
        dbCredentials.put("serverPort", jsonObject.get("serverPort").toString());
        dbCredentials.put("database", jsonObject.get("database").toString());
        dbCredentials.put("user", jsonObject.get("user").toString());
        dbCredentials.put("password", jsonObject.get("password").toString());
        return dbCredentials;
    }

}
