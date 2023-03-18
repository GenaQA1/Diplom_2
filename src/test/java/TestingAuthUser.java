import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingAuthUser {

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
    @DisplayName("Check authorization correct user")
    public void checkAuthCorrectUser(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        authUser.checkCorrectResponseAnswer(authUser.responseAuthUser(createUser.getCorrectUser()));
    }

    @Test
    @DisplayName("Check authorization user with out email")

    public void checkAuthUserOutMail(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        authUser.checkCorrectErrorResponse(authUser.responseAuthUser(createUser.getNotFieldMail()));
    }

    @Test
    @DisplayName("Check authorization user with out password")
    public void checkAuthUserOutPassword(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        authUser.checkCorrectErrorResponse(authUser.responseAuthUser(createUser.getNotFieldPassword()));
    }
}
