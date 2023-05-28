package com.example.gradebackend.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String email;
    private String username;
    private String password;
}
