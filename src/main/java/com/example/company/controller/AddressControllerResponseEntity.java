package com.example.company.controller;

import com.example.company.entity.Address;
import com.example.company.payload.AddressDto;
import com.example.company.payload.ApiResponse;
import com.example.company.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressControllerResponseEntity {
    @Autowired
    AddressService addressService;

    //    Create
    @PostMapping
    public ResponseEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(201).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //    Get
    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addresses = addressService.getAddresses();
        return ResponseEntity.ok(addresses);
    }

    //    Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Address addressById = addressService.getAddressById(id);
        return ResponseEntity.ok(addressById);
    }

    //    Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editAddress(@PathVariable Integer id, @Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //    Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id) {
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return  ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = ((FieldError) error).getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}