package com.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputProductDto {

    @NotNull(message = "amount can't be empty")
    private Double amount;

    @NotNull(message = "price can't be empty")
    private Double price;

    @NotNull(message = "productID can't be empty")
    private Long productID;

    @NotNull(message = "outputID can't be empty")
    private Long outputID;
}
