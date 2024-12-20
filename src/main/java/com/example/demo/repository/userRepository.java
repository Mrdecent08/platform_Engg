package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Budgets;
import com.example.demo.entity.Users;

@Repository
public interface userRepository extends JpaRepository<Users, Integer>{

	Optional<Users> findByUsername(String username);

	Optional<Users> findByUsernameAndProjectName(String username,String projectName);


}
