import org.junit.Test;

public class TestingOrdersUsers extends OrderCreate {


    CreateUser createUser = new CreateUser();
    OrderCreate orderCreate = new OrderCreate();


    @Test
    public void createOrderUsersWithAuthWithIngredient(){
        createUser.createCorrectUser();
        orderCreate.OrdersUsersInAuth();
    }

    @Test
    public void createOrderUsersOutAuthWithIngredient(){
        createUser.createCorrectUser();
        orderCreate.OrdersUsersOutAuth();
    }

    @Test
    public void createOrderUsersWithAuthOutIngredient(){
        createUser.createCorrectUser();
        orderCreate.OrdersUsersWithAuthOutIngredients();

    }
    @Test
    public void createOrderUsersWithAuthIncorrectIngredient(){
        createUser.createCorrectUser();
        orderCreate.OrdersUsersWithAuthIncorrectIngredients();

    }


}
