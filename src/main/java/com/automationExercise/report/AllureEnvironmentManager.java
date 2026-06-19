package com.automationExercise.report;

import com.automationExercise.utils.logsmanager.LogsManager;
import com.google.common.collect.ImmutableMap;

import java.io.File;

import static com.automationExercise.utils.readers.PropertyReader.getProperty;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureEnvironmentManager {
    public static void setEnvironmentVariables() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", getProperty("os.name"))
                        .put("Java version:", getProperty("java.runtime.version"))
                        .put("Browser", getProperty("browser"))
                        .put("Execution Type", getProperty("executionType"))
                        .put("URL", getProperty("BASE_URL"))
                        .build(), String.valueOf(AllureConstants.RESULTS_FOLDER) + File.separator
        );
        LogsManager.info("Allure environment variables set.");
        AllureBinaryManager.downloadAndExtract();
    }
}

