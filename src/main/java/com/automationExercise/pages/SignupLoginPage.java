package com.automationExercise.pages;

import com.automationExercise.drivers.DriverManager;
import com.automationExercise.utils.readers.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    private final DriverManager driver;
    private final String SignupLoginPageUrl = "/login";
    //locoators
    //login
    private final By EmailAddressLogin = By.xpath("//input[@type=\"email\"  and @data-qa=\"login-email\"]");
    private final By PasswordLogin = By.xpath("//input[@type=\"password\" and @data-qa=\"login-password\"]");
    //sign up inputs
    private final By NameSignUp = By.xpath("//input[@type=\"text\" and @data-qa=\"signup-name\"]");
    private final By EmailAddressSignUp = By.xpath("//input[@type=\"email\" and @data-qa=\"signup-email\"]");
    // buttons
    private final By LoginBtn = By.xpath("//button[@type=\"submit\" and @data-qa=\"login-button\"]");
    private final By SignupBtn = By.xpath("//button[@type=\"submit\" and @data-qa=\"signup-button\"]");
    //page
    private final By signupLabel = By.xpath("//div[@class='signup-form' and contains(.,'New User Signup!')]");
    //Errors
    private final By loginError = By.cssSelector(".login-form  p");
    private final By registerError = By.cssSelector(".signup-form p");

    public SignupLoginPage(DriverManager driver) {
        this.driver = driver;
    }

    //actions
    @Step("Navigate to Signup/Login page")
    public SignupLoginPage NavigateToSignupLoginPage() {
        driver.browser().navigateTo(PropertyReader.getProperty("BASE_URL") + SignupLoginPageUrl);
        System.out.println("Current URL = " + driver.browser().getCurrentUrl());
        return this;
    }


    @Step("Enter login email: {email}")
    public SignupLoginPage enterLoginEmail(String email) {
        driver.element().type(EmailAddressLogin, email);
        return this;
    }

    @Step("Enter login password: {password}")
    public SignupLoginPage enterLoginPassword(String password) {
        driver.element().type(PasswordLogin, password);
        return this;
    }

    @Step("Click login button")
    public SignupLoginPage clickLogin() {
        driver.element().click(LoginBtn);
        return this;
    }

    @Step("Perform login with email: {email}")
    public SignupLoginPage login(String email, String password) {
        enterLoginEmail(email);
        enterLoginPassword(password);
        clickLogin();
        return this;
    }

    @Step("Enter signup name: {name}")
    public SignupLoginPage enterSignupName(String name) {
        driver.element().type(NameSignUp, name);
        return this;
    }

    @Step("Enter signup email: {email}")
    public SignupLoginPage enterSignupEmail(String email) {
        driver.element().type(EmailAddressSignUp, email);
        return this;
    }

    @Step("Click signup button")
    public SignupLoginPage clickSignup() {
        driver.element().click(SignupBtn);
        return this;
    }

    @Step("Perform signup with name: {name} and email: {email}")
    public SignupLoginPage signup(String name, String email) {
        enterSignupName(name);
        enterSignupEmail(email);
        clickSignup();
        return this;
    }


    //validations
    @Step("Verify new user signup visible")
    public SignupLoginPage verifyNewUserSignupVisible() {
        driver.verification().isElementVisible(signupLabel);
        return this;
    }

    @Step("Verify login error message {errorExpected}")
    public SignupLoginPage verifyLoginErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(loginError);
        driver.verification().Equals(errorActual, errorExpected, "Login error message is not as expected");
        return this;
    }

    @Step("Verify register error message {errorExpected}")
    public SignupLoginPage verifyRegisterErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(registerError);
        driver.verification().Equals(errorActual, errorExpected, "Register error message is not as expected");
        return this;
    }

    // validate that iam in signup login page by using title

    /*
     * login scenario 2 inputs and then button click
     * and then verify that you are in the home page
     * */


    /*
     * signup scenario 2 inputs and then button click
     * and then verify that you are in the signup page
     * */


}
