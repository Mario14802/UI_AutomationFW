package com.automationExercise.pages;

import com.automationExercise.drivers.DriverManager;
import com.automationExercise.utils.logsmanager.LogsManager;
import com.automationExercise.utils.readers.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    //Locators
    private final String productEndPoint = PropertyReader.getProperty("BASE_URL") + "/product_details/";
    private final By productTitle = By.xpath("//div[@class=\"product-information\"]/h2");
    private final By productPrice = By.cssSelector(".product-information > span > span");
    private final By productAdded = By.xpath("//div[@class=\"modal-body\"]/p[contains(text(),\"Your product has been added to cart.\")]");
    private final By quantityInput = By.id("quantity");
    By addToCartBtn = By.xpath("//button[@type=\"button\"]");
    private DriverManager driver;


    public ProductDetailsPage(DriverManager driver) {
        this.driver = driver;
    }

    //actions
    public ProductDetailsPage navigateToProductPage(String ProductId) {
        LogsManager.info("Validating product details url : " + (productEndPoint + ProductId));
        driver.browser().navigateTo(productEndPoint + ProductId);
        return this;
    }

    public ProductDetailsPage addToCartFromDetails() {
        driver.element().click(addToCartBtn);
        return this;
    }

    //verification
    @Step("Validate product details for {ProductName} with price {ProductPrice}")
    public ProductDetailsPage validateProductDetails(String ProductName, String ProductPrice) {
        String actualProductName = driver.element().getText(productTitle);
        LogsManager.info("Validating productTitle : " + (actualProductName));

        String actualProductPrice = driver.element().getText(productPrice);
        LogsManager.info("Validating productPrice : " + (actualProductPrice));

        LogsManager.info("Validating product details for: " + actualProductName, " with price: " + actualProductPrice);
        driver.validation().Equals(actualProductName, ProductName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, ProductPrice, "Product price does not match");
        return this;
    }

    @Step("Validate item added label contains: {expectedText}")
    public ProductDetailsPage validateItemAddedLabel(String expectedText) {
        String actualTxt = driver.element().getText(productAdded);
        LogsManager.info("Validating item added label: " + actualTxt);
        driver.verification().Equals(actualTxt, expectedText, "Item added label does not match expected text");
        return this;
    }

}
