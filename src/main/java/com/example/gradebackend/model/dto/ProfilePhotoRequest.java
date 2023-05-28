package com.example.gradebackend.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePhotoRequest {

    private MultipartFile photo;



    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
