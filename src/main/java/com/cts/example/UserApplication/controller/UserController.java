package com.cts.example.UserApplication.controller;

import com.cts.example.UserApplication.model.UserOne;
import com.cts.example.UserApplication.response.ResponseHandler;
import com.cts.example.UserApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
//        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        List<UserOne> usersList= userService.getAllUsers();
//        return ResponseHandler.generateResponse("Successfully fetched all users",HttpStatus.OK,usersList);
       if(usersList.isEmpty()){
           return new ResponseEntity<String> ("User List is empty", HttpStatus.NO_CONTENT);
       }else {
           CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.HOURS);
           return ResponseEntity.ok().cacheControl(cacheControl)
                   .body(ResponseHandler.generateResponse("Successfully fetched all users",
                           HttpStatus.OK, usersList));
       }
    }

    @GetMapping("getUserById/{id}")
   public ResponseEntity<?> getUserById(@PathVariable Integer id){
        UserOne user= userService.getUserById(id);
//        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
//        return ResponseHandler.generateResponse("Successfully fetched user",HttpStatus.OK,user);
//        System.out.println("hello");
        if(user!=null){
            CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.HOURS);
            return  ResponseEntity.ok().cacheControl(cacheControl)
                .body(ResponseHandler.generateResponse("Successfully fetched user",
                        HttpStatus.OK, user));
           }else {
            return new ResponseEntity<String>("User List is empty", HttpStatus.NO_CONTENT);

        }
    }
}
