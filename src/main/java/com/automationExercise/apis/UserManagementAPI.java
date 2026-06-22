package com.automationExercise.apis;

import com.automationExercise.utils.logsmanager.LogsManager;
import com.automationExercise.validations.HardAssertion;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementAPI {
    //endpoints
    private static final String createAccount_endpoint = "/createAccount";
    private static final String deleteAccount_endpoint = "/deleteAccount";

    RequestSpecification requestSpecification;
    Response response;
    HardAssertion hardassertion;

    public UserManagementAPI() {

        requestSpecification = RestAssured.given();
        hardassertion = new HardAssertion();
    }

    //api methods
    @Step("Create a new user account with full details")
    public UserManagementAPI createRegisterUserAccount(String name, String email, String pass, String title, String birth_date, String birth_month, String birth_year, String firstName, String lastName
            , String company, String address1, String address2, String country, String zipcode, String state, String city, String mobile_number) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", pass);
        formParams.put("title", title);
        formParams.put("birth_date", birth_date);
        formParams.put("birth_month", birth_month);
        formParams.put("birth_year", birth_year);
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("zipcode", zipcode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobile_number);
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .post(createAccount_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Create a new user account with minimal details")
    public UserManagementAPI createLoginUserAccount(String name, String email, String pass, String firstName, String lastName) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", pass);
        formParams.put("title", "Mr");
        formParams.put("birth_date", "1");
        formParams.put("birth_month", "January");
        formParams.put("birth_year", "1990");
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", "company");
        formParams.put("address1", "address1");
        formParams.put("address2", "address2");
        formParams.put("country", "India");
        formParams.put("zipcode", "123456");
        formParams.put("state", "state");
        formParams.put("city", "city");
        formParams.put("mobile_number", "1234567890");
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .post(createAccount_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Delete user account by email")
    public UserManagementAPI deleteUserAccount(String email, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .delete(deleteAccount_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }


    //validations
    @Step("Verify that user is created successfully")
    public UserManagementAPI verifyUserCreatedSuccessfully() {
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Content Type: " + response.getContentType());

        hardassertion.Equals(response.jsonPath().get("message"), "User created!",
                "User is not created successfully");
        return this;
    }

    @Step("Verify that user is deleted successfully")
    public UserManagementAPI verifyUserDeletedSuccessfully() {
        System.out.println(response.asPrettyString());
        hardassertion.Equals(response.jsonPath().get("message"), "Account deleted!",
                "User is not deleted successfully");
        return this;
    }


}
