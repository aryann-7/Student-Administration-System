package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Term;

public interface TermRepository extends JpaRepository<Term, Long> {
	
    //term repo already gets CRUD operations as it extends JPA Repository, 
    //and type entity already has a id of type LONG, so no methods needed here
}