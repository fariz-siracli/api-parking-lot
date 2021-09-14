package com.fs.api.parking.lot.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Exception response")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    @ApiModelProperty("Error code")
    private String code;

    @ApiModelProperty("Error message")
    private String message;
}
