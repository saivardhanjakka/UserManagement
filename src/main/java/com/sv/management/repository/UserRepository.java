package com.sv.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sv.management.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmailAndPassword(String email, String password);
       boolean existsByEmail(String email) ;
       User findByEmail(String email);
     
}
