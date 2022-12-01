package com.cts.example.UserApplication;

import com.cts.example.UserApplication.model.UserOne;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserOneTest {

    @Test
    public void test01() {
        UserOne userObj = Mockito.mock(UserOne.class);
        userObj.setUsername("Ian");
        userObj.setPassword("ian@123");

        String mockUsername = userObj.getUsername();
        String mockPassword = userObj.getPassword();

        when(userObj.getUsername()).thenReturn(mockUsername);
        when(userObj.getPassword()).thenReturn(mockPassword);

        assertEquals(userObj.getUsername(), mockUsername);
        assertEquals(userObj.getPassword(),mockPassword);
    }
}

