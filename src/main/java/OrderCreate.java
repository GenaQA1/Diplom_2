

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderCreate {

    AuthUser authUser = new AuthUser();

    Order orderWithIngredients = new Order(List.of("61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa74"));
    Order orderIncorrectIngredients = new Order(List.of("61c0c5a71d1f82001bdaaa6"));

    Order nullOrder = new Order(List.of());

    public Order getNullOrder() {
        return nullOrder;
    }

    public Order getOrderWithIngredients() {
        return orderWithIngredients;
    }
    public Order getOrderIncorrectIngredients() {
        return orderIncorrectIngredients;
    }



    public Response createOrderUsersInAuth(UserProfile userProfile, Order order){
        Response response = given()
                .auth().oauth2(authUser.responseToken(userProfile))
                .header("Content-type", "application/json")
                .body(order)
                .post(URLs.API_ORDER);
        return response;
    }

    public void checkResponseOrdersUsersInAuth(Response response){
                response.then().assertThat()
                .body("order", notNullValue())
                .and()
                .statusCode(200);
    }

    public Response createOrderUsersWithoutAuth(Order order){
        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .post(URLs.API_ORDER);
        return response;
    }

    public void checkResponseOrdersUsersWithoutAuth(Response response){
                response.then().assertThat()
                .body("order.ingredients", nullValue()).and().assertThat().body("order.number",notNullValue())
                .and()
                .statusCode(200);
    }

    public void checkOrdersUsersWithAuthWithoutIngredients(Response response){
               response.then().assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    public void checkOrdersUsersWithAuthIncorrectIngredients(Response response){
                response.then()
                .statusCode(500);
    }





}
