package com.ACC.SpringApp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ACC.SpringApp.dao.UserDAO;
import com.ACC.SpringApp.dao.UserDAOImpl;
import com.ACC.SpringApp.exception.NotFoundException;
import com.ACC.SpringApp.model.User;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    static int limit=2;
    
    @Transactional(readOnly = true)
    public User findById(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found with id: " + id);
        }
        return user;
    }

    @Transactional
    public User saveUser(@Valid User user) {
    	long emailCount = userDAO.countEmail(user.getEmail());

        if (emailCount >= limit) {
            throw new NotFoundException("Cannot have more than " + limit + " users with the same email address.");
        }
        return userDAO.save(user);
    }

    @Transactional
    public boolean deleteUser(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            return false;
        }
        userDAO.delete(user);   
        return true;
    }
    
    @Transactional
    public boolean deleteAll() {
        List<User> users = userDAO.findAll();
        if (users == null) {
            return false;
        } 
        userDAO.deleteAll(users); 
        jdbcTemplate.execute("TRUNCATE TABLE User");
        return true;
    }

    @Transactional
    public List<User> findAll() {
        List<User> users = userDAO.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return users;
    }

    }



