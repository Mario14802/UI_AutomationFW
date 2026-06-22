package com.automationExercices.tests.ui;


import com.automationExercices.tests.BaseTest;
import com.automationExercise.drivers.DriverManager;
import com.automationExercise.pages.SignUpPage;
import com.automationExercise.pages.SignupLoginPage;
import com.automationExercise.pages.components.NavigationBarComponent;
import com.automationExercise.utils.TimeManager;
import com.automationExercise.utils.readers.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Signup page testing")
@Feature("Signup")
@Story("user Signup")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mario")
public class SignUpTest extends BaseTest {


    @Test
    public void ValidSignupTest() {
        String timestamp = TimeManager.getSimpleTimestamp();
        String email = testData.getJsonData("email") + timestamp + "@gmail.com";
        String name = testData.getJsonData("name");
        new SignupLoginPage(driver).NavigateToSignupLoginPage()
                .enterSignupName(name)
                .enterSignupEmail(email)
                .clickSignup();

        new SignUpPage(driver)
                .fillRegisterationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .clickCreateAccountButton()
                .verifyAccountCreated();
    }

    @Test
    public void UserAlreadyExistsTest() {
        String timestamp = TimeManager.getSimpleTimestamp();
        String email = testData.getJsonData("email") + timestamp + "@gmail.com";
        String name = testData.getJsonData("name");
        new SignupLoginPage(driver).NavigateToSignupLoginPage()
                .enterSignupName(name)
                .enterSignupEmail(email)
                .clickSignup();
        new SignUpPage(driver)
                .fillRegisterationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .clickCreateAccountButton()
                .verifyAccountCreated()
                .clickContinueButton()
                .clickOnLogoutButton();

        new SignupLoginPage(driver).NavigateToSignupLoginPage()
                .enterSignupName(name)
                .enterSignupEmail(email)
                .clickSignup()
                .verifyRegisterErrorMsg(testData.getJsonData("messages.error"));
    }


    //Configurations
    @BeforeClass
    protected void setUp() {
        testData = new JsonReader("register_test_data");
    }

    @BeforeMethod
    public void setup() {
        driver = new DriverManager();
        new NavigationBarComponent(driver).navigate();


    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }

}
