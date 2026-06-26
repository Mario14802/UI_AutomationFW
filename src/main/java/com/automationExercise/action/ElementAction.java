package com.automationExercise.action;

import com.automationExercise.utils.WaitManager;
import com.automationExercise.utils.logsmanager.LogsManager;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementAction {

    private WebDriver driver;
    private WaitManager waitmanager;

    public ElementAction(WebDriver driver) {
        this.driver = driver;
        this.waitmanager = new WaitManager(driver);
    }

    //Clicking
    public ElementAction click(By locator) {
        waitmanager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJs(locator);
                        // Wait until the element is stable (not moving)
                        Point initialLocation = element.getLocation();
                        LogsManager.info("initialLocation: " + initialLocation);
                        Point finalLocation = element.getLocation();
                        LogsManager.info("finalLocation: " + finalLocation);
                        if (!initialLocation.equals(finalLocation)) {
                            return false; // still moving, wait longer
                        }
                        try {
                            element.click();
                        } catch (ElementClickInterceptedException e) {
                            ((org.openqa.selenium.JavascriptExecutor) d).executeScript("arguments[0].click();", element);
                        } catch (ElementNotInteractableException e) {
                            ((org.openqa.selenium.JavascriptExecutor) d).executeScript("arguments[0].click();", element);
                        }
                        LogsManager.info("Clicked on element: " + locator);
                        return true;
                    } catch (Exception e) {
                        LogsManager.error("Failed to click on element: " + locator, e.getMessage());
                        return false;
                    }
                }
        );
        return this;
    }

    // type
    public ElementAction type(By locator, String text) {

        waitmanager.fluentWait().until(d -> {

            try {

                WebElement element = d.findElement(locator);

                scrollToElementJs(locator);

                element.clear();
                element.sendKeys(text);

                return true;

            } catch (Exception e) {
                LogsManager.error("failed to type ", e.getMessage());
                return false;
            }
        });
        return this;
    }

    // get text
    public String getText(By locator) {

        return waitmanager.fluentWait().until(d -> {

            try {

                WebElement element = d.findElement(locator);

                scrollToElementJs(locator);

                String msg = element.getText();

                return !msg.isEmpty() ? msg : null;

            } catch (Exception e) {
                LogsManager.error("failed to get text ", e.getMessage());
                return null;
            }
        });
    }

    //select from dropdown
    public ElementAction selectFromDropdown(By locator, String value) {
        waitmanager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJs(locator);
                        Select select = new Select(element);
                        select.selectByVisibleText(value);
                        LogsManager.info("Selected value '" + value + "' from dropdown: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    public ElementAction uploadFile(By locator, String Filepath) {

        String fileabsolute = ("user.dir") + File.separator + Filepath;


        waitmanager.fluentWait().until(d -> {

            try {

                WebElement element = d.findElement(locator);
                scrollToElementJs(locator);
                element.sendKeys(fileabsolute);

                return true;

            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }

    //hovering
    public ElementAction hover(By locator) {
        waitmanager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJs(locator);
                        new Actions(d).moveToElement(element).perform();
                        LogsManager.info("Hovered over element: " + locator);
                        return true;
                    } catch (Exception e) {
                        LogsManager.error("Failed to click on element: " + locator, e.getMessage());
                        return false;
                    }
                }
        );
        return this;
    }


    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Scrolls the page until the target element is visible.
     *
     * Why use JavaScript scrolling?
     * -----------------------------
     * Some elements may exist in the DOM but be outside the visible viewport.
     * Selenium may fail to click them reliably until they are brought into view.
     *
     * Using block:'center' places the element in the middle of the screen:
     *
     * Before scrolling:
     *
     * -------------------
     * |                 |
     * |                 |
     * |                 |
     * |     Button      |
     * -------------------
     *
     * After scrolling:
     *
     * -------------------
     * |                 |
     * |                 |
     * |    Button       |
     * |                 |
     * -------------------
     *
     * This is usually more reliable than scrolling the element to the very top.
     */
    /**
     * Scrolls until the target element becomes visible.
     * <p>
     * block:'center' places the element in the middle of the viewport.
     */
    public void scrollToElementJs(By locator) {

        WebElement element = driver.findElement(locator);

        Boolean isWithinViewport = (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "var rect = arguments[0].getBoundingClientRect();" +
                        "return (" +
                        "  rect.width > 0 &&" +
                        "  rect.height > 0 &&" +
                        "  rect.top >= 0 &&" +
                        "  rect.left >= 0 &&" +
                        "  rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&" +
                        "  rect.right <= (window.innerWidth || document.documentElement.clientWidth)" +
                        ");",
                        element
                );

        if (isWithinViewport == null || !isWithinViewport) {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            element
                    );
        }
    }


}

