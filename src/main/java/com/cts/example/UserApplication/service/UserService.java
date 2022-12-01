package com.cts.example.UserApplication.service;

import com.cts.example.UserApplication.model.UserOne;

import java.util.List;

public interface UserService {

    public List<UserOne> getAllUsers();

    public UserOne getUserById(Integer id);

    public UserOne addUser(UserOne user);

    public boolean authorizeUser(String username, String password);

}
