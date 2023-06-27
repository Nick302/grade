package com.example.gradebackend.service.impl;

import com.example.gradebackend.model.domain.Report;
import com.example.gradebackend.repository.ReportRepository;
import com.example.gradebackend.service.ReportService;
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
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Report>> getAllReportsByPage(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Report> createReport(Report report) {
        return Optional.ofNullable(repository.save(report));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
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
