package com.automationExercise.validations;

import com.automationExercise.utils.logsmanager.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

//Soft assertion
public class SoftAssertion extends BaseAssertion {

    private static SoftAssert softAssert = new SoftAssert();
    private static boolean used;
    public SoftAssertion() {
        super();
    }


    public SoftAssertion(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        used = true;
        softAssert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        used = true;
        softAssert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {
        used = true;
        softAssert.assertEquals(actual, expected, message);
    }

    // Executes all collected soft assertions.
    // If any assertion failed, assertAll() throws an AssertionError.
    public static void assertAll(ITestResult result) {
        if (!used) return; // If no assertions were made, do nothing
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            LogsManager.error("Assertion failed:", e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        } finally {
            softAssert = new SoftAssert(); // Reset the soft assert instance
        }
    }
}