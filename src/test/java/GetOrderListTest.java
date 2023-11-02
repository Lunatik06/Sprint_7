import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.OrderAction;
import org.example.order.OrderAssertions;
import org.junit.Test;

public class GetOrderListTest {

    private final OrderAssertions check = new OrderAssertions();
    private final OrderAction orders = new OrderAction();

    @Test
    @DisplayName("4. Список заказов: Проверь, что в тело ответа возвращается список заказов.")
    public void getOrderListTest() {

        ValidatableResponse response = orders.getOrderList();
        check.getOrderListSuccessfully(response);

    }

}
