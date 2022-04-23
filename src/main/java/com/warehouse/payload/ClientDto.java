package com.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    @NotNull(message = "Name can't be empty")
    private String name;

    @NotNull(message = "Phone number can't be empty")
    private String phoneNumber;
}
