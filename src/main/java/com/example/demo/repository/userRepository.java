package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
import java.util.List;


@Repository
public interface userRepository extends JpaRepository<User, String>{
	User findByUserId(String userId);
}
