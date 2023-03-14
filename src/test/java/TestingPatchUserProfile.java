import org.junit.Test;

public class TestingPatchUserProfile extends PatchUserProfile{

    CreateUser createUser = new CreateUser();
    PatchUserProfile patchUserProfile = new PatchUserProfile();

    @Test
    public void patchNameUserProfileInAuth(){
        createUser.createCorrectUser();
        patchUserProfile.changeNameUserProfileAuth();
    }


    @Test
    public void patchMailUserProfileInAuth(){
        patchUserProfile.deliteUsersPatchMail();
        createUser.createCorrectUser();
        patchUserProfile.changeMailUserProfileAuth();
        patchUserProfile.deliteUsersPatchMail();
    }

    @Test
    public void patchMailExistsUserProfileInAuth(){
        patchUserProfile.deliteUsersPatchMail();
        createUser.createCorrectUser();
        patchUserProfile.changeMailUserProfileAuth();
        createUser.createCorrectUser();
        patchUserProfile.changeMailExistsUserProfileAuth();
    }

    @Test
    public void patchNameUserProfileOutAuth(){
        createUser.createCorrectUser();
        patchUserProfile.changeNameUserProfileOutAuth();

    }


    @Test
    public void patchMailUserProfileOutAuth(){
        createUser.createCorrectUser();
        patchUserProfile.changeMailUserProfileOutAuth();

    }


}
