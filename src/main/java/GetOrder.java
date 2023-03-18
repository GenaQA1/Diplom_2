import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;


public class GetOrder {


    public String responseToken(UserProfile userProfile) {
        String response =
                given()
                        .header("Content-type", "application/json")
                        .body(userProfile)
                        .post(StaticValues.API_AUTH)
                        .then().extract().path("accessToken").toString().replace("Bearer ", "");
        return response;
    }

    public void getOrderAuthUser(UserProfile userProfile){
        given()
                .auth().oauth2(responseToken(userProfile)).log().all()
                .header("Content-type", "application/json")
                .get(StaticValues.API_ORDER)
                .then().assertThat()
                .body("success",equalTo(true))
                .and()
                .statusCode(200);
    }

    public void getOrderOutAuthUser(){
        given()
                .get(StaticValues.API_ORDER)
                .then().assertThat()
                .body("message",equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
