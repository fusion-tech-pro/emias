package com.fusiontech.emias.dto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
public class ParameterDTO {
    private Long id;

    @Size(max = 255)
    @NotNull
    private String name;

    @Size(max = 255)
    @NotNull
    private String type;

    @Size(max = 255)
    @NotNull
    private String description;

    @PositiveOrZero
    private int rank;
}
