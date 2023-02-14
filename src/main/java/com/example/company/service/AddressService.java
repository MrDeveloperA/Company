package com.example.company.service;

import com.example.company.entity.Address;
import com.example.company.payload.AddressDto;
import com.example.company.payload.ApiResponse;
import com.example.company.repository.AddressRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    //      Create
    public ApiResponse addAddress(AddressDto addressDto) {
        boolean existsByStreetAndNumber = addressRepository.existsByStreetAndNumber(addressDto.getStreet(), addressDto.getNumber());
        if (existsByStreetAndNumber)
            return new ApiResponse("Such homeNumber with such Street already exist", false);

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setNumber(addressDto.getNumber());

        addressRepository.save(address);
        return new ApiResponse("Saved successfully", true);
    }

    //  Get
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    //    Get Address by id
    public Address getAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return null;
        return optionalAddress.get();
    }

    //    Update
    public ApiResponse editAddress(Integer id, AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("Such Address was not found", false);
        Address editAddress = optionalAddress.get();
        editAddress.setStreet(addressDto.getStreet());
        editAddress.setNumber(addressDto.getNumber());

        addressRepository.save(editAddress);

        return new ApiResponse("Edited successfully", true);
    }

    //     Delete
    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address was deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
