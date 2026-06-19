package com.automationExercise.pages;

import com.automationExercise.action.ElementAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage {

    //variables
    private WebDriver driver;
    private ElementAction elementaction;
    private By addToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
    private By cartIcon = By.className("shopping_cart_link");

    //constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.elementaction = new ElementAction(driver);
    }


    //actions
    public HomePage addtocart() {
        elementaction.click(addToCartBtn);
        return this;
    }

    public HomePage validateCartIcon() {
        String cartNum = elementaction.getText(cartIcon);
        Assert.assertEquals(cartNum, "1");
        System.out.println("Number of added items= " + cartNum);
        return this;
    }


}
