package com.example.company.payload;

import com.example.company.entity.Address;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "corpName shouldn't be empty")
    private String corpName;
    @NotNull(message = "directorName shouldn't be empty")
    private String directorName;
    @NotNull(message = "address shouldn't be empty")
    private Integer address;
}
