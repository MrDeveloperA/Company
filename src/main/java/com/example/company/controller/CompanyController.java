package com.example.company.controller;

import com.example.company.entity.Address;
import com.example.company.entity.Company;
import com.example.company.payload.AddressDto;
import com.example.company.payload.ApiResponse;
import com.example.company.payload.CompanyDto;
import com.example.company.service.AddressService;
import com.example.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    //    Create
    @PostMapping
    public ResponseEntity<ApiResponse> addCompany(@Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(201).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //    Get
    @GetMapping
    public ResponseEntity<List<Company>> getCompanies() {
        List<Company> companies = companyService.getCompanies();
        return ResponseEntity.ok(companies);
    }

    //    Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompaniesById(@PathVariable Integer id) {
        Company companyById = companyService.getCompanyById(id);
        return ResponseEntity.ok(companyById);
    }

    //    Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCompany(@PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //    Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Integer id) {
        ApiResponse apiResponse = companyService.deleteCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = ((FieldError) error).getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}