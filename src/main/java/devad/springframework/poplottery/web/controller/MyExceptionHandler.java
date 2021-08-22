package devad.springframework.poplottery.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> validationBindExceptionHandler(BindException e){
        return new ResponseEntity<>("Form must contain valid data!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> validationMethodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException e){
        return new ResponseEntity<>("Not a valid API endpoint", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> validationMethodArgumentTypeMismatchHandler(HttpRequestMethodNotSupportedException e){
        return new ResponseEntity<>("Not a valid API endpoint", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> validationMethodArgumentTypeMismatchHandler(NumberFormatException e){
        return new ResponseEntity<>("Invalid characters", HttpStatus.BAD_REQUEST);
    }
}
