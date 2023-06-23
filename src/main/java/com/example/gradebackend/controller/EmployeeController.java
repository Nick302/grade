package com.example.gradebackend.controller;

import com.example.gradebackend.model.domain.Employee;
import com.example.gradebackend.model.dto.ProfilePhotoRequest;
import com.example.gradebackend.model.dto.request.*;
import com.example.gradebackend.service.impl.EmployeeServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.gradebackend.util.Constant.API_EMPLOYEE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = API_EMPLOYEE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Employee", description = "The Employee API")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @Operation(summary = "Post Upload image",
            description = "Post Upload image")
    @PostMapping("/photo/{id}")
    public void uploadProfilePhoto(@ModelAttribute ProfilePhotoRequest request, @PathVariable Integer id) throws IOException {
        MultipartFile photo = request.getPhoto();
        employeeService.upload(request.getPhoto().getOriginalFilename(), request.getPhoto().getInputStream());
        employeeService.updatePathPhoto(id, request.getPhoto().getOriginalFilename());
    }

    @Operation(summary = "Get by email",
            description = "Get by email")
    @PostMapping("/email")
    public ResponseEntity<Optional<Employee>> getEmployeeByEmail(@RequestBody PostGetEmployeeByEmailRequest employee) {
        try {
            return new ResponseEntity<>(employeeService.getEmployeeByEmail(employee), HttpStatus.OK);
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

    @Operation(summary = "Getting a complete list of employees by page",
            description = "Getting a complete list of employees by page, " +
                    "in the URL you can pass information for the sample. "
                    + "Example: http://site.com/api/v1/employee?page=0&size=10&sort=id,ASC")
    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<List<Employee>>> getAllEmployeesByPage(Pageable pageable) {
        try {
            return new ResponseEntity<>(employeeService.getAllEmployeesByPage(pageable), HttpStatus.OK);
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

    @Operation(summary = "Getting the employee",
            description = "Getting the employee by id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<Employee>> getById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK);
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

    @Operation(summary = "Create new employee",
            description = "Create new employee, need json body.")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Employee>> createEmployee(@RequestBody Employee employee) {
        try {
            return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.OK);
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
            summary = "Delete employee",
            description = "Delete employee, need id.")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
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
            summary = "Update employee",
            description = "Update employee, need id for url and employee in json body")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Employee>> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        try {
            return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
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

    @Operation(summary = "Set task on employee",
            description = "Set task on employee, need json body.")
    @PostMapping(value = "/settask",consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Employee>> setTaskOnEmployee(@RequestBody PostSetTaskRequest postSetTaskRequest) {
        try {
            return new ResponseEntity<>(employeeService.setTaskOnEmployee(postSetTaskRequest), HttpStatus.OK);
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

    @Operation(summary = "Set salary on employee",
            description = "Set salary on employee, need json body.")
    @PostMapping(value = "/setsalary", consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<Employee>> setSalaryOnEmployee(@RequestBody PostSetSalaryRequest postSetSalaryRequest) {
        try {
            System.out.println(postSetSalaryRequest);
            return new ResponseEntity<>(employeeService.setSalaryOnEmployee(postSetSalaryRequest), HttpStatus.OK);
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

    @Operation(summary = "Set department on employee",
            description = "Set salary on employee, need json body.")
    @PostMapping(value = "/setdep")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<Employee>> setDepartmentOnEmployee(@RequestBody PostSetDepartmentOnEmployeeRequest postSetDepartmentOnEmployee) {
        try {
            return new ResponseEntity<>(employeeService.setDepartmentOnEmployee(postSetDepartmentOnEmployee), HttpStatus.OK);
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

}
