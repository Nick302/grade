package com.example.gradebackend.model.dto.request;

import lombok.Data;

@Data
public class PostSetTaskRequest {
    private Integer idEmployee;
    private Integer idTask;
}
