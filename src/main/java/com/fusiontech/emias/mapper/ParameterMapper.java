package com.fusiontech.emias.mapper;

import com.fusiontech.emias.dto.ParameterDTO;
import com.fusiontech.emias.model.Parameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParameterMapper {
    Parameter toEntity(ParameterDTO parameterDTO);
    ParameterDTO toDTO(Parameter parameter);
}
