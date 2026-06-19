package com.automationExercise.utils;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class Allure {

    public static void cleanAllureResults() {
        File resultsDir = new File("test-output/allure-results");
        File reportDir = new File("test-output/allure-report");

        try {
            FileUtils.deleteDirectory(resultsDir);
            FileUtils.deleteDirectory(reportDir);
        } catch (Exception e) {
            System.out.println("Failed to delete Allure folders: " + e.getMessage());
        }
    }
}