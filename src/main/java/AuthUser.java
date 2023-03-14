import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthUser {

    CreateUser createUser = new CreateUser();
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        createUser.deliteUser();
    }

    File correctUser = new File("src/main/resources/CreateUser/correctUser.json");
    File notFieldMail = new File("src/main/resources/CreateUser/notFieldMail.json");
    File notFieldPassword = new File("src/main/resources/CreateUser/notFieldPassword.json");




    public void authCorrectUser(){

            Response response = given()
                    .header("Content-type", "application/json")
                    .body(correctUser)
                    .post("/api/auth/login");
            response.then().body("success", equalTo(true))
                    .and()
                    .statusCode(200);
        }
    public void authUserOutMail(){

        Response response = given()
                .header("Content-type", "application/json")
                .body(notFieldMail)
                .post("/api/auth/login");
        response.then()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
    }
    public void authUserPassword(){

        Response response = given()
                .header("Content-type", "application/json")
                .body(notFieldPassword)
                .post("/api/auth/login");
        response.then()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
    }
    }

