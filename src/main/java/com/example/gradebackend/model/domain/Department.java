package com.example.gradebackend.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "department")
public class Department extends BaseEntity{

    @Column(name = "name_department", nullable = false)
    private String nameDepartment;

    @Column(name = "percent_bugs")
    private Integer percentBugs;

    @OneToMany(mappedBy = "department")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

}
