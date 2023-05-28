package com.example.gradebackend.model.dto.request;

import lombok.Data;

@Data
public class PostSetSalaryRequest {
    private Integer idEmployee;
    private Integer idSalary;
}
