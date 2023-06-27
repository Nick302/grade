package com.example.gradebackend.controller;

import com.example.gradebackend.model.domain.Status;
import com.example.gradebackend.model.domain.Task;
import com.example.gradebackend.model.dto.request.GetTaskByDate;
import com.example.gradebackend.model.dto.request.PostSetTaskRequestDepartment;
import com.example.gradebackend.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static com.example.gradebackend.util.Constant.API_TASK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = API_TASK, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Task", description = "The Task API")
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = "Getting a complete list of tasks by status",
            description = "Getting a complete list of tasks by status, id and status need in url")
    @GetMapping("/{id}/{status}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<List<Task>>> getAllTaskEmployeeByStatus(@PathVariable Integer id, @PathVariable Status status) {
        try {
            return new ResponseEntity<>(taskService.getAllTaskEmployeeByStatus(id, status), HttpStatus.OK);
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
            summary = "Getting a complete list of tasks by employee",
            description = "Getting a complete list of tasks by employee, need id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<List<Task>>> getAllTaskByEmployee(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(taskService.getAllTaskByEmployee(id), HttpStatus.OK);
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
            summary = "Getting a complete list of tasks by date",
            description = "Getting a complete list of tasks by date, need json body")
    @PostMapping(value = "/date", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<List<Task>>> getAllTaskEmployeeByDate(@RequestBody GetTaskByDate taskByDate) {
        try {
            return new ResponseEntity<>(taskService.getAllTaskEmployeeByDate(taskByDate), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Getting a complete list of tasks by page",
            description = "Getting a complete list of tasks by page, in the URL you can pass information for the sample." +
                    " Example: http://site.com/api/v1/task?page=0&size=10&sort=id,ASC")
    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<List<Task>>> getAllTaskByPage(Pageable pageable) {
        try {
            return new ResponseEntity<>(taskService.getAllTaskByPage(pageable), HttpStatus.OK);
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

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
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
            summary = "Create new task",
            description = "Create new task, need json body.")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<Task>> createTask(@RequestBody Task task) {
        try {
            return new ResponseEntity<>(taskService.createTask(task), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Update task",
            description = "Update task, need id for url and employee in json body")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Task>> updateTask(@PathVariable Integer id, @RequestBody Task task) {
        try {
            return new ResponseEntity<>(taskService.updateTask(id, task), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete task",
            description = "Delete task, need id in url.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity deleteTask(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
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
            summary = "Set task",
            description = "Set task, need json body.")
    @PostMapping(value = "/settask",consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Optional<Task>> setTaskOnDepartment(@RequestBody PostSetTaskRequestDepartment postSetTaskRequest) {
        try {
            return new ResponseEntity<>(taskService.setTaskOnDepartment(postSetTaskRequest), HttpStatus.OK);
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
