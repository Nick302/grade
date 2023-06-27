package com.example.gradebackend.service.impl;

import com.example.gradebackend.model.domain.Department;
import com.example.gradebackend.model.domain.Employee;
import com.example.gradebackend.model.domain.Status;
import com.example.gradebackend.model.domain.Task;
import com.example.gradebackend.model.dto.request.GetTaskByDate;
import com.example.gradebackend.model.dto.request.PostSetTaskRequestDepartment;
import com.example.gradebackend.repository.DepartmentRepository;
import com.example.gradebackend.repository.EmployeeRepository;
import com.example.gradebackend.repository.TaskRepository;
import com.example.gradebackend.service.TaskService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Data
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Task>> getAllTaskEmployeeByStatus(Integer id, Status status) {
        Optional<Employee> foundEmployee = employeeRepository.findById(id);

        if (foundEmployee.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                foundEmployee
                        .get()
                        .getTask()
                        .stream()
                        .filter(s -> s.getStatus().equals(status))
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Task>> getAllTaskByEmployee(Integer id) {
        Optional<Employee> foundEmployee = employeeRepository.findById(id);

        if (foundEmployee.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(foundEmployee.get().getTask());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Task>> getAllTaskEmployeeByDate(GetTaskByDate taskByDate) {
        Optional<Employee> foundEmployee = employeeRepository.findById(taskByDate.getId());

        if (foundEmployee.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                foundEmployee
                        .get()
                        .getTask()
                        .stream()
                        .filter(t -> t.getDateLastDay().isBefore(taskByDate.getDate()))
                        .filter(t -> t.getStatus().equals(Status.COMPLETED))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> setTaskOnDepartment(PostSetTaskRequestDepartment postSetTaskRequest) {
        int idDepartment = postSetTaskRequest.getIdDepartment();
        int idTask = postSetTaskRequest.getIdTask();

        Optional<Department> foundDepartment = departmentRepository.findById(idDepartment);
        Optional<Task> foundTask = taskRepository.findById(idTask);

        if (foundDepartment.isEmpty() || foundTask.isEmpty()) {
            return Optional.empty();
        }
        foundTask.get().setDepartment(foundDepartment.get());
        taskRepository.save(foundTask.get());
        taskRepository.flush();

        return Optional.ofNullable(foundTask.get());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Task>> getAllTaskByPage(Pageable pageable) {
        return taskRepository.findAllBy(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Task> createTask(Task task) {
        return Optional.ofNullable(taskRepository.save(task));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Task> updateTask(Integer id, Task task) {
        Optional<Task> foundTask = taskRepository.findById(id);

        if (foundTask.isEmpty()) {
            return Optional.empty();
        }

        task.setId(id);

        return Optional.ofNullable(taskRepository.save(task));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean turnInTask(Integer id) {
        //TODO: Написать реализацию для сдачи задачи
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteTask(Integer id) {
        Optional<Task> taskForDelete = taskRepository.findById(id);

        if (taskForDelete.isEmpty()) {
            return false;
        }

        taskRepository.delete(taskForDelete.get());
        taskRepository.flush();

        return true;
    }
}
