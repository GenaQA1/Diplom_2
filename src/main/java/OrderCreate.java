import io.restassured.RestAssured;
import org.junit.Before;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderCreate {


    CreateUser createUser = new CreateUser();

    File correctUser = new File("src/main/resources/CreateUser/correctUser.json");
    File orderWithIngredients = new File("src/main/resources/OrderIngredients/OrderWithIngredients.json");
    File orderIncorrectIngredients = new File("src/main/resources/OrderIngredients/OrderIncorrectIngredients.json");

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

    public void OrdersUsersInAuth(){

        given()
                .auth().oauth2(responseToken())
                .header("Content-type", "application/json")
                .body(orderWithIngredients)
                .post("/api/orders")
                .then().assertThat()
                .body("order", notNullValue())
                .and()
                .statusCode(200);
    }

    public void OrdersUsersOutAuth(){

        given()
                .header("Content-type", "application/json")
                .body(orderWithIngredients)
                .post("/api/orders")
                .then().assertThat()
                .body("order.ingredients", nullValue()).and().assertThat().body("order.number",notNullValue())
                .and()
                .statusCode(200);
    }

    public void OrdersUsersWithAuthOutIngredients(){

        given()
                .header("Content-type", "application/json")
                .post("/api/orders")
                .then().assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    public void OrdersUsersWithAuthIncorrectIngredients(){

        given()
                .header("Content-type", "application/json")
                .body(orderIncorrectIngredients)
                .post("/api/orders")
                .then()
                .statusCode(500);
    }





}
