package com.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {

    @NotNull(message = "name can't be empty")
    private String name;

    @NotNull(message = "phoneNumber can't be empty")
    private String phoneNumber;
}
