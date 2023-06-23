package com.example.gradebackend.controller;

import com.example.gradebackend.model.dto.UserDTO;
import com.example.gradebackend.util.KeycloakUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.gradebackend.util.Constant.*;

@Slf4j
@RestController
@RequestMapping(API_AUTH)
@RequiredArgsConstructor
@Tag(name = "Department", description = "The Department API")
public class AuthController {

    private final KeycloakUtils keycloakUtils;

    @PostMapping
    public ResponseEntity createUserKC(@RequestBody UserDTO userDTO)
    {
        var resp = keycloakUtils.createKeycloakUser(userDTO);
        var userId = CreatedResponseUtil.getCreatedId(resp);
        List<String> defaultRoles = new ArrayList<>();

        if(CONFLICT == resp.getStatus()) {
            return new ResponseEntity("user email already exists" + userDTO.getEmail(), HttpStatus.CONFLICT);
        }

        defaultRoles.add(USER_ROLE_NAME);
        keycloakUtils.addRoles(userId, defaultRoles);

        return ResponseEntity.status(resp.getStatus()).build();
    }



}
