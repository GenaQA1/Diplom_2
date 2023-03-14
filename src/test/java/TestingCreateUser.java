import org.junit.Test;

public class TestingCreateUser extends CreateUser {
    CreateUser createUser = new CreateUser();

    @Test
    public void createCorrectUser(){
        createUser.createCorrectUser();
    }

    @Test
    public void reRegistrationUser(){
        createUser.createCorrectUser();
        createUser.createAgainUser();
    }

    @Test
    public void registrationUserOutMail(){
        createUser.createUserOutMail();
    }

    @Test
    public void registrationUserOutPassword(){
        createUser.createUserOutPassword();
    }

    @Test
    public void registrationUserOutName(){
        createUser.createUserOutName();
    }
}
