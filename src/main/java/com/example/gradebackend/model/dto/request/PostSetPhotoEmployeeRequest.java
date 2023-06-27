package com.example.gradebackend.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class PostSetPhotoEmployeeRequest {
   private Integer id;
   private MultipartFile file;

    public PostSetPhotoEmployeeRequest() {
    }

    public PostSetPhotoEmployeeRequest(Integer id, MultipartFile file) {
        this.id = id;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


}
