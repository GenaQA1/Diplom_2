import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;


public class GetOrder {


    public String responseToken(UserProfile userProfile) {
        String response =
                given()
                        .header("Content-type", "application/json")
                        .body(userProfile)
                        .post(URLs.API_AUTH)
                        .then().extract().path("accessToken").toString().replace("Bearer ", "");
        return response;
    }


    public Response orderAuthUser(UserProfile userProfile){
        Response response = given()
                .auth().oauth2(responseToken(userProfile)).log().all()
                .header("Content-type", "application/json")
                .get(URLs.API_ORDER);
        return response;
    }


    public void getOrderAuthUser(Response response){
        response.then().assertThat()
                .body("success",equalTo(true))
                .and()
                .statusCode(200);
    }


    public Response orderOutAuth(){
        Response response =given()
                .get(URLs.API_ORDER);
        return response;
    }

    public void getOrderOutAuthUser(Response response){
        response.then().assertThat()
                .body("message",equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
