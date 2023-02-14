package com.example.company.payload;

import com.example.company.entity.Company;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    @NotNull(message = "name shouldn't be empty")
    private String name;
    @NotNull(message = "company shouldn't be empty")
    private Integer company;
}
