package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.core.Is.is;

public class CourierAssertions {

    @Step("Проверить, что создание курьера прошло успешно")
    public void CreatedSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", is(true))
        ;

    }

    @Step("Проверить, что авторизация прошла успешно")
    public int logedInSuccessfully(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
        return id;
    }

    @Step("Проверить, что удаление курьера прошло успешно")
    public void deletedSuccessfully(ValidatableResponse Response) {
        Response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Проверить, что нельзя создать курьера с уже имеющимися данными")
    public void createdCourierTwice(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."))
        ;

    }

    @Step("Проверить, что нельзя создать курьера без обязательных параметров")
    public void createdWithoutObligateParam(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"))
        ;

    }

    @Step("Проверить, что нельзя авторизоватья с некорректными параметрами")
    public void loginWithWrongParam(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"))
        ;

    }
}
