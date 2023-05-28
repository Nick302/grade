package com.example.gradebackend.repository;

import com.example.gradebackend.model.domain.Premium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumRepository extends JpaRepository<Premium, Integer> {
    Optional<List<Premium>> findAllBy(Pageable pageable);
}
