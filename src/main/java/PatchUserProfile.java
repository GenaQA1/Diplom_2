import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PatchUserProfile {

    CreateUser createUser = new CreateUser();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        createUser.deliteUser();
    }


    File changeMailUserProfile = new File("src/main/resources/PatchUserProfile/changeMailUserProfile.json");
    File changeNameUserProfile = new File("src/main/resources/PatchUserProfile/changeNameUserProfile.json");
    File correctUser = new File("src/main/resources/CreateUser/correctUser.json");


    public String responseToken() {
        String response =
                given()
                        .header("Content-type", "application/json")
                        .body(correctUser)
                        .post("/api/auth/login")
                        .then().extract().path("accessToken").toString().replace("Bearer ", "");
        return response;
    }


    public void changeNameUserProfileAuth() {


        given()
                .auth().oauth2(responseToken())
                .header("Content-type", "application/json")
                .body(changeNameUserProfile)
                .patch("/api/auth/user")
                .then().assertThat()
                .body("user.name", equalTo("MusicApple12"))
                .and()
                .statusCode(200);
    }



    public void changeMailUserProfileAuth() {

        given()
                .auth().oauth2(responseToken())
                .header("Content-type", "application/json")
                .body(changeMailUserProfile)
                .patch("/api/auth/user")
                .then().assertThat()
                .body("user.email", equalTo("gena.chebotar@mail.ru"))
                .and()
                .statusCode(200);
    }


    public void changeMailExistsUserProfileAuth() {

        given()
                .auth().oauth2(responseToken())
                .header("Content-type", "application/json")
                .body(changeMailUserProfile)
                .patch("/api/auth/user")
                .then().assertThat()
                .body("message", equalTo("User with such email already exists"))
                .and()
                .statusCode(403);
    }


    public void changeNameUserProfileOutAuth() {

        given()

                .header("Content-type", "application/json")
                .body(changeNameUserProfile)
                .patch("/api/auth/user")
                .then()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }

    public void changeMailUserProfileOutAuth() {


        given()
                .header("Content-type", "application/json")
                .body(changeMailUserProfile)
                .patch("/api/auth/user")
                .then()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }

    public void deliteUsersPatchMail() {


        Response response = given()
                .header("Content-type", "application/json")
                .body(changeMailUserProfile)
                .post("/api/auth/login");

        if (response.path("accessToken") != null) {
            given()
                    .auth().oauth2(response.then().extract().path("accessToken").toString().replace("Bearer ", ""))
                    .delete("/api/auth/user")
                    .then().assertThat().body("message", equalTo("User successfully removed"))
                    .and()
                    .statusCode(202)
                    .and().extract().body();
        }


    }
}


