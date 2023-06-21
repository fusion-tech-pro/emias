package com.fusiontech.emias.mapper;

import com.fusiontech.emias.dto.ParameterValueDTO;
import com.fusiontech.emias.model.ParameterValue;
import com.fusiontech.emias.validator.ParameterValidator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ParameterValidator.class)
public interface ParameterValueMapper {
    @Mapping(target = "parameter", source = "parameterId")
    ParameterValue toEntity(ParameterValueDTO parameterValueDTO);

    @Mapping(target = "parameterId", source = "parameter.id")
    ParameterValueDTO toDTO(ParameterValue parameterValue);
}
