package com.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    public Result(String message, boolean active) {
        this.message = message;
        this.active = active;
    }

    private String message;

    private boolean active;

    private Object object;

}
