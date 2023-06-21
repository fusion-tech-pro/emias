package com.fusiontech.emias.validator;

import com.fusiontech.emias.dto.ParameterValueDTO;
import com.fusiontech.emias.dto.RuleDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ParameterUniqueConstraintValidator implements ConstraintValidator<ParameterUniqueConstraint, RuleDTO> {

    @Override
    public boolean isValid(RuleDTO ruleDTO, ConstraintValidatorContext context) {
        if (ruleDTO == null) {
            return true;
        }
        var distinctParameterCount = ruleDTO.getParameterValues().stream()
                .map(ParameterValueDTO::getParameterId).distinct().count();
        return distinctParameterCount == ruleDTO.getParameterValues().size();
    }
}
