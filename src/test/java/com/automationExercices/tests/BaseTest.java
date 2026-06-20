package com.automationExercices.tests;

import com.automationExercise.drivers.DriverManager;
import com.automationExercise.drivers.WebDriverProvider;
import com.automationExercise.utils.readers.JsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {

    protected DriverManager driver;
    protected JsonReader testData;
    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
