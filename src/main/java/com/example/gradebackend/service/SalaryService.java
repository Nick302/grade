package com.example.gradebackend.service;

import com.example.gradebackend.model.domain.Salary;
import com.example.gradebackend.model.dto.request.PostCreateSalary;

import java.util.List;
import java.util.Optional;

public interface SalaryService {

    Optional<List<Salary>> getAll();

    Optional<Salary> createSalary(PostCreateSalary postCreateSalary);

    Optional<Salary> updateSalary(Integer id, Salary salary);

    boolean deleteSalary(Integer id);
}
