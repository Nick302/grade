package com.example.gradebackend.service.impl;

import com.example.gradebackend.model.domain.Department;
import com.example.gradebackend.model.domain.Employee;
import com.example.gradebackend.model.domain.Premium;
import com.example.gradebackend.model.dto.request.PostSetPremiumRequest;
import com.example.gradebackend.model.dto.response.PostSetPremiumResponse;
import com.example.gradebackend.repository.DepartmentRepository;
import com.example.gradebackend.repository.EmployeeRepository;
import com.example.gradebackend.repository.PremiumRepository;
import com.example.gradebackend.service.PremiumService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Service
@Transactional
public class PremiumServiceImpl implements PremiumService {

    private final PremiumRepository premiumRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Premium>> getAllPremiumsByPage(Pageable pageable) {
        return premiumRepository.findAllBy(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Premium> createPremium(Premium premium) {
        return Optional.ofNullable(premiumRepository.save(premium));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deletePremium(Integer id) {
        Optional<Premium> premiumForDelete = premiumRepository.findById(id);

        if (premiumForDelete.isEmpty()) {
            return false;
        }

        premiumRepository.delete(premiumForDelete.get());
        premiumRepository.flush();

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostSetPremiumResponse givePremiumByDepartment(PostSetPremiumRequest postSetPremium) {
        Optional<Department> foundDepartment = departmentRepository.findById(postSetPremium.getIdDepartment());

        if(foundDepartment.isEmpty()) {
            new PostSetPremiumResponse("Department with id " + postSetPremium.getIdDepartment() + " doesnt exist!");
        }

        List<Employee> allEmployeesByDepartment = foundDepartment.get().getEmployees();
        Optional<Premium> foundPremium = premiumRepository.findById(postSetPremium.getIdPremium());

        if(foundPremium.isEmpty()) {
            new PostSetPremiumResponse("Premium with id " + postSetPremium.getIdPremium() + " doesnt exist!");
        }

        for(Employee employee : allEmployeesByDepartment) {
            List<Premium> employeePremiums = employee.getPremium();

            employeePremiums.add(foundPremium.get());
            employee.setPremium(employeePremiums);
        }

        employeeRepository.saveAll(allEmployeesByDepartment);

        return new PostSetPremiumResponse(
                "All employees of the department "
                + foundDepartment.get().getNameDepartment()
                + " was given a bonus!"
        );
    }
}
