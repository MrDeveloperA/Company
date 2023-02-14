package com.example.company.controller;

import com.example.company.entity.Company;
import com.example.company.entity.Department;
import com.example.company.payload.ApiResponse;
import com.example.company.payload.CompanyDto;
import com.example.company.payload.DepartmentDto;
import com.example.company.service.CompanyService;
import com.example.company.service.DepartmentService;
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
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    //    Create
    @PostMapping
    public ResponseEntity<ApiResponse> addDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(201).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //    Get
    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }

    //    Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentsById(@PathVariable Integer id) {
        Department departmentById = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentById);
    }

    //    Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //    Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Integer id) {
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
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