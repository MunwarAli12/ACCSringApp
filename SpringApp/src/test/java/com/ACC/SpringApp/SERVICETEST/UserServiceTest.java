package com.ACC.SpringApp.SERVICETEST;



import com.ACC.SpringApp.dao.UserDAO;
import com.ACC.SpringApp.model.User;
import com.ACC.SpringApp.service.UserService;
import com.ACC.SpringApp.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    private User user;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    public void testFindById_UserExists() {
        when(userDAO.findById(1L)).thenReturn(user);

        User foundUser = userService.findById(1L);

        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(userDAO).findById(1L);
    }

    @Test
    public void testFindById_UserNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);

        NotFoundException thrown = assertThrows(
            NotFoundException.class,
            () -> userService.findById(1L)
        );

        assertTrue(thrown.getMessage().contains("User not found with id: 1"));
        verify(userDAO).findById(1L);
    }

    @Test
    public void testSaveUser() {
        when(userDAO.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(user.getUsername(), savedUser.getUsername());
        verify(userDAO).save(user);
    }

    @Test
    public void testDeleteUser_UserExists() {
        when(userDAO.findById(1L)).thenReturn(user);
        doNothing().when(userDAO).delete(user);

        userService.deleteUser(1L);

        verify(userDAO).findById(1L);
        verify(userDAO).delete(user);
    }

    
}

