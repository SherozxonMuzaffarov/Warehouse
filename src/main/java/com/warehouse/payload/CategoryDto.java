package com.warehouse.payload;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class CategoryDto {


    private String name;
    private Long parentCategoryId;

}
