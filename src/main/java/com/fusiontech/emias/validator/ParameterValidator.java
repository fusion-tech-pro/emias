package com.fusiontech.emias.validator;

import com.fusiontech.emias.exception.EntityNotFoundException;
import com.fusiontech.emias.model.Parameter;
import com.fusiontech.emias.repository.ParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParameterValidator {
    private final ParameterRepository parameterRepository;

    public Parameter validate(Long parameterId) {
        return parameterRepository.findById(parameterId)
                .orElseThrow(() -> new EntityNotFoundException("Parameter not found"));
    }
}
