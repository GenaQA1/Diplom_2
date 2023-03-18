

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderCreate {

    Order orderWithIngredients = new Order(List.of("61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa74"));
    Order orderIncorrectIngredients = new Order(List.of("61c0c5a71d1f82001bdaaa6"));

    public Order getNullOrder() {
        return nullOrder;
    }

    Order nullOrder = new Order(List.of());


    public Order getOrderWithIngredients() {
        return orderWithIngredients;
    }
    public Order getOrderIncorrectIngredients() {
        return orderIncorrectIngredients;
    }


    public String responseToken(UserProfile userProfile) {
        String response =
                given()
                        .header("Content-type", "application/json")
                        .body(userProfile)
                        .post(URLs.API_AUTH)
                        .then().extract().path("accessToken").toString().replace("Bearer ", "");
        return response;
    }

    public Response orderUsersInAuth(UserProfile userProfile, Order order){
        Response response = given()
                .auth().oauth2(responseToken(userProfile))
                .header("Content-type", "application/json")
                .body(order)
                .post(URLs.API_ORDER);
        return response;
    }

    public void ordersUsersInAuth(Response response){
                response.then().assertThat()
                .body("order", notNullValue())
                .and()
                .statusCode(200);
    }

    public Response orderUsersOutAuth(Order order){
        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .post(URLs.API_ORDER);
        return response;
    }

    public void ordersUsersOutAuth(Response response){
                response.then().assertThat()
                .body("order.ingredients", nullValue()).and().assertThat().body("order.number",notNullValue())
                .and()
                .statusCode(200);
    }

    public void ordersUsersWithAuthOutIngredients(Response response){
               response.then().assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    public void ordersUsersWithAuthIncorrectIngredients(Response response){
                response.then()
                .statusCode(500);
    }





}
