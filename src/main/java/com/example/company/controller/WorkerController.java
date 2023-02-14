package com.example.company.controller;

import com.example.company.entity.Department;
import com.example.company.entity.Worker;
import com.example.company.payload.ApiResponse;
import com.example.company.payload.DepartmentDto;
import com.example.company.payload.WorkerDto;
import com.example.company.service.DepartmentService;
import com.example.company.service.WorkerService;
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
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    //    Create
    @PostMapping
    public ResponseEntity<ApiResponse> addWorker(@Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(201).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //    Get
    @GetMapping
    public ResponseEntity<List<Worker>> getWorkers() {
        List<Worker> workers = workerService.getWorkers();
        return ResponseEntity.ok(workers);
    }

    //    Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorkersById(@PathVariable Integer id) {
        Worker workerById = workerService.getWorkerById(id);
        return ResponseEntity.ok(workerById);
    }

    //    Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.editWorker(id, workerDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //    Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.deleteWorker(id);
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