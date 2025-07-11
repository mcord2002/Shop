package backend.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id) {
        super("could not find id " + id);
    }
    public CategoryNotFoundException(String message){
        super(message);
    }
}
