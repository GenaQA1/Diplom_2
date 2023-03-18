import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingOrdersUsers {


    UserClient userClient = new UserClient();
    AuthUser authUser = new AuthUser();
    OrderCreate orderCreate = new OrderCreate();

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.URL_BASE;
    }

    @After
    public void setDown() {
        userClient.deleteUser(authUser.authUser(userClient.getCorrectUser()));
    }


    @Test
    @DisplayName("Check create order with authorization user and with Ingredient")
    public void createOrderUsersWithAuthWithIngredient(){
        userClient.createUser(userClient.getCorrectUser());
        orderCreate.ordersUsersInAuth(orderCreate.orderUsersInAuth(userClient.getCorrectUser(),orderCreate.getOrderWithIngredients()));
    }

    @Test
    @DisplayName("Check create order out authorization user and with Ingredient")
    public void createOrderUsersOutAuthWithIngredient(){
        userClient.createUser(userClient.getCorrectUser());
        orderCreate.ordersUsersOutAuth(orderCreate.orderUsersOutAuth(orderCreate.getOrderWithIngredients()));
    }

    @Test
    @DisplayName("Check create order with authorization user and out Ingredient")
    public void createOrderUsersWithAuthOutIngredient(){
        userClient.createUser(userClient.getCorrectUser());
        orderCreate.ordersUsersWithAuthOutIngredients(orderCreate.orderUsersInAuth(userClient.getCorrectUser(),orderCreate.getNullOrder()));
    }
    @Test
    @DisplayName("Check create order with authorization user and incorrect Ingredient")
    public void createOrderUsersWithAuthIncorrectIngredient(){
        userClient.createUser(userClient.getCorrectUser());
        orderCreate.ordersUsersWithAuthIncorrectIngredients(orderCreate.orderUsersInAuth(userClient.getCorrectUser(),orderCreate.getOrderIncorrectIngredients()));
    }
}
