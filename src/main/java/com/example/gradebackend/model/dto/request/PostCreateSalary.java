package com.example.gradebackend.model.dto.request;

import lombok.Data;

@Data
public class PostCreateSalary {
    private double amount;
    private Integer idPosition;
}
