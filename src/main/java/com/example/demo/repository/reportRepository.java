package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.reportEntity;
import com.example.demo.entity.s3Report;

@Repository
public interface reportRepository extends JpaRepository<reportEntity, Integer>{

}
