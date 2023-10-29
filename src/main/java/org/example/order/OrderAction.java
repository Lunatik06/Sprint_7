package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderAction extends org.example.Client {
    static final String ORDERS_PATH = "/orders";

    @Step("Создать заказ")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrderList() {
        return spec()
                .when()
                .get(ORDERS_PATH + "?limit=3")
                .then().log().all();
    }
}