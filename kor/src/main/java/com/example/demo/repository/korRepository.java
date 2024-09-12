package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.korReport;

@Repository
public interface korRepository extends JpaRepository<korReport, Integer>{

	List<korReport> findAlertByAlertname(String alertName);


}
