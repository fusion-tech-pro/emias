package com.fusiontech.emias.service;


import com.fusiontech.emias.dto.ParameterDTO;
import com.fusiontech.emias.model.Parameter;

import java.util.List;
import java.util.Optional;

public interface ParameterService {
    List<ParameterDTO> findAll();
    Optional<ParameterDTO> findById(Long id);
    Optional<Parameter> findByName(String name);
    ParameterDTO update(ParameterDTO parameterDTO);
    ParameterDTO create(ParameterDTO parameterDTO);
    void delete(Long id);
}
