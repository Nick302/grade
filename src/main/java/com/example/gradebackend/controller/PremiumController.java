package com.example.gradebackend.controller;

import com.example.gradebackend.model.domain.Premium;
import com.example.gradebackend.model.dto.request.PostSetPremiumRequest;
import com.example.gradebackend.model.dto.response.PostSetPremiumResponse;
import com.example.gradebackend.service.impl.PremiumServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static com.example.gradebackend.util.Constant.API_PREMIUM;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = API_PREMIUM, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Premium", description = "The Premium API")
public class PremiumController {

    private final PremiumServiceImpl premiumService;

    @Operation(summary = "Getting a complete list of premiums by page",
            description = "Getting a complete list of premiums by page, " +
                    "in the URL you can pass information for the sample. "
                    + "Example: http://site.com/api/v1/premium?page=0&size=10&sort=id,ASC")
    @GetMapping
    public ResponseEntity<Optional<List<Premium>>> getAllPremiumsByPage(Pageable pageable) {
        try {
            return new ResponseEntity<>(premiumService.getAllPremiumsByPage(pageable), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new premium",
            description = "Create new premium, need json body.")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Premium>> createPremium(@RequestBody Premium premium) {
        try {
            return new ResponseEntity<>(premiumService.createPremium(premium), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete premium",
            description = "Delete premium, need id.")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePremium(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(premiumService.deletePremium(id), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Give premium by departments",
            description = "Give premium by departments, need json body.")
    @PostMapping(value = "/give", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PostSetPremiumResponse> givePremiumByDepartment(@RequestBody PostSetPremiumRequest postSetPremium) {
        try {
            return new ResponseEntity<>(premiumService.givePremiumByDepartment(postSetPremium), HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
