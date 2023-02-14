package com.example.company.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotNull(message = "street shouldn't be empty")
    private String street;
    @NotNull(message = "house number shouldn't be empty")
    private Integer number;
}
