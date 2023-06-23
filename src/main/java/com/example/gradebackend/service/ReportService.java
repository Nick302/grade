package com.example.gradebackend.service;

import com.example.gradebackend.model.domain.Report;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Optional<List<Report>> getAllReportsByPage(Pageable pageable);

    Optional<Report> createReport(Report report);

    boolean deleteReport(Integer id);
}
