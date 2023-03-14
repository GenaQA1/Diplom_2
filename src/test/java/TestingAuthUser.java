import org.junit.Test;

public class TestingAuthUser extends AuthUser{

    AuthUser authUser = new AuthUser();
    CreateUser createUser = new CreateUser();

    @Test
    public void authCorrectUser(){
        createUser.createCorrectUser();
        authUser.authCorrectUser();
    }

    @Test
    public void authUserOutMail(){
        createUser.createCorrectUser();
        authUser.authUserOutMail();
    }

    @Test
    public void authUserOutPassword(){
        createUser.createCorrectUser();
        authUser.authUserPassword();
    }
}
