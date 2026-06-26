package com.automationExercise.action;

import com.automationExercise.utils.WaitManager;
import com.automationExercise.utils.logsmanager.LogsManager;
import com.automationExercise.utils.readers.PropertyReader;
import org.openqa.selenium.WebDriver;

public class BrowserActions {

    private final WebDriver driver;
    private final WaitManager waitmanager;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
        this.waitmanager = new WaitManager(driver);

    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
        LogsManager.info("Maximaize windows");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();

    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
        LogsManager.info("Url" + url);
    }

    public void refresh() {
        driver.navigate().refresh();
        LogsManager.info("refresh");
    }

    public void closeWindow() {
        driver.close();
        LogsManager.info("closeWindow");
    }

    public void openNewWindow(String url) {
        driver.switchTo().window(url);
        LogsManager.info("openNewWindow");
    }

    //close extension tab
    public void closeExtensionTab() {
        if (PropertyReader.getProperty("ignoreExtensions").equalsIgnoreCase("enable")) {
            String currentWindowHandle = driver.getWindowHandle(); //0 1
            waitmanager.fluentWait().until(
                    d ->
                    {
                        return d.getWindowHandles().size() > 1; //wait until extension tab is opened
                    }
            );
            for (String windowHandle : driver.getWindowHandles()) //extension tab is opened
            {
                if (!windowHandle.equals(currentWindowHandle))
                    driver.switchTo().window(windowHandle).close(); //close the extension tab
            }
            driver.switchTo().window(currentWindowHandle); //switch back to the main window
            LogsManager.info("Extension tab closed");
        }

    }

}
