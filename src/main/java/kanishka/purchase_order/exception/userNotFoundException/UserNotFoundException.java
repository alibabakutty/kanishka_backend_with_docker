package kanishka.purchase_order.exception.userNotFoundException;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
