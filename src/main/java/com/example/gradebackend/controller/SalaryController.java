package com.example.gradebackend.controller;

import com.example.gradebackend.model.domain.Position;
import com.example.gradebackend.model.domain.Salary;
import com.example.gradebackend.model.dto.request.PostCreatePosition;
import com.example.gradebackend.model.dto.request.PostCreateSalary;
import com.example.gradebackend.service.impl.SalaryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static com.example.gradebackend.util.Constant.API_SALARY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = API_SALARY, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Salary", description = "The Salary API")
public class SalaryController {

    private final SalaryServiceImpl salaryService;

    @Operation(summary = "Get all salaries",
            description = "Get all salaries.")
    @GetMapping
    public ResponseEntity<Optional<List<Salary>>> getAll() {
        try {
            return new ResponseEntity<>(salaryService.getAll(), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new salary",
            description = "Create new salary, need json body.")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Salary>> createSalary(@RequestBody PostCreateSalary postCreateSalary) {
        try {
            System.out.println(postCreateSalary.toString());
            return new ResponseEntity<>(salaryService.createSalary(postCreateSalary), HttpStatus.OK);
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

    @Operation(summary = "Create position",
            description = "Create position, need json body.")
    @PostMapping(value = "/position", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Position>> createPosition(@RequestBody PostCreatePosition position) {
        try {
            return new ResponseEntity<>(salaryService.createPosition(position), HttpStatus.OK);
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

    @Operation(summary = "Get salary by id",
            description = "Get salary by id employee.")
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<Salary>> getSalaryById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(salaryService.getSalaryById(id), HttpStatus.OK);
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

    @Operation(
            summary = "Update Salary",
            description = "Update Salary, need id for url and employee in json body")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Salary>> updateSalary(@PathVariable Integer id, @RequestBody Salary salary) {
        try {
            return new ResponseEntity<>(salaryService.updateSalary(id, salary), HttpStatus.OK);
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

    @Operation(
            summary = "Delete salary",
            description = "Delete salary, need id.")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteSalary(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(salaryService.deleteSalary(id), HttpStatus.OK);
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
