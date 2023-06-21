package com.fusiontech.emias.dto;

import com.fusiontech.emias.validator.ParameterUniqueConstraint;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ParameterUniqueConstraint
public class RuleDTO {
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String url;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ParameterValueDTO> parameterValues;
}
