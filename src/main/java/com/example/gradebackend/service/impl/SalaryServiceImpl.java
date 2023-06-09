package com.example.gradebackend.service.impl;

import com.example.gradebackend.model.domain.Position;
import com.example.gradebackend.model.domain.PositionTitle;
import com.example.gradebackend.model.domain.Salary;
import com.example.gradebackend.model.dto.request.PostCreatePosition;
import com.example.gradebackend.model.dto.request.PostCreateSalary;
import com.example.gradebackend.repository.PositionRepository;
import com.example.gradebackend.repository.SalaryRepository;
import com.example.gradebackend.service.SalaryService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Salary>> getAll() {
        return Optional.ofNullable(salaryRepository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Salary> createSalary(PostCreateSalary postCreateSalary) {
        Optional<Position> foundPosition = positionRepository.findById(postCreateSalary.getIdPosition());
        if(foundPosition.isEmpty()) return Optional.empty();
        Salary salary = new Salary();
        salary.setAmount(postCreateSalary.getAmount());
        salary.setPosition(foundPosition.get());
        return Optional.ofNullable(salaryRepository.save(salary));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Salary> updateSalary(Integer id, Salary salary) {
        Optional<Salary> foundSalary = salaryRepository.findById(id);

        if (foundSalary.isEmpty()) {
            return Optional.empty();
        }

        salary.setId(id);

        return Optional.ofNullable(salaryRepository.save(salary));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSalary(Integer id) {
        Optional<Salary> salaryForDelete = salaryRepository.findById(id);

        if (salaryForDelete.isEmpty()) {
            return false;
        }

        salaryRepository.delete(salaryForDelete.get());
        salaryRepository.flush();

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Position> createPosition(PostCreatePosition position) {
        Position newPositon = new Position();
        var namePosition = PositionTitle.valueOf(position.getNamePos());

        if(namePosition != null) {
            newPositon.setPositionTitle(namePosition);
            return Optional.ofNullable(positionRepository.save(newPositon));
        }

        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Salary> getSalaryById(Integer id) {
        return salaryRepository.findById(id);
    }
}
