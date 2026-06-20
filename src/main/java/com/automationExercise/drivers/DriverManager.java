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

public class DriverManager {
    private final String browser = PropertyReader.getProperty("browser");
    private  ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public DriverManager()
    {
        LogsManager.info("Initializing DriverManager with browser: " + browser);
        //Get the browser from properties
        Browsers browserType = Browsers.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser: " + browserType);
        //get the driver of the needed browser
        AbstractDriver abstractDriver = browserType.getDriverFactory(); //local
        //ThreadLocal answers: "Which driver belongs to this thread?"
        //ThreadGuard answers: "Is the current thread allowed to use this driver is it the creator of the object or driver?"
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    //get the exact driver form the thread
    public WebDriver get() {
        return driverThreadLocal.get();
    }

    // return the object type to able to make method chaining
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


    public  void quitDriver() {
        driverThreadLocal.get().quit();
    }
}
