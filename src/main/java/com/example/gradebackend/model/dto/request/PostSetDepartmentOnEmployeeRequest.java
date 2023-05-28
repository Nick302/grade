package com.example.gradebackend.model.dto.request;

import lombok.Data;

@Data
public class PostSetDepartmentOnEmployeeRequest {
    private Integer idEmployee;
    private Integer idDepartment;
}
