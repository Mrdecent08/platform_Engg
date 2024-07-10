package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Metrics;

@Repository
public interface metricsRepository extends JpaRepository<Metrics, Integer>{

	Optional<Metrics> findByAlertName(String alertName);


}
