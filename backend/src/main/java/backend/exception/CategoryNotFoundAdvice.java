package backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class CategoryNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public Map<String, String> exceptionHandler(CategoryNotFoundException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return errorMap;
    }
}
