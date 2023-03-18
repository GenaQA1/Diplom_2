import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingPatchUserProfile {

    CreateUser createUser = new CreateUser();
    PatchUserProfile patchUserProfile = new PatchUserProfile();
    AuthUser authUser = new AuthUser();

    @Before
    public void setUp() {
        RestAssured.baseURI = StaticValues.URL_BASE;
        createUser.deleteUser(authUser.responseAuthUser(createUser.getCorrectUser()));
        createUser.deleteUser(authUser.responseAuthUser(patchUserProfile.getChangeNameUserProfile()));
        createUser.deleteUser(authUser.responseAuthUser(patchUserProfile.getChangeMailUserProfile()));
    }

    @After
    public void setDown() {
        createUser.deleteUser(authUser.responseAuthUser(createUser.getCorrectUser()));
        createUser.deleteUser(authUser.responseAuthUser(patchUserProfile.getChangeNameUserProfile()));
        createUser.deleteUser(authUser.responseAuthUser(patchUserProfile.getChangeMailUserProfile()));
    }

    @Test
    @DisplayName("checking the change of the authorized user name")
    public void patchNameUserProfileInAuth() {
        createUser.responseCreateUsers(createUser.getCorrectUser());
        patchUserProfile.changeNameUserProfileAuth(createUser.getCorrectUser(), patchUserProfile.getChangeNameUserProfile());
    }

    @Test
    @DisplayName("checking the change of the authorized user email")
    public void patchEmailUserProfileInAuth() {
        createUser.responseCreateUsers(createUser.getCorrectUser());
        patchUserProfile.changeMailUserProfileAuth(createUser.getCorrectUser(), patchUserProfile.getChangeMailUserProfile());
    }

    @Test
    @DisplayName("checking the receipt of an error for changing the mail to an existing one")
    public void patchMailExistsUserProfileInAuth() {
        createUser.responseCreateUsers(createUser.getCorrectUser());
        patchUserProfile.changeMailUserProfileAuth(createUser.getCorrectUser(), patchUserProfile.getChangeMailUserProfile());
        createUser.responseCreateUsers(createUser.getCorrectUser());
        patchUserProfile.changeMailExistsUserProfileAuth(createUser.getCorrectUser(), patchUserProfile.getChangeMailUserProfile());
    }

    @Test
    @DisplayName("checking the user name change without authorization")
    public void patchNameUserProfileOutAuth() {
        createUser.responseCreateUsers(createUser.getCorrectUser());
        patchUserProfile.changeNameUserProfileOutAuth(patchUserProfile.getChangeNameUserProfile());
    }


    @Test
    @DisplayName("checking the user email change without authorization")
    public void patchEmailUserProfileOutAuth(){
        createUser.responseCreateUsers(createUser.getCorrectUser());
        patchUserProfile.changeMailUserProfileOutAuth(patchUserProfile.getChangeMailUserProfile());
    }
}

