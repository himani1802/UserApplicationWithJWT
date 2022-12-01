package com.cts.example.UserApplication;

import com.cts.example.UserApplication.controller.UserController;
import com.cts.example.UserApplication.model.UserOne;
import com.cts.example.UserApplication.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockmvc;

    private List<UserOne> userList = new ArrayList();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockmvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    public void getAllUserSuccess() throws Exception {
        UserOne user = new UserOne();
        user.setId(1);
        user.setUsername("Paul");
        user.setPassword("paul@123");
        userList.add(user);
        when(userService.getAllUsers()).thenReturn(userList);
        List<UserOne> uList = userService.getAllUsers();
        assertEquals(userList, uList);
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAllUserFailure() throws Exception {
        userList.clear();
        when(userService.getAllUsers()).thenReturn(userList);
        assertEquals(0,userList.size());
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void addUserSuccess() throws Exception {
        UserOne user  = new UserOne();
        user.setId(1);
        user.setUsername("Paul");
        user.setPassword("paul@123");
        userList.add(user);
        when(userService.addUser(any())).thenReturn(user);
        UserOne user1  = userService.addUser(user);
        assertEquals(user,user1);
        mockmvc.perform(MockMvcRequestBuilders.post("/auth/v1/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)));
    }

    @Test
    public void addUserFailure() throws Exception {
        UserOne user  = new UserOne();
        user.setId(101);
        user.setUsername("Paul");
        user.setPassword("paul@123");
        userList.add(user);
        when(userService.addUser(any())).thenReturn(null);
        UserOne user1  = userService.addUser(user);
        assertNull(user1);
        mockmvc.perform(MockMvcRequestBuilders.post("/auth/v1/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)));
    }
}
