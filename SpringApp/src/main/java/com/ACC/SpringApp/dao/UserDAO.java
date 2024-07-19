package com.ACC.SpringApp.dao;

import java.util.List;

import com.ACC.SpringApp.model.User;

public interface UserDAO {
    User findById(Long id);
    User save(User user);
    void delete(User user);
    List<User> findAll();
    long countEmail(String email);
   void deleteAll(List<User> users);
  
}
