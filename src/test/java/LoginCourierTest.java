import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.courier.CourierAction;
import org.example.courier.CourierAssertions;
import org.example.courier.CourierGenerator;
import org.example.courier.Credentials;
import org.junit.After;
import org.junit.Test;

public class LoginCourierTest {

    private final CourierAction client = new CourierAction();
    private final CourierAssertions check = new CourierAssertions();
    protected int courierId;

    @After
    public void deleteCourier() {
        ValidatableResponse delete = client.delete(courierId);
        check.deletedSuccessfully(delete);
    }

    @Test
    @DisplayName("2. Логин курьера: курьер может авторизоваться; для авторизации нужно передать все обязательные поля; успешный запрос возвращает id.")
    public void loginCourierTest() {
        var courier = CourierGenerator.random();

        ValidatableResponse response = client.create(courier);
        check.CreatedSuccessfully(response);

        var creds = Credentials.from(courier);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.logedInSuccessfully(loginResponse);

        assert courierId != 0;

    }

    @Test
    @DisplayName("2. Логин курьера: система вернёт ошибку, если неправильно указать логин; если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;")
    public void loginCourierWithWrongLoginTest() {
        var courier = CourierGenerator.generic();

        ValidatableResponse response = client.create(courier);
        check.CreatedSuccessfully(response);

        var creds = Credentials.from(courier);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.logedInSuccessfully(loginResponse);

        var courierWithWrongLogin = CourierGenerator.withWrongLogin();
        var credsWithWrongLogin = Credentials.from(courierWithWrongLogin);

        ValidatableResponse loginResponseithWrongParam = client.login(credsWithWrongLogin);
        check.loginWithWrongParam(loginResponseithWrongParam);

        assert courierId != 0;

    }

    @Test
    @DisplayName("2. Логин курьера: система вернёт ошибку, если неправильно указать пароль;")
    public void loginCourierWithWrongPasswordTest() {
        var courier = CourierGenerator.generic();

        ValidatableResponse response = client.create(courier);
        check.CreatedSuccessfully(response);

        var creds = Credentials.from(courier);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.logedInSuccessfully(loginResponse);

        var courierWithWrongPassword = CourierGenerator.withWrongPassword();
        var credsWithWrongPassword = Credentials.from(courierWithWrongPassword);

        ValidatableResponse loginResponseithWrongParam = client.login(credsWithWrongPassword);
        check.loginWithWrongParam(loginResponseithWrongParam);

        assert courierId != 0;
    }


}