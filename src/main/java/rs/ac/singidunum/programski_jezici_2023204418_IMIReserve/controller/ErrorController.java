package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@CrossOrigin
public class ErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception e) {
        Map<String, Object> json = new HashMap<>();
        json.put("message", e.getMessage());
        json.put("timestamp", System.currentTimeMillis());
        return json;
    }
}
