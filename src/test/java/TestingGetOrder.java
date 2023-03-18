import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingGetOrder {

    CreateUser createUser = new CreateUser();
    AuthUser authUser = new AuthUser();
    GetOrder getOrder = new GetOrder();

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
    @DisplayName("Check get order with authorization user")
    public void getOrderWithAuth(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        getOrder.getOrderAuthUser(createUser.getCorrectUser());
    }

    @Test
    @DisplayName("Check get order out authorization user")
    public void getOrderOutAuth(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        getOrder.getOrderOutAuthUser();
    }
}
