import org.junit.Test;

public class TestingGetOrder extends  GetOrder{

    CreateUser createUser = new CreateUser();
    GetOrder getOrder = new GetOrder();

    @Test
    public void getOrderWithAuth(){
        createUser.createCorrectUser();
        getOrder.getOrderAuthUser();
    }

    @Test
    public void getOrderOutAuth(){
        createUser.createCorrectUser();
        getOrder.getOrderOutAuthUser();
    }
}
