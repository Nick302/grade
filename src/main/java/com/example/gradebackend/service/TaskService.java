package com.example.gradebackend.service;

import com.example.gradebackend.model.domain.Status;
import com.example.gradebackend.model.domain.Task;
import com.example.gradebackend.model.dto.request.GetTaskByDate;
import com.example.gradebackend.model.dto.request.PostSetTaskRequestDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<List<Task>> getAllTaskEmployeeByStatus(Integer id, Status status);

    Optional<List<Task>> getAllTaskByEmployee(Integer id);

    Optional<List<Task>> getAllTaskEmployeeByDate(GetTaskByDate taskByDate);

    Optional<List<Task>> getAllTaskByPage(Pageable pageable);

    Optional<Task> createTask(Task task);

    Optional<Task> updateTask(Integer id, Task task);

    boolean turnInTask(Integer id);

    boolean deleteTask(Integer id);

    Optional<Task> getTaskById(Integer id);

    Optional<Task> setTaskOnDepartment(PostSetTaskRequestDepartment postSetTaskRequest);
}
