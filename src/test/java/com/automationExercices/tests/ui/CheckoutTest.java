package com.automationExercices.tests.ui;

import com.automationExercices.tests.BaseTest;
import com.automationExercise.apis.UserManagementAPI;
import com.automationExercise.drivers.DriverManager;
import com.automationExercise.drivers.UITest;
import com.automationExercise.pages.CartPage;
import com.automationExercise.pages.ProductsPage;
import com.automationExercise.pages.SignupLoginPage;
import com.automationExercise.pages.components.NavigationBarComponent;
import com.automationExercise.utils.TimeManager;
import com.automationExercise.utils.readers.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Checkout testing")
@Feature("Checkout")
@Story("user Checkout")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mario")
@UITest
public class CheckoutTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimestamp();

    // Existing variables
    private String productName;
    private String productPrice;
    private String itemAddedLabel;
    private String productID; // Note: Kept but unused in current steps
    private String email;
    private String name;

    // Added missing variables for User Details
    private String password;
    private String titleMale;
    private String day;
    private String month;
    private String year;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address1;
    private String address2;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String mobileNumber;

    // Added missing variables for Product Validation
    private String productQuantity;
    private String productTotal;

    @Test
    public void registerNewAccount() {
        new UserManagementAPI().createRegisterUserAccount(
                        name,
                        email,
                        password,
                        titleMale,
                        day,
                        month,
                        year,
                        firstName,
                        lastName,
                        companyName,
                        address1,
                        address2,
                        country,
                        state,
                        city,
                        zipcode,
                        mobileNumber
                )
                .verifyUserCreatedSuccessfully();
    }

    @Test(dependsOnMethods = "registerNewAccount")
    public void loginToAccount() {
        new SignupLoginPage(driver).NavigateToSignupLoginPage()
                .enterLoginEmail(email)
                .enterLoginPassword(password)
                .clickLogin()
                .navigationBar
                .verifyUserLabel(name);
    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount"})
    public void addProductToCart() {
        new ProductsPage(driver)
                .navigateToProductPage()
                .clickOnAddToCart(productName)
                .validateItemAddedLabel(itemAddedLabel)
                .clickOnViewCart()
                .verifyProductDetailsOnCart(
                        productName,
                        productPrice,
                        productQuantity,
                        productTotal
                );
    }

    @Test(dependsOnMethods = {"addProductToCart", "loginToAccount", "registerNewAccount"})
    public void checkout() {
        new CartPage(driver)
                .clickOnProceedToCheckout()
                .verifyDeliveryAddress(
                        titleMale, firstName, lastName, companyName, address1,
                        address2, city, state, zipcode, country, mobileNumber
                )
                .verifyBillingAddress(
                        titleMale, firstName, lastName, companyName, address1,
                        address2, city, state, zipcode, country, mobileNumber
                );
    }

    @Test(dependsOnMethods = {"checkout", "loginToAccount", "registerNewAccount"})
    public void deleteAccountAsPostCondition() {
        new UserManagementAPI()
                .deleteUserAccount(email, password)
                .verifyUserDeletedSuccessfully();
    }

    // Configurations
    @BeforeClass
    protected void setUpClass() {
        testData = new JsonReader("Checkout_Test_data");

        // Initialize dynamic credentials using timestamp
        name = testData.getJsonData("name");
        email = testData.getJsonData("email") + timestamp + "@gmail.com";
        password = testData.getJsonData("password");

        // Initialize Personal Information
        titleMale = testData.getJsonData("titleMale");
        day = testData.getJsonData("day");
        month = testData.getJsonData("month");
        year = testData.getJsonData("year");
        firstName = testData.getJsonData("firstName");
        lastName = testData.getJsonData("lastName");

        // Initialize Address Details
        companyName = testData.getJsonData("companyName");
        address1 = testData.getJsonData("address1");
        address2 = testData.getJsonData("address2");
        country = testData.getJsonData("country");
        state = testData.getJsonData("state");
        city = testData.getJsonData("city");
        zipcode = testData.getJsonData("zipcode");
        mobileNumber = testData.getJsonData("mobileNumber");

        // Initialize Product details
        productName = testData.getJsonData("product.name");
        productPrice = testData.getJsonData("product.price");
        productQuantity = testData.getJsonData("product.quantity");
        productTotal = testData.getJsonData("product.total");
        itemAddedLabel = testData.getJsonData("messages.cartAdded");

        driver = new DriverManager();
        new NavigationBarComponent(driver).navigate();
    }


    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}