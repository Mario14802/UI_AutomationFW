package com.automationExercise.pages;

import com.automationExercise.action.ElementAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage {
    //locators
    private final By Username = By.id("user-name");
    private final By Password = By.id("password");
    private final By LoginBtn = By.id("login-button");
    //variable
    private WebDriver driver;
    private ElementAction elementaction;

    //constructor

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.elementaction = new ElementAction(driver);
    }


    //actions
    public LoginPage login(String username, String password) {
        elementaction.type(Username, username);
        elementaction.type(Password, password);
        elementaction.click(LoginBtn);
        return this;
    }

    public HomePage isLoggedIn(String currenturl) {
        Assert.assertEquals(driver.getCurrentUrl(), currenturl);
        return new HomePage(driver);
    }


}
