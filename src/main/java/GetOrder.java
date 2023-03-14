import io.restassured.RestAssured;
import org.junit.Before;

import java.io.File;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrder {
    CreateUser createUser = new CreateUser();

    File correctUser = new File("src/main/resources/CreateUser/correctUser.json");

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        createUser.deliteUser();
    }

    public String responseToken() {
        String response =
                given()
                        .header("Content-type", "application/json")
                        .body(correctUser)
                        .post("/api/auth/login")
                        .then().extract().path("accessToken").toString().replace("Bearer ", "");
        return response;
    }

    public void getOrderAuthUser(){
        given()
                .auth().oauth2(responseToken())
                .header("Content-type", "application/json")
                .get("/api/orders")
                .then().assertThat()
                .body("orders",notNullValue())
                .and()
                .statusCode(200);
    }

    public void getOrderOutAuthUser(){
        given()
                .get("/api/orders")
                .then().assertThat()
                .body("message",equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }



}
