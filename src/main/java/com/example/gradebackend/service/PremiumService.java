package com.example.gradebackend.service;

import com.example.gradebackend.model.domain.Premium;
import com.example.gradebackend.model.dto.request.PostSetPremiumRequest;
import com.example.gradebackend.model.dto.response.PostSetPremiumResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PremiumService {

    Optional<Premium> createPremium(Premium premium);

    Optional<List<Premium>> getAllPremiumsByPage(Pageable pageable);

    boolean deletePremium(Integer id);

    PostSetPremiumResponse givePremiumByDepartment(PostSetPremiumRequest postSetPremium);
}
