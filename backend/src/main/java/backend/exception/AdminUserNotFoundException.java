package backend.exception;

public class AdminUserNotFoundException extends RuntimeException{
    public AdminUserNotFoundException(Long id) {
        super("could not find id " + id);
    }
    public AdminUserNotFoundException(String message){
        super(message);
    }
}
