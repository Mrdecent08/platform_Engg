package com.example.demo.repository;

import com.example.demo.entity.registerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface registerDetailsRepository extends JpaRepository<registerDetails,Integer>{

	String findTokenByApplicationName(String applicationName);

}
