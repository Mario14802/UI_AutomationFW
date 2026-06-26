package com.automationExercices.tests.ui;

import com.automationExercices.tests.BaseTest;
import com.automationExercise.drivers.DriverManager;
import com.automationExercise.drivers.UITest;
import com.automationExercise.pages.ProductsPage;
import com.automationExercise.pages.components.NavigationBarComponent;
import com.automationExercise.utils.readers.JsonReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UITest
public class ProductDetailsTest extends BaseTest {
    private String productName;
    private String productPrice;
    private String itemAddedLabel;
    private String productID;
    private String productDetailName;

    @Test
    public void verifyProductDetailsTest() {

        new ProductsPage(driver)
                .navigateToProductPage()
                .searchProduct(productName)
                .clickOnViewProduct(productName)
                .validateProductDetails(productName, productPrice)
                .addToCartFromDetails()
                .validateItemAddedLabel(itemAddedLabel);


    }

    @BeforeClass
    protected void setUpClass() {
        testData = new JsonReader("productsDetails_Test_data");

        productName = testData.getJsonData("searchedProduct.name");
        productPrice = testData.getJsonData("searchedProduct.price");
        itemAddedLabel = testData.getJsonData("messages.cartAdded");
        productID = testData.getJsonData("searchedProduct.id");

    }

    @BeforeMethod
    public void setUp() {
        driver = new DriverManager();
        new NavigationBarComponent(driver).navigate();
    }


    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }


}