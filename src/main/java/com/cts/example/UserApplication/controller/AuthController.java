package com.cts.example.UserApplication.controller;

import com.cts.example.UserApplication.model.UserOne;

import com.cts.example.UserApplication.service.UserServiceImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@SecurityRequirements
@RequestMapping("/auth/v1/user/")
public class AuthController {

    @Autowired
    private UserServiceImpl service;

    @PostMapping("addUser")
    public ResponseEntity<?> addNewUser(@RequestBody UserOne user){
        UserOne u = service.addUser(user);
        if(u!=null) {
            return new ResponseEntity<String> ("User is added", HttpStatus.CREATED);
        }
        return new ResponseEntity<String> ("User is not added", HttpStatus.NO_CONTENT);

    }

    public String generateToken(String username,String password) throws ServletException {
        String jwtToken="";
        if(username==null || password == null) {
            throw new ServletException("Please enter valid username and password");
        }
        boolean flag = service.authorizeUser(username, password);
        if(!flag) {
            throw new ServletException("Invalid credentials");
        }
        else {
            jwtToken = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+6000000))
                    .signWith(SignatureAlgorithm.HS256, "mySecretKey")
                    .compact();
        }
        return jwtToken;
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody UserOne user){
         Map<String, String> map = new HashMap<String, String>();
         try {
            String getToken = generateToken(user.getUsername(), user.getPassword());
            map.put("message", "User login successful");
            map.put("Token", getToken);
        }
        catch(Exception e) {
            map.put("message", "User login failed");
            map.put("Token", null);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
