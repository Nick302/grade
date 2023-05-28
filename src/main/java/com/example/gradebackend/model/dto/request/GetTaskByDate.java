package com.example.gradebackend.model.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetTaskByDate {
   private Integer id;
   private LocalDate date;
}
