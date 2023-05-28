package com.example.gradebackend.controller;

import com.example.gradebackend.model.domain.Department;
import com.example.gradebackend.model.domain.Employee;
import com.example.gradebackend.service.impl.DepartmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.gradebackend.util.Constant.API_DEPARTMENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = API_DEPARTMENT, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Department", description = "The Department API")
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    @Operation(
            summary = "Getting a complete list of departments by page",
            description = "Getting a complete list of departments by page, in the URL you can pass information for the sample." +
                    " Example: http://site.com/api/v1/department?page=0&size=10&sort=percentBugs,ASC")
    @GetMapping
    public ResponseEntity<Optional<List<Department>>> getAllDepartmentsByPage(Pageable pageable) {
        try {
            return new ResponseEntity<>(departmentService.getAllDepartmentsByPage(pageable), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }/* catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }*/ catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Getting the Department",
            description = "Getting the Department by id.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Department>> getDepartmentById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }/* catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }*/ catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Create new department",
            description = "Create new department, need json body.")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Department>> createDepartment(@RequestBody Department department) {
        try {
            return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }/* catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }*/ catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Update department",
            description = "Update department, need id for url and department in json body")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Department>> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        try {
            return new ResponseEntity<>(departmentService.updateDepartment(id, department), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete department",
            description = "Delete department, need id.")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartmentById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(departmentService.deleteDepartmentById(id), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }/* catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }*/ catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
