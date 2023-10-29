package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class CourierAction extends org.example.Client {
    static final String COURIER_PATH = "/courier";

    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Авторизоваться курьером")
    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("Удалить курьера")
    public ValidatableResponse delete(int courierId) {
        return spec()
                .body(Map.of("id", courierId))
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then().log().all();
    }


}
