package com.example.gradebackend.repository;

import com.example.gradebackend.model.domain.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    Optional<List<Report>> findAllBy(Pageable pageable);
}
