import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingAuthUser {

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
    @DisplayName("Check authorization correct user")
    public void checkAuthCorrectUser(){
        userClient.createUser(userClient.getCorrectUser());
        authUser.checkResponseCorrectAuth(authUser.authUser(userClient.getCorrectUser()));
    }

    @Test
    @DisplayName("Check authorization user without email")
    public void checkAuthUserOutMail(){
        userClient.createUser(userClient.getCorrectUser());
        authUser.checkResponseIncorrectAuth(authUser.authUser(userClient.getUserWithoutMail()));
    }

    @Test
    @DisplayName("Check authorization user without password")
    public void checkAuthUserOutPassword(){
        userClient.createUser(userClient.getCorrectUser());
        authUser.checkResponseIncorrectAuth(authUser.authUser(userClient.getUserWithoutPassword()));
    }
}
