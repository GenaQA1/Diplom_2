import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthUser {

    public Response authUser(UserProfile userProfile){
            Response response = given()
                    .header("Content-type", "application/json")
                    .body(userProfile).log().all()
                    .post(URLs.API_AUTH);
            return response;
        }

    public void checkResponseCorrectAuth(Response response){
        response.then()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    public void checkResponseIncorrectAuth(Response response){
        response.then()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
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

}

