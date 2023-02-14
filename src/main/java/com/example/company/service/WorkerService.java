package com.example.company.service;

import com.example.company.entity.Address;
import com.example.company.entity.Company;
import com.example.company.entity.Department;
import com.example.company.entity.Worker;
import com.example.company.payload.ApiResponse;
import com.example.company.payload.DepartmentDto;
import com.example.company.payload.WorkerDto;
import com.example.company.repository.AddressRepository;
import com.example.company.repository.CompanyRepository;
import com.example.company.repository.DepartmentRepository;
import com.example.company.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    //      Create
    public ApiResponse addWorker(WorkerDto workerDto) {
        boolean existsByName = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByName)
            return new ApiResponse("Such worker with this phone number already exist", false);

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());


        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddress());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Such address was not found", false);
        worker.setAddress(optionalAddress.get());

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartment());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Such department was not found", false);
        worker.setDepartment(optionalDepartment.get());

        workerRepository.save(worker);
        return new ApiResponse("Saved successfully", true);
    }

    //  Get
    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }

    //    Get by id
    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return null;
        return optionalWorker.get();
    }

    //    Update
    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("Such Department was not found", false);

        boolean existsByPhoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot)
            return new ApiResponse("Such worker with this phone number already exist", false);


        Worker editWorker = optionalWorker.get();
        editWorker.setName(workerDto.getName());
        editWorker.setPhoneNumber(workerDto.getPhoneNumber());

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddress());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Such address was not found", false);
        editWorker.setAddress(optionalAddress.get());

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartment());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Such department was not found", false);
        editWorker.setDepartment(optionalDepartment.get());

        workerRepository.save(editWorker);

        return new ApiResponse("Edited successfully", true);
    }

    //     Delete
    public ApiResponse deleteWorker(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker was deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
