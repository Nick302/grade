package com.example.gradebackend.service.impl;

import com.example.gradebackend.model.domain.Department;
import com.example.gradebackend.repository.DepartmentRepository;
import com.example.gradebackend.service.DepartmentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Department>> getAllDepartmentsByPage(Pageable pageable) {
        return departmentRepository.findAllBy(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Department> getDepartmentById(Integer id) {
        return departmentRepository.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Department> createDepartment(Department department) {
        return Optional.ofNullable(departmentRepository.save(department));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteDepartmentById(Integer id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isEmpty()) return false;
        departmentRepository.delete(department.get());
        departmentRepository.flush();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Department> updateDepartment(Integer id, Department department) {
        Optional<Department> foundDepartment = departmentRepository.findById(id);
        if (foundDepartment.isEmpty()) {
            return Optional.empty();
        }
        department.setId(id);
        return Optional.ofNullable(departmentRepository.save(department));
    }
}
