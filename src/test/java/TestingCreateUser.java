import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingCreateUser  {
    CreateUser createUser = new CreateUser();
    AuthUser authUser = new AuthUser();


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
    @DisplayName("Check create correct user")
    public void checkCreateCorrectUser(){
        createUser.checkCorrectResponseCreateUser(createUser.responseCreateUsers(createUser.getCorrectUser()));
    }

    @Test
    @DisplayName("Check re-registration the same user")
    public void reRegistrationUser(){
        createUser.checkCorrectResponseCreateUser(createUser.responseCreateUsers(createUser.getCorrectUser()));
        createUser.createAgainUser(createUser.responseCreateUsers(createUser.getCorrectUser()));
    }

    @Test
    @DisplayName("Check create user out email")
    public void registrationUserOutEmail(){
        createUser.checkResponseErrorInBody(createUser.responseCreateUsers(createUser.getNotFieldMail()));
    }

    @Test
    @DisplayName("Check create user out password")
    public void registrationUserOutPassword(){
        createUser.checkResponseErrorInBody(createUser.responseCreateUsers(createUser.getNotFieldPassword()));
    }

    @Test
    @DisplayName("Check create user out name")
    public void registrationUserOutName(){
        createUser.checkResponseErrorInBody(createUser.responseCreateUsers(createUser.getNotFieldName()));
    }
}
