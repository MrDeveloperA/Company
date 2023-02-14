package com.example.company.repository;

import com.example.company.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
boolean existsByStreetAndNumber(String street, Integer number);
}
