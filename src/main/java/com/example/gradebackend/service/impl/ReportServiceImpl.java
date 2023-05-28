package com.example.gradebackend.service.impl;

import com.example.gradebackend.model.domain.Position;
import com.example.gradebackend.model.domain.Premium;
import com.example.gradebackend.model.domain.Report;
import com.example.gradebackend.model.domain.Salary;
import com.example.gradebackend.model.dto.request.PostCreateSalary;
import com.example.gradebackend.repository.ReportRepository;
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
public class ReportServiceImpl {

    private final ReportRepository repository;

    @Transactional(readOnly = true)
    public Optional<List<Report>> getAllReportsByPage(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<Report> createReport(Report report) {
        return Optional.ofNullable(repository.save(report));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReport(Integer id) {
        Optional<Report> reportForDelete = repository.findById(id);

        if (reportForDelete.isEmpty()) {
            return false;
        }

        repository.delete(reportForDelete.get());
        repository.flush();

        return true;
    }

}
