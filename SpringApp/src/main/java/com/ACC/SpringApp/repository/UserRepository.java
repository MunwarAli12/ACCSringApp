package com.ACC.SpringApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ACC.SpringApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT COUNT(u) FROM User u WHERE u.email = :email")
	long countEmail(@Param("email") String email);

	
	 



}
