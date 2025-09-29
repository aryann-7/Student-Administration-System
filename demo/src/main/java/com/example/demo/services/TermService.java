package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Term;
import com.example.demo.repositories.TermRepository;

@Service
public class TermService {

    @Autowired
    private TermRepository termRepository;

    public void createTerm(Term term) {
        //save term to db
        termRepository.save(term);
    }

    public List<Term> getAllTerms() {
        //return term list
        return termRepository.findAll();
    }
    
    //method to save term
    public Term saveTerm(Term term) {
        return termRepository.save(term);
    }
}