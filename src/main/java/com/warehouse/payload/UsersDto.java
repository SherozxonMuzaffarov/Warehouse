package com.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    @NotNull(message = "firstName can't be empty")
    private String firstName;

    private  String lastName;

    @NotNull(message = "phoneNumber can't be empty")
    private String phoneNumber;

    @NotNull(message = "code can't be empty")
    private String code;

    @NotNull(message = "password can't be empty")
    private String password;

    @NotNull(message = "warehouses can't be empty")
    private List<Integer> warehousesId;
}
