package com.automationExercices.tests.ui;

import com.automationExercices.tests.BaseTest;
import com.automationExercise.apis.UserManagementAPI;
import com.automationExercise.drivers.DriverManager;
import com.automationExercise.drivers.UITest;
import com.automationExercise.pages.SignupLoginPage;
import com.automationExercise.pages.components.NavigationBarComponent;
import com.automationExercise.utils.TimeManager;
import com.automationExercise.utils.readers.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Login page testing")
@Feature("Login")
@Story("user login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mario")
@UITest
public class LoginTest extends BaseTest {

    @Test
    @Description("Verify user can login with valid email and password")
    public void validLoginTC() {
        String timestamp = TimeManager.getSimpleTimestamp();

        String email = testData.getJsonData("email") + timestamp + "@gmail.com";
        String name = testData.getJsonData("name");
        String Password = testData.getJsonData("password");
        //Create user
        new UserManagementAPI().createLoginUserAccount(
                        name,
                        email,
                        Password,
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();
//Login with user
        new SignupLoginPage(driver).NavigateToSignupLoginPage()
                .enterLoginEmail(email)
                .enterLoginPassword(Password)
                .clickLogin()
                .navigationBar
                .verifyUserLabel(name);
//delete user
        new UserManagementAPI().deleteUserAccount(email, Password)
                .verifyUserDeletedSuccessfully();

    }

    @Test
    @Description("Verify user cannot login with invalid email")
    public void InvalidEmailLoginTC() {

        String timestamp = TimeManager.getSimpleTimestamp();

        String email = testData.getJsonData("email") + timestamp + "@gmail.com";
        String name = testData.getJsonData("name");
        String Password = testData.getJsonData("password");
        String InvalidEmail = (testData.getJsonData("InvalidEmail") + "@gmail.com");
        String errormessage = testData.getJsonData("messages.error");
        //Create user
        new UserManagementAPI().createLoginUserAccount(
                        name,
                        email,
                        Password,
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        //Login with invalid email
        new SignupLoginPage(driver).NavigateToSignupLoginPage()
                .enterLoginEmail(InvalidEmail)
                .enterLoginPassword(Password)
                .clickLogin()
                .verifyLoginErrorMsg(errormessage);
        //delete user
        new UserManagementAPI().deleteUserAccount(email, Password)
                .verifyUserDeletedSuccessfully();

    }

    @Description("Verify user cannot login with invalid Password")
    @Test
    public void InvalidPasswordLoginTC() {
        String timestamp = TimeManager.getSimpleTimestamp();

        String email = testData.getJsonData("email") + timestamp + "@gmail.com";
        String name = testData.getJsonData("name");
        String Password = testData.getJsonData("Password");
        String InvalidPassword = testData.getJsonData("invalidPassword");
        String errormessage = testData.getJsonData("messages.error");
        //Create user
        new UserManagementAPI().createLoginUserAccount(
                        name,
                        email,
                        Password,
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();
        //Login with user
        new SignupLoginPage(driver).NavigateToSignupLoginPage()
                .enterLoginEmail(email)
                .enterLoginPassword(InvalidPassword)
                .clickLogin()
                .verifyLoginErrorMsg(errormessage);

        //delete user
        new UserManagementAPI().deleteUserAccount(email, Password)
                .verifyUserDeletedSuccessfully();

    }

    @BeforeClass
    public void beforeClass() {
        testData = new JsonReader("Login_Test_data");
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = new DriverManager();
        new NavigationBarComponent(driver).navigate();

    }

    @AfterMethod
    public void TearDown() {
        driver.quitDriver();
    }


}
