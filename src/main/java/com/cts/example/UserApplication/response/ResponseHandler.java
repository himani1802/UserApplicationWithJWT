package com.cts.example.UserApplication.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message,
                                                          HttpStatus status,
                                                          Object responseObj) {
        Map<String, Object> mapObj = new HashMap<String, Object>();
        mapObj.put("Message", message);
        mapObj.put("Status", status);
        mapObj.put("Data", responseObj);
        return new ResponseEntity<Object>(mapObj, status);
    }

}
