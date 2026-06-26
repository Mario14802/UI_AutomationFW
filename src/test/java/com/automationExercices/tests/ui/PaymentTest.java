package com.automationExercices.tests.ui;

import com.automationExercices.tests.BaseTest;
import com.automationExercise.apis.UserManagementAPI;
import com.automationExercise.drivers.DriverManager;
import com.automationExercise.drivers.UITest;
import com.automationExercise.pages.CartPage;
import com.automationExercise.pages.CheckoutPage;
import com.automationExercise.pages.ProductsPage;
import com.automationExercise.pages.SignupLoginPage;
import com.automationExercise.pages.components.NavigationBarComponent;
import com.automationExercise.utils.TimeManager;
import com.automationExercise.utils.readers.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI Payment")
@Story("Payment")
@UITest
public class PaymentTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();

    // User Information Fields
    private String name;
    private String email;
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

    // Product Fields
    private String productName;
    private String productPrice;
    private String productQuantity;
    private String productTotal;
    private String cartAddedMessage;

    // Payment & Success Fields
    private String cardName;
    private String cardNumber;
    private String cardCvc;
    private String cardExMonth;
    private String cardExYear;
    private String paymentSuccessMessage;

    @Test(
            testName = "Register Account via API",
            description = "Register a new user account via API to prepare for checkout test"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Mario")

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

    @Test(
            dependsOnMethods = "registerNewAccount",
            testName = "Login to User Account",
            description = "Log into the application using the freshly registered user credentials"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Mario")
    public void loginToAccount() {
        new SignupLoginPage(driver)
                .NavigateToSignupLoginPage()
                .enterLoginEmail(email)
                .enterLoginPassword(password)
                .clickLogin()
                .navigationBar
                .verifyUserLabel(name);
    }

    @Test(
            dependsOnMethods = {"loginToAccount", "registerNewAccount"},
            testName = "Add Product to Shopping Cart",
            description = "Navigate to the products section and successfully add a designated item to the shopping cart"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Mario")
    public void addProductToCart() {
        new ProductsPage(driver)
                .navigateToProductPage()
                .clickOnAddToCart(productName)
                .validateItemAddedLabel(cartAddedMessage)
                .clickOnViewCart()
                .verifyProductDetailsOnCart(
                        productName,
                        productPrice,
                        productQuantity,
                        productTotal
                );
    }

    @Test(
            dependsOnMethods = {"addProductToCart", "loginToAccount", "registerNewAccount"},
            testName = "Verify Checkout Addresses",
            description = "Proceed to checkout page and assert the validity of delivery and billing address structures"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Mario")
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

    @Test(
            dependsOnMethods = {"checkout", "addProductToCart", "loginToAccount", "registerNewAccount"},
            testName = "Process Order Payment",
            description = "Submit valid payment card credentials, complete order processing, and verify transaction success"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Mario")
    public void paymentTest() {
        new CheckoutPage(driver)
                .clickOnPlaceOrder()
                .fillCardInfo(
                        cardName,
                        cardNumber,
                        cardCvc,
                        cardExMonth,
                        cardExYear
                )
                .verifyPaymentSuccessMessage(paymentSuccessMessage);
    }

    @Test(
            dependsOnMethods = {"paymentTest", "checkout", "loginToAccount", "registerNewAccount"},
            testName = "Teardown User Data via API",
            description = "Post-condition data teardown: Delete the created test user account through backend API execution"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Mario")
    public void deleteAccountAsPostCondition() {
        new UserManagementAPI()
                .deleteUserAccount(email, password)
                .verifyUserDeletedSuccessfully();
    }

    // Configurations
    @BeforeClass
    protected void setUpClass() {
        testData = new JsonReader("Checkout_Test_data");

        // Dynamic user assignment from JSON variables
        name = testData.getJsonData("name");
        email = testData.getJsonData("email") + timestamp + "@gmail.com";
        password = testData.getJsonData("password");

        // Personal Information
        titleMale = testData.getJsonData("titleMale");
        day = testData.getJsonData("day");
        month = testData.getJsonData("month");
        year = testData.getJsonData("year");
        firstName = testData.getJsonData("firstName");
        lastName = testData.getJsonData("lastName");

        // Physical Addresses
        companyName = testData.getJsonData("companyName");
        address1 = testData.getJsonData("address1");
        address2 = testData.getJsonData("address2");
        country = testData.getJsonData("country");
        state = testData.getJsonData("state");
        city = testData.getJsonData("city");
        zipcode = testData.getJsonData("zipcode");
        mobileNumber = testData.getJsonData("mobileNumber");

        // Store Catalog structures
        productName = testData.getJsonData("product.name");
        productPrice = testData.getJsonData("product.price");
        productQuantity = testData.getJsonData("product.quantity");
        productTotal = testData.getJsonData("product.total");
        cartAddedMessage = testData.getJsonData("messages.cartAdded");

        // Bank / Payment Information
        cardName = testData.getJsonData("card.cardName");
        cardNumber = testData.getJsonData("card.cardNumber");
        cardCvc = testData.getJsonData("card.cvc");
        cardExMonth = testData.getJsonData("card.exMonth");
        cardExYear = testData.getJsonData("card.exYear");
        paymentSuccessMessage = testData.getJsonData("messages.paymentSuccess");

        driver = new DriverManager();
        new NavigationBarComponent(driver).navigate();
    }


    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}