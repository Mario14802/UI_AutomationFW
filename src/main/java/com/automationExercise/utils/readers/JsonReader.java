package com.automationExercise.utils.readers;

import com.automationExercise.utils.logsmanager.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {

    private final String test_data_path = "src/test/resources/test-data/";

    String jsonFileName;
    String jsonReader;

          public JsonReader(String jsonFileName)
          {
              this.jsonFileName = jsonFileName;
              try {
                  JSONObject data = (JSONObject) new JSONParser()
                          .parse(new FileReader(test_data_path+jsonFileName+".json"));
                  jsonReader = data.toString();

              }
              catch (Exception e)
              {
                  LogsManager.error("Failed to load file: " + jsonFileName + " -> " + e.getMessage());


              }

          }

          public String getJsonData(String jsonPath)
          {
              try {
                   return JsonPath.read(jsonReader, jsonPath);
              }
              catch (Exception e)
              {
                  LogsManager.error("Failed to read file: " + jsonFileName + " -> " + e.getMessage());
                  return "";
              }

          }

}
