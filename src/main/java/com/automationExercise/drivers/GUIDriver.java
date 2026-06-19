package com.automationExercise.drivers;


import com.automationExercise.action.AlertsAction;
import com.automationExercise.action.BrowserActions;

import com.automationExercise.action.ElementAction;
import com.automationExercise.action.FrameActions;
import com.automationExercise.utils.logsmanager.LogsManager;
import com.automationExercise.utils.readers.PropertyReader;
import com.automationExercise.validations.SoftAssertion;
import com.automationExercise.validations.HardAssertion;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final String browser = PropertyReader.getProperty("browser");
    private  ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public GUIDriver()
    {
        LogsManager.info("Initializing GUIDriver with browser: " + browser);
        System.out.println("Initializing GUIDriver with browser: " + browser);
        Browsers browserType = Browsers.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser: " + browserType);
        AbstractDriver abstractDriver = browserType.getDriverFactory(); //local
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public ElementAction element() {
        return new ElementAction(get());
    }
    public BrowserActions browser() {
        return new BrowserActions(get());
    }
    public FrameActions frame() {
        return new FrameActions(get());
    }
    public AlertsAction alert() {
        return new AlertsAction(get());
    }
    //soft assertions
    public SoftAssertion validation() {
        return new SoftAssertion(get());
    }
    // hard assertions
    public HardAssertion verification() {
        return new HardAssertion(get());
    }
    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public  void quitDriver() {
        driverThreadLocal.get().quit();
    }
}
