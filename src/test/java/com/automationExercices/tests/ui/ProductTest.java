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
public class ProductTest extends BaseTest {

    private String productName1;
    private String productPrice1;
    private String itemAddedLabel;


    @Test
    public void validAddToCartTest() {
        new ProductsPage(driver)
                .navigateToProductPage()
                .searchProduct(productName1)
                .clickOnAddToCart(productName1)
                .validateItemAddedLabel(itemAddedLabel);
    }


    @BeforeClass
    protected void setUpClass() {
        testData = new JsonReader("products_Test_data");

        productName1 = testData.getJsonData("product1.name");
        productPrice1 = testData.getJsonData("product1.price");
        itemAddedLabel = testData.getJsonData("messages.cartAdded");
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