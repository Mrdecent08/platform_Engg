package com.example.demo.repository;

import com.example.demo.entity.registerDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface registerDetailsRepository extends JpaRepository<registerDetails,Integer>{

	
	Optional<registerDetails> findByApplicationName(String applicationName);


}
