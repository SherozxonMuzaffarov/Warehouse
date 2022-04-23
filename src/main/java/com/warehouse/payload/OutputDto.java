package com.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputDto {

    @NotNull(message = "wareHouseId can't be empty")
    private Timestamp timestamp;

    @NotNull(message = "factureNumber can't be empty")
    private  String factureNumberId;

    @NotNull(message = "code can't be empty")
    private String code;

    @NotNull(message = "wareHouseId can't be empty")
    private Long wareHouseId;

    @NotNull(message = "clientId can't be empty")
    private Long clientId;

    @NotNull(message = "currency can't be empty")
    private Long currencyID;

}
