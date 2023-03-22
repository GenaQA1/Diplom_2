import io.restassured.response.Response;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class UserClient {


    UserProfile correctUser = new UserProfile("gena.chebotar@mail.ru", "GoLittleRockStar", "MusicApple1");
    UserProfile userWithoutMail = new UserProfile(null, "GoLittleRockStar", "MusicApple1");
    UserProfile userWithoutPassword = new UserProfile("gena.chebotar@mail.ru", null, "MusicApple1");
    UserProfile userWithoutName = new UserProfile("gena.chebotar@mail.ru", "GoLittleRockStar", null);



    public UserProfile getCorrectUser() {
        return correctUser;
    }
    public UserProfile getUserWithoutMail() {
        return userWithoutMail;
    }

    public UserProfile getUserWithoutPassword() {
        return userWithoutPassword;
    }

    public UserProfile getUserWithoutName() {
        return userWithoutName;
    }

    /*Подобным образом я хотел реализовать рандомные данные для аккаунта. Но когда я к примеру начинаю использовать его в другом методе, то он генерит новые данные
    Получается что пока не сильно смог придумать как его использовать с великой пользой
    UserProfile correctUser = new UserProfile(randomWrite(8) + "@mail.ru", randomWrite(8), randomWrite(8));
    */



    public Response createUser(UserProfile userProfile) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(userProfile).log().all()
                .post(URLs.API_REGISTR);
        return response;
    }

    public void checkCorrectResponseCreateUser(Response response){
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    public void checkCreateAgainUser(Response response) {
        response.then()
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }

    public void checkResponseErrorInBody(Response response) {
        response.then().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    public void deleteUser(Response response) {
        if (response.path("accessToken") != null) {
            given()
                    .auth().oauth2(((response.then().extract().path("accessToken")).toString().replace("Bearer ", "")))
                    .delete(URLs.API_DELITE)
                    .then().assertThat().body("message", equalTo("User successfully removed"))
                    .and()
                    .statusCode(202);
        }
    }

////   метод возвращающий рандом.
//    public String randomWrite(int lengthSize) {
//        String emailAddress = "";
//        String alphabet = "abcdefghijklmnopqrstuvwxyz";
//        while (emailAddress.length() < lengthSize) {
//            int character = (int) (Math.random() * 26);
//            emailAddress += alphabet.substring(character, character + 1);
//            emailAddress += Integer.valueOf((int) (Math.random() * 99)).toString();
//        }
//return emailAddress;
//    }
}

