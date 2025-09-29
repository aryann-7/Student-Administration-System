package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.SchoolClass;
import com.example.demo.repositories.SchoolClassRepository;

@Service
public class ClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    //create/update a class
    public SchoolClass saveClass(SchoolClass schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    //get a class by ID
    public Optional<SchoolClass> getClassById(int id) {
        return schoolClassRepository.findById(id);
    }

    //get classes by name
    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }

    //delete class
    public void deleteClass(int id) {
        schoolClassRepository.deleteById(id);
    }
    //check if class exists by name
    public boolean existsByName(String name) {
        return schoolClassRepository.existsByName(name);
    }
}