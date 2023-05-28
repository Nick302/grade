package com.example.gradebackend.controller;

import com.example.gradebackend.model.domain.Premium;
import com.example.gradebackend.model.domain.Report;
import com.example.gradebackend.service.impl.ReportServiceImpl;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/report", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceImpl reportService;

    @GetMapping
    public ResponseEntity<Optional<List<Report>>> getAllReportsByPage(Pageable pageable) {
        try {
            return new ResponseEntity<>(reportService.getAllReportsByPage(pageable), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Report>> createReport(@RequestBody Report report) {
        try {
            return new ResponseEntity<>(reportService.createReport(report), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReport(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(reportService.deleteReport(id), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
