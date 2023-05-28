package com.example.gradebackend.model.dto.response;

import lombok.Data;

@Data
public class PostSetPremiumResponse {

   private String message;

   public PostSetPremiumResponse(String message) {
      this.message = message;
   }
}
