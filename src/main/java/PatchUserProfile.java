
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class PatchUserProfile {


    public UserProfile getChangeMailUserProfile() {
        return changeMailUserProfile;
    }
    public UserProfile getChangeNameUserProfile() {
        return changeNameUserProfile;
    }

    UserProfile changeMailUserProfile = new UserProfile("gena.chebotar9@mail.ru","GoLittleRockStar","MusicApple1");
    UserProfile changeNameUserProfile = new UserProfile("gena.chebotar@mail.ru","GoLittleRockStar","MusicApple22");

    public String responseToken(UserProfile userProfile) {
        String response =
                given()
                        .header("Content-type", "application/json")
                        .body(userProfile)
                        .post(StaticValues.API_AUTH)
                        .then().extract().path("accessToken").toString().replace("Bearer ", "");
        return response;
    }
    public void changeNameUserProfileAuth(UserProfile userProfile, UserProfile changeData) {
        given()
                .auth().oauth2(responseToken(userProfile))
                .header("Content-type", "application/json")
                .body(changeData)
                .patch(StaticValues.API_PATH)
                .then().assertThat()
                .body("user.name", equalTo("MusicApple22"))
                .and()
                .statusCode(200);
    }
    public void changeMailUserProfileAuth(UserProfile userProfile, UserProfile changeData) {
        given()
                .auth().oauth2(responseToken(userProfile))
                .header("Content-type", "application/json")
                .body(changeData)
                .patch(StaticValues.API_PATH)
                .then().assertThat()
                .body("user.email", equalTo("gena.chebotar9@mail.ru"))
                .and()
                .statusCode(200);
    }


    public void changeMailExistsUserProfileAuth(UserProfile userProfile, UserProfile changeData) {

        given()
                .auth().oauth2(responseToken(userProfile))
                .header("Content-type", "application/json")
                .body(changeData)
                .patch(StaticValues.API_PATH)
                .then().assertThat().log().all()
                .body("message", equalTo("User with such email already exists"))
                .and()
                .statusCode(403);
    }

    public void changeNameUserProfileOutAuth(UserProfile changeData) {
        given()
                .header("Content-type", "application/json")
                .body(changeData)
                .patch(StaticValues.API_PATH)
                .then()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }

    public void changeMailUserProfileOutAuth(UserProfile changeData) {
        given()
                .header("Content-type", "application/json")
                .body(changeData)
                .patch(StaticValues.API_PATH)
                .then()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}



