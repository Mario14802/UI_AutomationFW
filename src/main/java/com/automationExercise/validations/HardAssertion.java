package com.automationExercise.validations;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

//Hard assertion verifcation
public class HardAssertion extends BaseAssertion {

    public HardAssertion() {
        super();
    }

    public HardAssertion(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }


}
