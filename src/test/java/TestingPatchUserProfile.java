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
    public void patchNameUserProfileInAuth() {
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeNameUserProfileAuth(patchUserProfile.pathUserAuth(userClient.getCorrectUser(),patchUserProfile.getChangeNameUserProfile()));
    }

    @Test
    @DisplayName("checking the change of the authorized user email")
    public void patchEmailUserProfileInAuth() {
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeMailUserProfileAuth(patchUserProfile.pathUserAuth(userClient.getCorrectUser(),patchUserProfile.getChangeMailUserProfile()));
    }

    @Test
    @DisplayName("checking the receipt of an error for changing the mail to an existing one")
    public void patchMailExistsUserProfileInAuth() {
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeMailUserProfileAuth(patchUserProfile.pathUserAuth(userClient.getCorrectUser(),patchUserProfile.getChangeMailUserProfile()));
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeMailExistsUserProfileAuth(patchUserProfile.pathUserAuth(userClient.getCorrectUser(),patchUserProfile.getChangeMailUserProfile()));
    }

    @Test
    @DisplayName("checking the user name change without authorization")
    public void patchNameUserProfileOutAuth() {
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeNameUserProfileOutAuth(patchUserProfile.pathUserOutAuth(patchUserProfile.getChangeNameUserProfile()));
    }


    @Test
    @DisplayName("checking the user email change without authorization")
    public void patchEmailUserProfileOutAuth(){
        userClient.createUser(userClient.getCorrectUser());
        patchUserProfile.checkChangeMailUserProfileOutAuth(patchUserProfile.pathUserOutAuth(patchUserProfile.getChangeMailUserProfile()));
    }
}

