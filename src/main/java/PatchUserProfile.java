
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class PatchUserProfile {

AuthUser authUser = new AuthUser();


    UserProfile changeMailUserProfile = new UserProfile("gena.chebotar9@mail.ru","GoLittleRockStar","MusicApple1");
    UserProfile changeNameUserProfile = new UserProfile("gena.chebotar@mail.ru","GoLittleRockStar","MusicApple22");

    public UserProfile getChangeMailUserProfile() {
        return changeMailUserProfile;
    }
    public UserProfile getChangeNameUserProfile() {
        return changeNameUserProfile;
    }

    public Response changeUserAuth(UserProfile userProfile, UserProfile changeData){
        Response response = given()
                .auth().oauth2(authUser.responseToken(userProfile))
                .header("Content-type", "application/json")
                .body(changeData)
                .patch(URLs.API_PATH);
        return response;
    }


    public void checkChangeNameUserProfileAuth(Response response) {
                response.then().assertThat()
                .body("user.name", equalTo("MusicApple22"))
                .and()
                .statusCode(200);
    }
    public void checkChangeMailUserProfileAuth(Response response) {
               response.then().assertThat()
                .body("user.email", equalTo("gena.chebotar9@mail.ru"))
                .and()
                .statusCode(200);
    }


    public void checkChangeMailExistsUserProfileAuth(Response response) {
        response.then().assertThat().log().all()
                .body("message", equalTo("User with such email already exists"))
                .and()
                .statusCode(403);
    }

    public Response changeUserOutAuth(UserProfile changeData){
        Response response =  given()
                .header("Content-type", "application/json")
                .body(changeData)
                .patch(URLs.API_PATH);
        return response;
    }
    public void checkChangeNameUserProfileOutAuth(Response response) {
                response.then()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }

    public void checkChangeMailUserProfileOutAuth(Response response) {
                response.then()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}



