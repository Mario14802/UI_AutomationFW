package com.automationExercise.utils.readers;

import com.automationExercise.utils.logsmanager.LogsManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {

    /**
     * Loads all .properties files from src/main/resources
     * and makes them available as JVM system properties.
     *
     * Example:
     * browser=chrome
     * baseUrl=https://blazedemo.com
     *
     * After loading:
     * System.getProperty("browser") -> chrome
     */
    public static Properties loadProperties() {

        try {

            // Stores all properties loaded from files.
            Properties properties = new Properties();

            // Recursively search for all properties files
            // inside src/main/resources and its subdirectories.
            Collection<File> files = FileUtils.listFiles(
                    new File("src/main/resources"),
                    new String[]{"properties"},
                    true
            );

            // Load every discovered properties file into
            // a single Properties object.
            files.forEach(file -> {

                try {
                    properties.load(
                            FileUtils.openInputStream(file)
                    );

                } catch (Exception e) {
                    LogsManager.error("Failed to load file: " + file.getName() + " -> " + e.getMessage());
                }
            });

            /*
             * Copy loaded properties to JVM system properties.
             *
             * This allows accessing values globally using:
             * System.getProperty("propertyName")
             *
             * Example:
             * System.getProperty("browser")
             */
            System.getProperties().putAll(properties);

            return properties;

        } catch (Exception e) {
            LogsManager.error("Failed to load properties: " + e.getMessage());
            return null;
        }
    }

    public static String getProperty(String key) {
        try {
            return System.getProperty(key);
        }
        catch (Exception e)
        {
            System.out.println("Error getting property: " + e.getMessage());
            return "";
        }

    }



}