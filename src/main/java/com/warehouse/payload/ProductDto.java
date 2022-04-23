package com.warehouse.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductDto {

    @NotNull(message = "name can't be empty")
    private String name;

    @NotNull(message = "category can't be empty")
    private Long categoryId;

    @NotNull(message = "code can't be empty")
    private String code;

    private Long photoId;

    @NotNull(message = "measuremen can't be empty")
    private Long measurementId;
}
