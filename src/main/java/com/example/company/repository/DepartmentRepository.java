package com.example.company.repository;

import com.example.company.entity.Company;
import com.example.company.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByName(String name);
}
