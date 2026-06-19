package com.automationExercices.tests.api;


import com.automationExercices.tests.BaseTest;
import com.automationExercise.drivers.GUIDriver;
import com.automationExercise.pages.components.NavigationBarComponent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SignUp extends BaseTest {


@Test
public void signupTest() {
}

@BeforeMethod
public void setup(){
    driver =new GUIDriver();
    new NavigationBarComponent(driver).navigate();

}
@AfterMethod
public void tearDown(){
driver.quitDriver();
}

}
