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
    @DisplayName("Check create user out email")
    public void registrationUserOutEmail(){
        userClient.checkResponseErrorInBody(userClient.createUser(userClient.getNotFieldMail()));
    }

    @Test
    @DisplayName("Check create user out password")
    public void registrationUserOutPassword(){
        userClient.checkResponseErrorInBody(userClient.createUser(userClient.getNotFieldPassword()));
    }

    @Test
    @DisplayName("Check create user out name")
    public void registrationUserOutName(){
        userClient.checkResponseErrorInBody(userClient.createUser(userClient.getNotFieldName()));
    }
}
