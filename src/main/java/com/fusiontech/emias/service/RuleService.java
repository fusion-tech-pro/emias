package com.fusiontech.emias.service;

import com.fusiontech.emias.dto.RuleDTO;
import com.fusiontech.emias.model.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.SortedMap;

public interface RuleService {
    List<RuleDTO> findAll();
    Optional<RuleDTO> findById(Long id);

    RuleDTO update(RuleDTO ruleDTO);
    RuleDTO create(RuleDTO ruleDTO);
    void delete(Long id);

    List<RuleDTO> getRulesByParameters(SortedMap<Parameter, String> parameters);
}
