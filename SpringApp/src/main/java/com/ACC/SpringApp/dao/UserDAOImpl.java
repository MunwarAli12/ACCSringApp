package com.ACC.SpringApp.dao;



import com.ACC.SpringApp.model.User;
import com.ACC.SpringApp.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

   
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    
    public List<User> findAll() {
    	return userRepository.findAll();
    }

  
    public User save(User user) {
        return userRepository.save(user);
    }

  
    public void delete(User user) {
        userRepository.delete(user);
    }

	
	public long countEmail(String email) {
		return userRepository.countEmail(email);
	}

	
    public void deleteAll(List<User> users) {
        userRepository.deleteAll(users);
    }



	
}
