package com.cts.example.UserApplication;

import com.cts.example.UserApplication.model.UserOne;
import com.cts.example.UserApplication.repository.UserRepository;
import com.cts.example.UserApplication.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Autowired
    private MockMvc mockmvc;

    private List<UserOne> userList = new ArrayList();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockmvc = MockMvcBuilders.standaloneSetup(userService).build();
    }
    @Test
    public void getAllUserSuccess() throws Exception {
        UserOne user = new UserOne();
        user.setId(1);
        user.setUsername("Paul");
        user.setPassword("paul@123");
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        List<UserOne> uList = userService.getAllUsers();
        assertEquals(userList, uList);
    }

    @Test
    public void getAllUserFailure() throws Exception {
        when(userRepository.findAll()).thenReturn(null);
        List<UserOne> uList = userService.getAllUsers();
        assertNull(uList);
    }

    @Test
    public void addUserSuccess() throws Exception {
        UserOne user  = new UserOne();
        user.setId(101);
        user.setUsername("Paul");
        user.setPassword("paul@123");
        userList.add(user);
        when(userRepository.save(any())).thenReturn(user);
        UserOne user1  = userService.addUser(user);
        assertEquals(user,user1);
    }

    @Test
    public void addUserFailure() throws Exception {
        UserOne user  = new UserOne();
        user.setId(101);
        user.setUsername("Paul");
        user.setPassword("paul2123");
        userList.add(user);
        when(userRepository.save(any())).thenReturn(null);
        UserOne user1  = userService.addUser(user);
        assertNull(user1);
    }
}
