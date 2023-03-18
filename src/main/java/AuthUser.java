import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthUser {

    public Response responseAuthUser(UserProfile userProfile){
            Response response = given()
                    .header("Content-type", "application/json")
                    .body(userProfile).log().all()
                    .post(StaticValues.API_AUTH);
            return response;
        }

    public void checkCorrectResponseAnswer(Response response){
        response.then()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    public void checkCorrectErrorResponse(Response response){
        response.then()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
    }
}

