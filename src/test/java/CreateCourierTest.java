import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.courier.CourierAction;
import org.example.courier.CourierAssertions;
import org.example.courier.CourierGenerator;
import org.example.courier.Credentials;
import org.junit.After;
import org.junit.Test;

public class CreateCourierTest {

    private final CourierAction client = new CourierAction();
    private final CourierAssertions check = new CourierAssertions();
    protected int courierId;

    @After
    public void deleteCourier() {
        ValidatableResponse delete = client.delete(courierId);
        check.deletedSuccessfully(delete);
    }

    @Test
    @DisplayName("1. Создание курьера: курьера можно создать; запрос возвращает правильный код ответа; успешный запрос возвращает ok: true;")
    public void createCourierTest() {
        var courier = CourierGenerator.random();

        ValidatableResponse response = client.create(courier);
        check.CreatedSuccessfully(response);

        var creds = Credentials.from(courier);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.logedInSuccessfully(loginResponse);

        assert courierId != 0;

    }

    @Test
    @DisplayName("1. Создание курьера: нельзя создать двух одинаковых курьеров.")
    public void forbiddenCreateTwiceCourierTest() {
        var courierFirst = CourierGenerator.generic();

        ValidatableResponse responseFirst = client.create(courierFirst);
        check.CreatedSuccessfully(responseFirst);

        var courierSecond = CourierGenerator.generic();

        ValidatableResponse responseSecond = client.create(courierSecond);
        check.createdCourierTwice(responseSecond);

        var creds = Credentials.from(courierFirst);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.logedInSuccessfully(loginResponse);

        assert courierId != 0;

    }

    @Test
    @DisplayName("1. Создание курьера: чтобы создать курьера, нужно передать в ручку все обязательные поля; если одного из полей нет, запрос возвращает ошибку")
    public void createCourierWithoutObligateParamTest() {
        var courierWithoutLogin = CourierGenerator.withoutLogin();

        ValidatableResponse responseWithoutLogin = client.create(courierWithoutLogin);
        check.createdWithoutObligateParam(responseWithoutLogin);

        var courierWithoutPassword = CourierGenerator.withoutPassword();

        ValidatableResponse responseWithoutPassword = client.create(courierWithoutPassword);
        check.createdWithoutObligateParam(responseWithoutPassword);

        var courierWithoutFirstname = CourierGenerator.withoutFirstname();

        ValidatableResponse responseWithoutFirstname = client.create(courierWithoutFirstname); // данный кейс падает, потому что функционал позвляет создавать юзера без Firstname
        check.createdWithoutObligateParam(responseWithoutFirstname);

        assert courierId != 0;
    }


}