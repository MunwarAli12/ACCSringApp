package com.ACC.SpringApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ACC.SpringApp.dao.UserDAO;
import com.ACC.SpringApp.exception.NotFoundException;
import com.ACC.SpringApp.model.User;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final int EMAIL_LIMIT = 1;

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
        
        User existingUser = userDAO.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new NotFoundException("Username '" + user.getUsername() + "' is already taken.");
        }

        if (emailCount >= EMAIL_LIMIT) {
            throw new NotFoundException("Email registration limit reached. Only " + EMAIL_LIMIT + " registration allowed.");
        }
        
     // Validation for programming language and framework
        String lang = user.getProgrammingLanguage().toLowerCase();
        String fw = user.getFramework().toLowerCase();

        if (lang.equals("java") && !fw.equals("spring boot")) {
            throw new NotFoundException("If programming language is Java, the framework must be Spring Boot.");
        }

        if ((fw.equals("react") || fw.equals("angular") || fw.equals("expressjs")) && !lang.equals("javascript")) {
            throw new NotFoundException("If framework is React, Angular, or ExpressJS, programming language must be JavaScript.");
        }
        
        return userDAO.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userDAO.delete(user);
    }

    @Transactional
    public void deleteAll() {
        List<User> users = userDAO.findAll();
        if (users == null || users.isEmpty()) {
            throw new NotFoundException("No users to delete.");
        }
        userDAO.deleteAll(users);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> users = userDAO.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("No users found.");
        }
        return users;
    }
}
