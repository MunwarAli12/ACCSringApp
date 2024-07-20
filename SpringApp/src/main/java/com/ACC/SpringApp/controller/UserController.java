package com.ACC.SpringApp.controller;

import com.ACC.SpringApp.exception.NotFoundException;
import com.ACC.SpringApp.model.User; 
import com.ACC.SpringApp.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController { 

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> findll() {
        List<User> user = userService.findAll();
        return ResponseEntity.ok(user);
    }
    

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.saveUser(user);
        
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok("User successfully deleted.");
            
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + id);
        }
    }
    
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
          boolean idd = userService.deleteAll();
            if(idd) {
            	return ResponseEntity.ok("Users not found ");
            }
            else {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("All users data successfully deleted" );
            }
    }
}
