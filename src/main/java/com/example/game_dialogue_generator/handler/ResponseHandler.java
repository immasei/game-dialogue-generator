package com.example.game_dialogue_generator.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> handle(HttpStatus status,
                                             Object message,
                                             Object data) {
        // all responses follow the same format
        //  message = message or list of errors
        //  status = http status code
        //  data = obj/ list of obj/ anything ie list of users
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        body.put("status", status.value());
        body.put("data", data);

        return new ResponseEntity<>(body, status);
    }
}
