import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUser {


    File correctUser = new File("src/main/resources/CreateUser/correctUser.json");
    File notFieldMail = new File("src/main/resources/CreateUser/notFieldMail.json");
    File notFieldPassword = new File("src/main/resources/CreateUser/notFieldPassword.json");
    File notFieldName = new File("src/main/resources/CreateUser/notFieldName.json");

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        deliteUser();
    }



    public void createCorrectUser(){

        Response response = given()
                .header("Content-type", "application/json")
                .body(correctUser)
                .post("/api/auth/register");
        response.then().body("success", equalTo(true)).and().statusCode(200);
    }
    public void createAgainUser(){

        Response response = given()
                .header("Content-type", "application/json")
                .body(correctUser)
                .post("/api/auth/register");
        response.then()
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }

    public void createUserOutMail(){

        Response response = given()
                .header("Content-type", "application/json")
                .body(notFieldMail)
                .post("/api/auth/register");
        response.then().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    public void createUserOutPassword(){

        Response response = given()
                .header("Content-type", "application/json")
                .body(notFieldPassword)
                .post("/api/auth/register");
        response.then().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
    public void createUserOutName(){

        Response response = given()
                .header("Content-type", "application/json")
                .body(notFieldName)
                .post("/api/auth/register");
        response.then().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @After
    public void deliteUser(){
        Response response =  given()
                .header("Content-type", "application/json")
                .body(correctUser)
                .post("api/auth/login");

        if (response.path("accessToken") != null){
        response.then().assertThat().body("accessToken",notNullValue())
                .and()
                .statusCode(200);

        given()
                .auth().oauth2(( ((String) response.then().extract().path("accessToken")).replace("Bearer ","")))
                .delete("api/auth/user")
                .then().assertThat().body("message",equalTo("User successfully removed"))
                .and()
                .statusCode(202);
    }

}


}
