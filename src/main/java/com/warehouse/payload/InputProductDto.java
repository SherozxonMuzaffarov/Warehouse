package com.warehouse.payload;

import com.warehouse.entity.Input;
import com.warehouse.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputProductDto {

    @NotNull(message = "amount can't be empty")
    private Double amount;

    @NotNull(message = "price can't be empty")
    private Double price;

    @NotNull(message = "expireDate can't be empty")
    private Date expireDate;

    @NotNull(message = "productId can't be empty")
    private Long productId;

    @NotNull(message = "inputId can't be empty")
    private Long inputId;

}
