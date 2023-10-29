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

    @Parameterized.Parameters(name = "Имя: {0}, фамилия: {1}, адрес: {2}, метро: {3}, телефон: {4}, дней аренды: {5}, дата доставки: {6}, комментарий: {7}, цвет: {8}")
    public static Object[][] getTestData() {
        String[] blackColor = new String[]{"BLACK"};
        String[] greyColor = new String[]{"BLACK"};
        String[] bothColor = new String[]{"BLACK", "GREY"};
        String[] withoutColor = new String[]{};

        return new Object[][]{
                {"Саске", "Учиха", "Коноха, д. 5, кв 93", "1", "+7 800 355 35 35", 1, "2023-10-12", "Нужно срочно", blackColor},
                {"Хината", "Хьюга", "Коноха, 9/22", "2", "89001230099", 6, "2023-12-10", "Можно не торопиться", greyColor},
                {"Первый", "Хокаге", "Деревня скрытая в листве", "4", "001, потом в тональном режиме 1", 3, "2023-10-10", " ", bothColor},
                {"Naruto", "Uzumaki", "Hidden Lead", "5", "Два, три, четыре пять", 2, "2024-01-01", "", withoutColor},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("3. Создание заказа: можно указать один из цветов — BLACK или GREY; можно указать оба цвета; можно совсем не указывать цвет; тело ответа содержит track.")
    public void createOrderWithDifferentColorSuccess() {

        var order = new Order(firstName, lastName, address, metroStation, phone, rentTime,
                deliveryDate, comment, color);

        ValidatableResponse response = orders.createOrder(order);
        check.CreatedOrderSuccessfully(response);
    }
}