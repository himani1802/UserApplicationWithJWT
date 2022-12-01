package com.cts.example.UserApplication.service;

import com.cts.example.UserApplication.exception.UserNotFoundException;
import com.cts.example.UserApplication.model.UserOne;
import com.cts.example.UserApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserOne> getAllUsers() {
        List<UserOne> list = userRepository.findAll();
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public UserOne getUserById(Integer id) {
        Optional<UserOne> user=this.userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UserNotFoundException("User not found :" +id);
        }
    }

    @Override
    public UserOne addUser(UserOne user) {

            return userRepository.save(user);

    }

    @Override
    public boolean authorizeUser(String username, String password) {
        UserOne user = userRepository.authorizeUser(username, password);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }
}
