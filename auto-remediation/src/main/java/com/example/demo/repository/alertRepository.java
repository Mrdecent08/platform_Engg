package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.alertDetails;

@Repository
public interface alertRepository extends JpaRepository<alertDetails, Integer>{

	List<alertDetails> findAlertByAlertname(String alertName);

}
