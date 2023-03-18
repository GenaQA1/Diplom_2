import io.restassured.RestAssured;
import org.junit.Before;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderCreate {

    Order orderWithIngredients = new Order(List.of("61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa74"));
    Order orderIncorrectIngredients = new Order(List.of("61c0c5a71d1f82001bdaaa6"));

    public Order getOrderWithIngredients() {
        return orderWithIngredients;
    }
    public Order getOrderIncorrectIngredients() {
        return orderIncorrectIngredients;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    public String responseToken(UserProfile userProfile) {
        String response =
                given()
                        .header("Content-type", "application/json")
                        .body(userProfile)
                        .post(StaticValues.API_AUTH)
                        .then().extract().path("accessToken").toString().replace("Bearer ", "");
        return response;
    }

    public void ordersUsersInAuth(UserProfile userProfile, Order order){
        given()
                .auth().oauth2(responseToken(userProfile))
                .header("Content-type", "application/json")
                .body(order)
                .post(StaticValues.API_ORDER)
                .then().assertThat()
                .body("order", notNullValue())
                .and()
                .statusCode(200);
    }

    public void ordersUsersOutAuth(Order order){
        given()
                .header("Content-type", "application/json")
                .body(order)
                .post(StaticValues.API_ORDER)
                .then().assertThat()
                .body("order.ingredients", nullValue()).and().assertThat().body("order.number",notNullValue())
                .and()
                .statusCode(200);
    }

    public void ordersUsersWithAuthOutIngredients(UserProfile userProfile){
        given()
                .auth().oauth2(responseToken(userProfile))
                .header("Content-type", "application/json")
                .post(StaticValues.API_ORDER)
                .then().assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    public void ordersUsersWithAuthIncorrectIngredients(UserProfile userProfile, Order order){
        given()
                .auth().oauth2(responseToken(userProfile))
                .header("Content-type", "application/json")
                .body(order)
                .post(StaticValues.API_ORDER)
                .then()
                .statusCode(500);
    }





}
