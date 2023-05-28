package com.example.gradebackend.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "premium")
public class Premium extends BaseEntity {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_premium",
            joinColumns = @JoinColumn(name = "premium_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private List<Employee> employee = new ArrayList<>();

    @Column(name = "amount_award", nullable = false)
    private Integer amountAward;

}
