package com.fusiontech.emias.service.impl;

import com.fusiontech.emias.dto.ParameterDTO;
import com.fusiontech.emias.exception.DuplicateException;
import com.fusiontech.emias.exception.EntityNotFoundException;
import com.fusiontech.emias.mapper.ParameterMapper;
import com.fusiontech.emias.model.Parameter;
import com.fusiontech.emias.repository.ParameterRepository;
import com.fusiontech.emias.service.ParameterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;
    private final ParameterMapper parameterMapper;

    @Override
    public List<ParameterDTO> findAll() {
        log.info("Service: Finding all parameters");
        List<Parameter> parameters = parameterRepository.findAll();
        List<ParameterDTO> parameterDTOs = parameters.stream().map(parameterMapper::toDTO).collect(Collectors.toList());
        log.info("Service: Retrieved {} parameters", parameterDTOs.size());
        return parameterDTOs;
    }

    @Override
    public ParameterDTO create(ParameterDTO parameterDTO) {
        log.info("Service: Creating parameter: {}", parameterDTO);
        if(parameterRepository.findByName(parameterDTO.getName()).isPresent()){
            throw new DuplicateException("Parameter with the same name already exists");
        }
        Parameter parameter = parameterMapper.toEntity(parameterDTO);
        parameter = parameterRepository.save(parameter);
        ParameterDTO createdParameterDTO = parameterMapper.toDTO(parameter);
        log.info("Service: Parameter created: {}", createdParameterDTO);
        return createdParameterDTO;
    }

    @Override
    public ParameterDTO update(ParameterDTO parameterDTO) {
        if (!parameterRepository.existsById(parameterDTO.getId())) {
            throw new EntityNotFoundException("Parameter not found");
        }
        var parameterDuplicate = parameterRepository.findByName(parameterDTO.getName());
        if(parameterDuplicate.isPresent() && !parameterDuplicate.get().getId().equals(parameterDTO.getId())){
            throw new DuplicateException("Parameter with the same name already exists");
        }
        log.info("Service: Updating parameter: {}", parameterDTO);
        Parameter parameter = parameterMapper.toEntity(parameterDTO);
        parameter = parameterRepository.save(parameter);
        ParameterDTO updatedParameterDTO = parameterMapper.toDTO(parameter);
        log.info("Service: Parameter updated: {}", updatedParameterDTO);
        return updatedParameterDTO;
    }

    @Override
    public Optional<ParameterDTO> findById(Long id) {
        log.info("Service: Finding parameter by ID: {}", id);
        Optional<Parameter> parameter = parameterRepository.findById(id);
        Optional<ParameterDTO> parameterDTO = parameter.map(parameterMapper::toDTO);
        if (parameterDTO.isPresent()) {
            log.info("Service: Parameter found: {}", parameterDTO.get());
        } else {
            log.info("Service: Parameter not found with ID: {}", id);
        }
        return parameterDTO;
    }

    @Override
    public Optional<Parameter> findByName(String name) {
        return parameterRepository.findByName(name);
    }

    @Override
    public void delete(Long id) {
        if (!parameterRepository.existsById(id)) {
            throw new EntityNotFoundException("Parameter not found");
        }
        log.info("Service: Deleting parameter with ID: {}", id);
        parameterRepository.deleteById(id);
        log.info("Service: Parameter deleted with ID: {}", id);
    }
}

