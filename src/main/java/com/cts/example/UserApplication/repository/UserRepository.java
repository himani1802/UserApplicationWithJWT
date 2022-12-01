package com.cts.example.UserApplication.repository;

import com.cts.example.UserApplication.model.UserOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserOne, Integer> {

    @Query(value="SELECT * from user_one where user_one.username= :username and user_one.password= :password", nativeQuery = true)
    public UserOne authorizeUser(String username, String password);
}
