package com.cts.example.UserApplication;

import com.cts.example.UserApplication.model.UserOne;
import com.cts.example.UserApplication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureMockMvc
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    UserOne user = new UserOne();

    @BeforeEach
    public void init() {
        user.setId(1);
        user.setUsername("Paul");
        user.setPassword("paul@123");
    }

    @Test
    public void saveUserSuccess() throws Exception {
        UserOne u1 = null;
        userRepository.save(user);

        u1= userRepository.findById(user.getId()).get();

        assertEquals(user.getUsername(), u1.getUsername());
    }

    @Test
    public void saveUserFailure() throws Exception {
        UserOne u1 = null;
        if(userRepository.findAll().toString().isEmpty()) {
            userRepository.save(user);
            u1 = userRepository.findById(user.getId()).get();
        }
        assertNull(u1);
    }
}
