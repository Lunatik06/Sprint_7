package org.example.order;

import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderAssertions {

    public void createdOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("track")
        ;

    }

    public void getOrderListSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("orders", notNullValue())
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
        ;

    }


}