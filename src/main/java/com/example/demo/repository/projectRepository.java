package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Projects;

@Repository
public interface projectRepository extends JpaRepository<Projects, String> {

}
