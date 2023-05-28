package com.example.gradebackend.service;

import com.example.gradebackend.model.domain.Employee;
import com.example.gradebackend.model.dto.request.PostSetSalaryRequest;
import com.example.gradebackend.model.dto.request.PostSetTaskRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    void upload(String imagePath, InputStream content) throws IOException;

    Optional<List<Employee>> getAllEmployeesByPage(Pageable pageable);

    Optional<Employee> getById(Integer id);

    Optional<Employee> createEmployee(Employee employee);

    boolean deleteEmployee(Integer id);

    Optional<Employee> updateEmployee(Integer id, Employee employee);

    Optional<Employee> setTaskOnEmployee(PostSetTaskRequest postSetTaskRequest);

    Optional<Employee> setSalaryOnEmployee(PostSetSalaryRequest postSetSalaryRequest);
}
