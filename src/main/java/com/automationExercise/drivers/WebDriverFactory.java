package com.automationExercise.drivers;

import com.automationExercise.utils.logsmanager.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class WebDriverFactory {

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();
    private static final String browser=System.getProperty("browser");

    private static WebDriver getDriver( ) {
        Browsers browserEnum = Browsers.valueOf(browser.toUpperCase());
        LogsManager.info("Browser:" + browserEnum.name()+ "Starts" );
        AbstractDriver driver = browserEnum.getDriverFactory();
        return driver.createDriver();
    }


    public static WebDriver initDriver() {
        WebDriver driver = ThreadGuard.protect(getDriver());
        threadDriver.set(driver);
        return threadDriver.get();
    }

    public static WebDriver get()
    {
        return threadDriver.get();
    }

    public static void quitDriver() {
        threadDriver.get().quit();
    }

}
