package com.example.gradebackend.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "report")
public class Report extends BaseEntity {

    @OneToMany(fetch = FetchType.EAGER)
    private List<Department> departmentList;

}
