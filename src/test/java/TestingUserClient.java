import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingUserClient {
    UserClient userClient = new UserClient();
    AuthUser authUser = new AuthUser();


    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.URL_BASE;
    }

    @After
    public void setDown() {
        userClient.deleteUser(authUser.authUser(userClient.getCorrectUser()));
    }

    @Test
    @DisplayName("Check create correct user")
    public void checkCreateCorrectUser(){
        userClient.checkCorrectResponseCreateUser(userClient.createUser(userClient.getCorrectUser()));
    }

    @Test
    @DisplayName("Check re-registration the same user")
    public void reRegistrationUser(){
        userClient.checkCorrectResponseCreateUser(userClient.createUser(userClient.getCorrectUser()));
        userClient.checkCreateAgainUser(userClient.createUser(userClient.getCorrectUser()));
    }

    @Test
    @DisplayName("Check create user Without email")
    public void registrationUserWithoutEmail(){
        userClient.checkResponseErrorInBody(userClient.createUser(userClient.getUserWithoutMail()));
    }

    @Test
    @DisplayName("Check create user Without password")
    public void registrationUserWithoutPassword(){
        userClient.checkResponseErrorInBody(userClient.createUser(userClient.getUserWithoutPassword()));
    }

    @Test
    @DisplayName("Check create user out name")
    public void registrationUserWithoutName(){
        userClient.checkResponseErrorInBody(userClient.createUser(userClient.getUserWithoutName()));
    }
}
