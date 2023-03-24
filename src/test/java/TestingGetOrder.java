import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingGetOrder {

    UserClient userClient = new UserClient();
    AuthUser authUser = new AuthUser();
    GetOrder getOrder = new GetOrder();

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.URL_BASE;
    }

    @After
    public void setDown() {
        userClient.deleteUser(authUser.authUser(userClient.getCorrectUser()));
    }

    @Test
    @DisplayName("Check get order with authorization user")
    public void getOrderWithAuth(){
        userClient.createUser(userClient.getCorrectUser());
        getOrder.checkCorrectResponseGetOrder(getOrder.orderAuthUser(userClient.getCorrectUser()));
    }

    @Test
    @DisplayName("Check get order Without authorization user")
    public void getOrderWithoutAuth(){
        userClient.createUser(userClient.getCorrectUser());
        getOrder.checkCorrectResponseOrderWithoutAuthUser(getOrder.orderWithoutAuth());
    }




}
