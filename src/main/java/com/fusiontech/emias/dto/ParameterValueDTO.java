package com.fusiontech.emias.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ParameterValueDTO {
    private Long id;

    @NotNull
    private Long parameterId;

    @NotNull
    @Size(max = 255)
    private String value;
}
