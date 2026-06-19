package com.automationExercise.drivers;

import com.automationExercise.utils.logsmanager.LogsManager;
import com.automationExercise.utils.readers.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class ChromeFactory extends AbstractDriver {
    private ChromeOptions getoptions() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("remote") ||
        PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless"))
        {
            options.addArguments("--headless");
        }

        return options;
    }

    @Override
    public WebDriver createDriver() {

        return switch (PropertyReader.getProperty("executionType").toLowerCase()) {

            case "local", "localheadless" ->
                    new ChromeDriver(getoptions());

            case "remote" -> {
                try {
                    yield new RemoteWebDriver(
                            new URI("http://"
                                    + PropertyReader.getProperty("remoteHost")
                                    + ":"
                                    + PropertyReader.getProperty("remotePort")
                                    + "/wd/hub")
                                    .toURL(),
                            getoptions()
                    );
                } catch (Exception e) {
                    LogsManager.error("Error creating RemoteWebDriver: " + e.getMessage());
                    throw new RuntimeException("Failed to create RemoteWebDriver", e);
                }
            }

            default -> throw new IllegalArgumentException(
                    "Invalid execution type: "
                            + PropertyReader.getProperty("executionType"));
        };
    }

}
