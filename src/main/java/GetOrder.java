import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;


public class GetOrder {

    AuthUser authUser = new AuthUser();

    public Response orderAuthUser(UserProfile userProfile){
        Response response = given()
                .auth().oauth2(authUser.responseToken(userProfile)).log().all()
                .header("Content-type", "application/json")
                .get(URLs.API_ORDER);
        return response;
    }


    public void checkCorrectResponseGetOrder(Response response){
        response.then().assertThat()
                .body("success",equalTo(true))
                .and()
                .statusCode(200);
    }


    public Response orderWithoutAuth(){
        Response response =
                given()
                .get(URLs.API_ORDER);
        return response;
    }

    public void checkCorrectResponseOrderWithoutAuthUser(Response response){
        response.then().assertThat()
                .body("message",equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
