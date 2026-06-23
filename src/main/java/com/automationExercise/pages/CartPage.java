package com.automationExercise.pages;

import com.automationExercise.drivers.DriverManager;
import com.automationExercise.utils.readers.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {

    private final By CheckoutBtn = By.xpath("//a[@class=\"btn btn-default check_out\"]");
    String cartURL = PropertyReader.getProperty("BASE_URL") + "/view_cart";
    private DriverManager driver;

    public CartPage(DriverManager driver) {
        this.driver = driver;
    }


    //Dynamic locators
    public By ProductName(String ProductName) {
        return By.xpath("//a[text()= '" + ProductName + "' ]");
    }

    public By ProductPrice(String productName) {
        return By.xpath("(//h4  /a[.='" + productName + "'] //following::td[@class='cart_price'] /p)[1]");
    }

    private By productQuantity(String productName) {
        return By.xpath("(//h4  /a[.='" + productName + "'] //following::td[@class='cart_quantity'] /button)[1]");
    }

    private By productTotal(String productName) {
        return By.xpath("(//h4  /a[.='" + productName + "'] //following::td[@class='cart_total'] /p)[1]");
    }

    private By removeProductDL(String productName) {
        return By.xpath("(//h4  /a[.='" + productName + "'] //following::td[@class='cart_delete'] /a)[1]");
    }

    //actions
    @Step("Navigate To Cart Page")
    public CartPage navigate() {
        driver.browser().navigateTo(cartURL);
        return this;
    }

    @Step("Click On Proceed To Checkout Button")
    public CheckoutPage clickOnProceedToCheckout() {
        driver.element().click(CheckoutBtn);
        return new CheckoutPage(driver);
    }

    @Step("Remove Product From Cart")
    public CartPage removeProduct(String pName) {
        driver.element().click(removeProductDL(pName));
        return this;
    }
    //verification

    @Step("Verify Product Details On Cart")
    public CartPage verifyProductDetailsOnCart(String productName, String productPrice, String productQuantity, String productTotal) {
        String actualProductName = driver.element().getText(ProductName(productName));
        String actualProductPrice = driver.element().getText(ProductPrice(productName));
        String actualProductQuantity = driver.element().getText(productQuantity(productName));
        String actualProductTotal = driver.element().getText(productTotal(productName));
        driver.validation().Equals(actualProductName, productName, " Product Name is not matched");
        driver.validation().Equals(actualProductPrice, productPrice, " Product Price is not matched");
        driver.validation().Equals(actualProductQuantity, productQuantity, " Product Quantity is not matched");
        driver.validation().Equals(actualProductTotal, productTotal, " Product Total is not matched");
        return this;
    }
}
