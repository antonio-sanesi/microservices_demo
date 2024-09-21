package it.asanesi.order_service.error;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> handleException(ResponseStatusException exc){
        ErrorDto error = new ErrorDto();
        error.setStatus(exc.getStatusCode().value());
        error.setMessage(exc.getReason());

        return new ResponseEntity<>(error, exc.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException exc){
        ErrorDto error = new ErrorDto();
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        //compongo il messaggio di errore
        error.setMessage(
                exc.getBindingResult().getAllErrors().stream()
                        .map(validationError -> validationError.getDefaultMessage())
                        .collect(Collectors.joining("\n"))
        );

        return new ResponseEntity<>(error, exc.getStatusCode());
    }

    // aggiungo un exception handler per FeignException
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDto> handleException(FeignException exc) {
        ErrorDto error = new ErrorDto();
        error.setStatus(exc.status());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(exc.status()));
    }


    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleException(Exception exc) {
        System.out.println(exc.getClass().getName());

        ErrorDto error = new ErrorDto();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}