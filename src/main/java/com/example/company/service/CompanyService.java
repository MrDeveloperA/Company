package com.example.company.service;

import com.example.company.entity.Address;
import com.example.company.entity.Company;
import com.example.company.payload.AddressDto;
import com.example.company.payload.ApiResponse;
import com.example.company.payload.CompanyDto;
import com.example.company.repository.AddressRepository;
import com.example.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    //      Create
    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean existsByCorpName = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (existsByCorpName)
            return new ApiResponse("Such corporation name already exist", false);

        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddress());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Such address was not found", false);
        company.setAddress(optionalAddress.get());

        companyRepository.save(company);
        return new ApiResponse("Saved successfully", true);
    }

    //  Get
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    //    Get Address by id
    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return null;
        return optionalCompany.get();
    }

    //    Update
    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Such Company was not found", false);
        Company editCompany = optionalCompany.get();
        editCompany.setCorpName(companyDto.getCorpName());
        editCompany.setDirectorName(companyDto.getDirectorName());

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddress());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Such address was not found", false);
        editCompany.setAddress(optionalAddress.get());

        companyRepository.save(editCompany);

        return new ApiResponse("Edited successfully", true);
    }

    //     Delete
    public ApiResponse deleteCompany(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company was deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
