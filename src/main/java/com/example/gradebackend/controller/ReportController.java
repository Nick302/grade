package com.example.gradebackend.controller;

import com.example.gradebackend.model.domain.Premium;
import com.example.gradebackend.model.domain.Report;
import com.example.gradebackend.service.impl.ReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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

import static com.example.gradebackend.util.Constant.API_REPORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = API_REPORT, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceImpl reportService;

    @Operation(summary = "Getting a complete list of reports by page",
            description = "Getting a complete list of reports by page, " +
                    "in the URL you can pass information for the sample. "
                    + "Example: http://site.com/api/v1/report?page=0&size=10&sort=id,ASC")
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

    @Operation(summary = "Create report",
            description = "Create report, need json body.")
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

    @Operation(summary = "Delete report by id",
    description = "Delete report by id, need id in url"
    )
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
