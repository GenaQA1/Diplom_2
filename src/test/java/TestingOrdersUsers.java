import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingOrdersUsers {


    CreateUser createUser = new CreateUser();
    AuthUser authUser = new AuthUser();
    OrderCreate orderCreate = new OrderCreate();

    @Before
    public void setUp() {
        RestAssured.baseURI = StaticValues.URL_BASE;
        createUser.deleteUser(authUser.responseAuthUser(createUser.getCorrectUser()));
    }

    @After
    public void setDown() {
        createUser.deleteUser(authUser.responseAuthUser(createUser.getCorrectUser()));
    }


    @Test
    @DisplayName("Check create order with authorization user and with Ingredient")
    public void createOrderUsersWithAuthWithIngredient(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        orderCreate.ordersUsersInAuth(createUser.getCorrectUser(),orderCreate.getOrderWithIngredients());
    }

    @Test
    @DisplayName("Check create order out authorization user and with Ingredient")
    public void createOrderUsersOutAuthWithIngredient(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        orderCreate.ordersUsersOutAuth(orderCreate.getOrderWithIngredients());
    }

    @Test
    @DisplayName("Check create order with authorization user and out Ingredient")
    public void createOrderUsersWithAuthOutIngredient(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        orderCreate.ordersUsersWithAuthOutIngredients(createUser.getCorrectUser());
    }
    @Test
    @DisplayName("Check create order with authorization user and incorrect Ingredient")
    public void createOrderUsersWithAuthIncorrectIngredient(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        orderCreate.ordersUsersWithAuthIncorrectIngredients(createUser.getCorrectUser(),orderCreate.getOrderIncorrectIngredients());
    }
}
