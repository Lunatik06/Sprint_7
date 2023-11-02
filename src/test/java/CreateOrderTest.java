import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderAction;
import org.example.order.OrderAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final OrderAssertions check = new OrderAssertions();
    private final OrderAction orders = new OrderAction();
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;


    public CreateOrderTest(String firstName, String lastName, String address, String metroStation,
                           String phone, int rentTime, String deliveryDate, String comment,
                           String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    static Faker faker = new Faker();
    //return new Courier(faker.name().username(), faker.number().digits(6), faker.name().firstName());
    @Parameterized.Parameters(name = "Имя: {0}, фамилия: {1}, адрес: {2}, метро: {3}, телефон: {4}, дней аренды: {5}, дата доставки: {6}, комментарий: {7}, цвет: {8}")
    public static Object[][] getTestData() {
        String[] blackColor = new String[]{"BLACK"};
        String[] greyColor = new String[]{"GREY"};
        String[] bothColor = new String[]{"BLACK", "GREY"};
        String[] withoutColor = new String[]{};

        // UPD: добавил рандомную генерацию данных для части полей
        return new Object[][]{
                {faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(), faker.number().digits(1), faker.phoneNumber().phoneNumber(), faker.number().numberBetween(1,10), "2023-10-12", "Нужно срочно", blackColor},
                {faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(), faker.number().digits(1), faker.phoneNumber().phoneNumber(), faker.number().numberBetween(1,10), "2023-10-12", "cool", greyColor},
                {"Первый", "Хокаге", "Деревня скрытая в листве", "4", "001, потом в тональном режиме 1", 3, "2023-10-10", " ", bothColor},
                {faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(), faker.number().digits(1), faker.phoneNumber().phoneNumber(), faker.number().numberBetween(1,10), "2023-12-12", "", withoutColor},
        };
    }

    @Test
    @DisplayName("3. Создание заказа: можно указать один из цветов — BLACK или GREY; можно указать оба цвета; можно совсем не указывать цвет; тело ответа содержит track.")
    public void createOrderWithDifferentColorSuccess() {

        var order = new Order(firstName, lastName, address, metroStation, phone, rentTime,
                deliveryDate, comment, color);

        ValidatableResponse response = orders.createOrder(order);
        check.createdOrderSuccessfully(response);
    }
}