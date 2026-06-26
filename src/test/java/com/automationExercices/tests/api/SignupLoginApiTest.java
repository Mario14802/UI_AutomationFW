package com.automationExercices.tests.api;

import com.automationExercices.tests.BaseTest;
import com.automationExercise.apis.UserManagementAPI;
import com.automationExercise.utils.TimeManager;
import com.automationExercise.utils.readers.JsonReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SignupLoginApiTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimestamp();

    @Test
    public void registerTest() {
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("titleMale"),
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
                .verifyUserCreatedSuccessfully();
    }

    //Configurations
    @BeforeClass
    protected void setUp() {
        testData = new JsonReader("register_test_data");
    }


}
