package com.example.gradebackend.service;

import com.example.gradebackend.model.domain.Department;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Optional<List<Department>> getAllDepartmentsByPage(Pageable pageable);

    Optional<Department> getDepartmentById(Integer id);

    Optional<Department> createDepartment(Department department);

    Optional<Department> updateDepartment(Integer id, Department department);

    boolean deleteDepartmentById(Integer id);
}
