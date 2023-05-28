package com.example.gradebackend.repository;

import com.example.gradebackend.model.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<List<Employee>> findAllBy(Pageable pageable);

    Optional<Employee> findByEmail(String email);
}
