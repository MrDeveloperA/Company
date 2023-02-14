package com.example.company.service;

import com.example.company.entity.Address;
import com.example.company.entity.Company;
import com.example.company.entity.Department;
import com.example.company.payload.ApiResponse;
import com.example.company.payload.CompanyDto;
import com.example.company.payload.DepartmentDto;
import com.example.company.repository.AddressRepository;
import com.example.company.repository.CompanyRepository;
import com.example.company.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    //      Create
    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean existsByName = departmentRepository.existsByName(departmentDto.getName());
        if (existsByName)
            return new ApiResponse("Such department name already exist", false);

        Department department = new Department();
        department.setName(departmentDto.getName());

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompany());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Such company was not found", false);
        department.setCompany(optionalCompany.get());

        departmentRepository.save(department);
        return new ApiResponse("Saved successfully", true);
    }

    //  Get
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    //    Get by id
    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return null;
        return optionalDepartment.get();
    }

    //    Update
    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Such Department was not found", false);
        Department editDepartment = optionalDepartment.get();
        editDepartment.setName(departmentDto.getName());

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompany());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Such company was not found", false);
        editDepartment.setCompany(optionalCompany.get());

        departmentRepository.save(editDepartment);

        return new ApiResponse("Edited successfully", true);
    }

    //     Delete
    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department was deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
