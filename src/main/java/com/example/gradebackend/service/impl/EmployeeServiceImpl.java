package com.example.gradebackend.service.impl;

import com.example.gradebackend.model.domain.Department;
import com.example.gradebackend.model.domain.Employee;
import com.example.gradebackend.model.domain.Salary;
import com.example.gradebackend.model.domain.Task;
import com.example.gradebackend.model.dto.request.PostGetEmployeeByEmailRequest;
import com.example.gradebackend.model.dto.request.PostSetDepartmentOnEmployeeRequest;
import com.example.gradebackend.model.dto.request.PostSetSalaryRequest;
import com.example.gradebackend.model.dto.request.PostSetTaskRequest;
import com.example.gradebackend.repository.DepartmentRepository;
import com.example.gradebackend.repository.EmployeeRepository;
import com.example.gradebackend.repository.SalaryRepository;
import com.example.gradebackend.repository.TaskRepository;
import com.example.gradebackend.service.EmployeeService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Data
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${packageOne}")
    String packageOne;
    @Value("${packageTwo}")
    String packageTwo;

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final SalaryRepository salaryRepository;
    private final DepartmentRepository departmentRepository;

    public void updatePathPhoto(Integer id, String path) {
       Optional<Employee> employee = employeeRepository.findById(id);
       if(employee.isEmpty()) return;

       employee.get().setPathPhoto(path);
       employeeRepository.save(employee.get());
    }

    @Override
    public void upload(String imagePath, InputStream content) throws IOException {
        Path fullImagePath = Path.of(packageOne, imagePath);
        Path fullImagePath2 = Path.of(packageTwo, imagePath);

        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Thread.sleep(500);
            Files.createDirectories(fullImagePath2.getParent());
            Thread.sleep(500);
            Files.write(fullImagePath, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Thread.sleep(500);
            Files.write(fullImagePath2, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> getEmployeeByEmail(@RequestBody PostGetEmployeeByEmailRequest employee) {
        return employeeRepository.findByEmail(employee.getEmail());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Employee>> getAllEmployeesByPage(Pageable pageable) {
        return employeeRepository.findAllBy(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> getById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Employee> createEmployee(Employee employee) {
        return Optional.ofNullable(employeeRepository.save(employee));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteEmployee(Integer id) {
        Optional<Employee> employeeForDelete = employeeRepository.findById(id);

        if (employeeForDelete.isEmpty()) {
            return false;
        }

        employeeRepository.delete(employeeForDelete.get());
        employeeRepository.flush();

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Employee> updateEmployee(Integer id, Employee employee) {
        Optional<Employee> foundEmployee = employeeRepository.findById(id);

        if (foundEmployee.isEmpty()) {
            return Optional.empty();
        }

        employee.setId(id);

        return Optional.ofNullable(employeeRepository.save(employee));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Employee> setTaskOnEmployee(PostSetTaskRequest postSetTaskRequest) {
        int idEmployee = postSetTaskRequest.getIdEmployee();
        int idTask = postSetTaskRequest.getIdTask();

        Optional<Employee> foundEmployee = employeeRepository.findById(idEmployee);
        Optional<Task> foundTask = taskRepository.findById(idTask);

        if (foundEmployee.isEmpty() || foundTask.isEmpty()) {
            return Optional.empty();
        }

        List<Task> tasksOurEmployee = foundEmployee.get().getTask();

        tasksOurEmployee.add(foundTask.get());
        foundEmployee.get().setTask(tasksOurEmployee);

        return Optional.ofNullable(employeeRepository.save(foundEmployee.get()));
    }

    @Override
    public Optional<Employee> setSalaryOnEmployee(PostSetSalaryRequest postSetSalaryRequest) {
        int idEmployee = postSetSalaryRequest.getIdEmployee();
        int idSalary = postSetSalaryRequest.getIdSalary();

        Optional<Employee> foundEmployee = employeeRepository.findById(idEmployee);
        Optional<Salary> foundSalary = salaryRepository.findById(idSalary);

        if (foundEmployee.isEmpty() || foundSalary.isEmpty()) {
            return Optional.empty();
        }

        foundEmployee.get().setSalary(foundSalary.get());
        return Optional.ofNullable(employeeRepository.save(foundEmployee.get()));
    }

    @Override
    public Optional<Employee> setDepartmentOnEmployee(PostSetDepartmentOnEmployeeRequest postSetDepartmentOnEmployee) {
        int idEmployee = postSetDepartmentOnEmployee.getIdEmployee();
        int idDepartment = postSetDepartmentOnEmployee.getIdDepartment();

        Optional<Employee> foundEmployee = employeeRepository.findById(idEmployee);
        Optional<Department> foundDepartment = departmentRepository.findById(idDepartment);

        if (foundEmployee.isEmpty() || foundDepartment.isEmpty()) {
            return Optional.empty();
        }

        foundEmployee.get().setDepartment(foundDepartment.get());
        return Optional.ofNullable(employeeRepository.save(foundEmployee.get()));
    }
}
