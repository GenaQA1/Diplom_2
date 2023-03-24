import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingPatchUserProfile {

    UserClient userClient = new UserClient();
    PatchUserProfile patchUserProfile = new PatchUserProfile();
    AuthUser authUser = new AuthUser();

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.URL_BASE;
    }

    @After
    public void setDown() {
        userClient.deleteUser(authUser.authUser(userClient.getCorrectUser()));
        userClient.deleteUser(authUser.authUser(patchUserProfile.getChangeNameUserProfile()));
        userClient.deleteUser(authUser.authUser(patchUserProfile.getChangeMailUserProfile()));
    }

    @Test
    @DisplayName("checking the change of the authorized user name")
    public void changeNameUserProfileInAuth() {
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeDataUserProfileAuth(patchUserProfile.changeUserAuth(userClient.getCorrectUser(),patchUserProfile.getChangeNameUserProfile()),"user.name","MusicApple22");
    }

    @Test
    @DisplayName("checking the change of the authorized user email")
    public void changeEmailUserProfileInAuth() {
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeDataUserProfileAuth(patchUserProfile.changeUserAuth(userClient.getCorrectUser(),patchUserProfile.getChangeMailUserProfile()),"user.email","gena.chebotar9@mail.ru");
    }

    @Test
    @DisplayName("checking the receipt of an error for changing the mail to an existing one")
    public void changeMailExistsUserProfileInAuth() {
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeDataUserProfileAuth(patchUserProfile.changeUserAuth(userClient.getCorrectUser(),patchUserProfile.getChangeMailUserProfile()),"user.email","gena.chebotar9@mail.ru");
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeMailExistsUserProfileAuth(patchUserProfile.changeUserAuth(userClient.getCorrectUser(),patchUserProfile.getChangeMailUserProfile()));
    }

    @Test
    @DisplayName("checking the user name change without authorization")
    public void changeNameUserProfileOutAuth() {
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeDataUserProfileWithoutAuth(patchUserProfile.changeUserWithoutAuth(patchUserProfile.getChangeNameUserProfile()));
    }


    @Test
    @DisplayName("checking the user email change without authorization")
    public void changeEmailUserProfileOutAuth(){
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeDataUserProfileWithoutAuth(patchUserProfile.changeUserWithoutAuth(patchUserProfile.getChangeMailUserProfile()));
    }
}

