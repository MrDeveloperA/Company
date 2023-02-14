package com.example.company.controller;

import com.example.company.entity.Address;
import com.example.company.payload.AddressDto;
import com.example.company.payload.ApiResponse;
import com.example.company.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address1")
public class AddressController {
    @Autowired
    AddressService addressService;

    //    Create
    @PostMapping
    public ApiResponse addAddress(@Valid @RequestBody AddressDto addressDto) {
        return addressService.addAddress(addressDto);
    }

    //    Get
    @GetMapping
    public List<Address> getAddresses() {
        return addressService.getAddresses();
    }

    //    Get by id
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable Integer id) {
        return addressService.getAddressById(id);
    }

    //    Update
    @PutMapping("/{id}")
    public ApiResponse editAddress(@PathVariable Integer id, @Valid @RequestBody AddressDto addressDto) {
        return addressService.editAddress(id, addressDto);
    }

    //    Delete
    @DeleteMapping("/{id}")
    public ApiResponse deleteAddress(@PathVariable Integer id) {
        return addressService.deleteAddress(id);
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