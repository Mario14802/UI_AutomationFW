package com.automationExercise.pages.components;

import com.automationExercise.drivers.DriverManager;
import com.automationExercise.pages.CartPage;
import com.automationExercise.pages.ProductsPage;
import com.automationExercise.pages.SignupLoginPage;
import com.automationExercise.utils.logsmanager.LogsManager;
import com.automationExercise.utils.readers.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final DriverManager driver;
    // Locators
    private final By homeButton = By.xpath("//a[@href='/' and contains(normalize-space(.),'Home')]");
    private final By productButton = By.xpath("//a[@href='/products' and contains(normalize-space(.),'Products')]");
    private final By cartButton = By.xpath("//a[.=' Cart']");
    private final By logoutButton = By.xpath("//a[.=' Logout']");
    private final By signupLoginButton = By.xpath("//a[.=' Signup / Login']");
    private final By testCasesButton = By.xpath("//a[.=' Test Cases']");
    private final By deleteAccountButton = By.xpath("//a[.=' Delete Account']");
    private final By apiButton = By.xpath("//a[.=' API Testing']");
    private final By videoTutorials = By.xpath("//a[.=' Video Tutorials']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By userLabel = By.tagName("b");
    public NavigationBarComponent(DriverManager driver) {
        this.driver = driver;
    }

    //actions
    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("BASE_URL"));
        return this;
    }

    @Step("Click on Home Button")
    public NavigationBarComponent clickOnHomeButton() {
        driver.element().click(homeButton);
        return this;
    }

    @Step("Click on Products Button")
    public ProductsPage clickOnProductsButton() {
        driver.element().click(productButton);
        return new ProductsPage(driver);
    }

    @Step("Click on Cart Button")
    public CartPage clickOnCartButton() {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }

    @Step("Click on Logout Button")
    public void clickOnLogoutButton() {
        driver.element().click(logoutButton);

    }

    @Step("Click on Signup/Login Button")
    public SignupLoginPage clickOnSignupLoginButton() {
        driver.element().click(signupLoginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Test Cases Button")
    public void clickOnTestCasesButton() {
        driver.element().click(testCasesButton);

    }

    @Step("Click on Delete Account Button")
    public void clickOnDeleteAccountButton() {
        driver.element().click(deleteAccountButton);

    }

    @Step("Click on ContactUs Button Button")
    public void clickOnContactUsButton() {
        driver.element().click(contactUsButton);

    }

    //validations
    @Step("Verify Home Page Label")
    public NavigationBarComponent verifyHomePage() {
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Verify User Label")
    public NavigationBarComponent verifyUserLabel(String expectedName) {
        String actualName = driver.element().getText(userLabel);
        LogsManager.info("Verifying user label: " + actualName);
        driver.verification().Equals(actualName, expectedName, "User label does not match. Expected: " + expectedName + ", Actual: " + actualName);
        return this;

    }


}
