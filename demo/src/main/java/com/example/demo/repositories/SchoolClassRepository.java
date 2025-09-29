package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.SchoolClass;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Integer> {
	
       //method to check for existing classes
       boolean existsByName(String name); 
}