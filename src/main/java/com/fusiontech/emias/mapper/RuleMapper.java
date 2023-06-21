package com.fusiontech.emias.mapper;

import com.fusiontech.emias.dto.RuleDTO;
import com.fusiontech.emias.model.Rule;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = ParameterValueMapper.class)
public interface RuleMapper {
    Rule toEntity(RuleDTO ruleDTO);

    RuleDTO toDTO(Rule rule);

    List<RuleDTO> toDTOList(List<Rule> rules);

    @AfterMapping
    default void afterMapping(RuleDTO ruleDTO, @MappingTarget Rule rule) {
        rule.getParameterValues().forEach(parameterValue -> parameterValue.setRule(rule));
    }
}
