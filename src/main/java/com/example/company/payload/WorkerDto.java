package com.example.company.payload;

import com.example.company.entity.Address;
import com.example.company.entity.Department;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {
    @NotNull(message = "name shouldn't be empty")
    private String name;
    @NotNull(message = "phoneNumber shouldn't be empty")
    private String phoneNumber;
    @NotNull(message = "address shouldn't be empty")
    private Integer address;
    @NotNull(message = "department shouldn't be empty")
    private Integer department;
}
