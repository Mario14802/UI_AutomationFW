package com.automationExercise.action;

import com.automationExercise.utils.logsmanager.LogsManager;
import org.openqa.selenium.WebDriver;

public class BrowserActions {

    private final WebDriver driver;
    public BrowserActions(WebDriver driver) {
        this.driver = driver;

    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
        LogsManager.info("Maximaize windows");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();

    }

    public void navigateTo(String url)
    {
        driver.navigate().to(url);
        LogsManager.info("Url"+url);
    }

    public void refresh()
    {
       driver.navigate().refresh();
        LogsManager.info("refresh");
    }

    public void closeWindow()
    {
        driver.close();
        LogsManager.info("closeWindow");
    }

    public void openNewWindow(String url)
    {
        driver.switchTo().window(url);
        LogsManager.info("openNewWindow");
    }



}
