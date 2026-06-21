package com.automationExercise.apis;

import com.automationExercise.utils.readers.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private static String baseURI = PropertyReader.getProperty("BASE_URL_API");

    private Builder() {
        // Private constructor to prevent instantiation
    }

    //build request specification
    public static RequestSpecification getUserManagementRequestSpecification(Map<String, ?> formParams) {
        return new RequestSpecBuilder().setBaseUri(baseURI)
                .setContentType(ContentType.URLENC)
                .addFormParams(formParams)
                .build();
    }
}
