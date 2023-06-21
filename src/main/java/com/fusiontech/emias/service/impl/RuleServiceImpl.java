package com.fusiontech.emias.service.impl;

import com.fusiontech.emias.dto.ParameterValueDTO;
import com.fusiontech.emias.dto.RuleDTO;
import com.fusiontech.emias.exception.DuplicateException;
import com.fusiontech.emias.exception.RouteException;
import com.fusiontech.emias.mapper.ParameterMapper;
import com.fusiontech.emias.exception.EntityNotFoundException;
import com.fusiontech.emias.mapper.RuleMapper;
import com.fusiontech.emias.model.Parameter;
import com.fusiontech.emias.model.ParameterValue;
import com.fusiontech.emias.model.Rule;
import com.fusiontech.emias.repository.RuleRepository;
import com.fusiontech.emias.service.ParameterService;
import com.fusiontech.emias.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {
    private final RuleRepository ruleRepository;
    private final RuleMapper ruleMapper;
    private final ParameterService parameterService;
    private final ParameterMapper parameterMapper;
    @Override
    public List<RuleDTO> findAll() {
        log.info("Service: Finding all rules");
        List<Rule> rules = ruleRepository.findAll();
        List<RuleDTO> ruleDTOs = rules.stream().map(ruleMapper::toDTO).collect(Collectors.toList());
        log.info("Service: Retrieved {} rules", ruleDTOs.size());
        return ruleDTOs;
    }

    @Override
    public RuleDTO create(RuleDTO ruleDTO) {
        log.info("Service: Creating rule: {}", ruleDTO);
        if(ruleRepository.findByName(ruleDTO.getName()).isPresent()){
            throw new DuplicateException("Rule with the same name already exists");
        }
        List<ParameterValueDTO> parameters = ruleDTO.getParameterValues();

        SortedMap<Parameter, String> list = new TreeMap<>();
        for (int i = 0; i < parameters.size(); i++) {
             Parameter data = parameterMapper.toEntity(parameterService.findById(parameters.get(i).getParameterId()).get());
            list.put(data, parameters.get(i).getValue());
        }
        List<RuleDTO> existingRules = getRulesByParameters(list);
        if (existingRules.size() > 1) {
            throw new RouteException("Such rule already exist");
        }
        Rule rule = ruleMapper.toEntity(ruleDTO);
        rule = ruleRepository.save(rule);

        RuleDTO createdRuleDTO = ruleMapper.toDTO(rule);
        log.info("Service: Rule created: {}", createdRuleDTO);
        return createdRuleDTO;
    }

    @Override
    public RuleDTO update(RuleDTO ruleDTO) {
        if (!ruleRepository.existsById(ruleDTO.getId())) {
            throw new EntityNotFoundException("Rule not found");
        }
        var ruleDuplicate = ruleRepository.findByName(ruleDTO.getName());
        if(ruleDuplicate.isPresent() && !ruleDuplicate.get().getId().equals(ruleDTO.getId())){
            throw new DuplicateException("Rule with the same name already exists");
        }
        log.info("Service: Updating rule: {}", ruleDTO);
        Optional <Rule> rule = ruleRepository.findById(ruleDTO.getId());
        if (rule.isPresent()) {
            Rule newRule = ruleMapper.toEntity(ruleDTO);
            newRule = ruleRepository.save(newRule);
            RuleDTO updatedRuleDTO = ruleMapper.toDTO(newRule);
            log.info("Service: Rule updated: {}", updatedRuleDTO);
            return updatedRuleDTO;
        } else {
            throw new RouteException("Can not update rule");
        }
    }

    @Override
    public Optional<RuleDTO> findById(Long id) {
        log.info("Service: Finding rule by ID: {}", id);
        Optional<Rule> rule = ruleRepository.findById(id);
        Optional<RuleDTO> ruleDTO = rule.map(ruleMapper::toDTO);
        if (ruleDTO.isPresent()) {
            log.info("Service: Rule found: {}", ruleDTO.get());
        } else {
            log.info("Service: Rule not found with ID: {}", id);
        }
        return ruleDTO;
    }

    @Override
    public void delete(Long id) {
        if (!ruleRepository.existsById(id)) {
            throw new EntityNotFoundException("Rule not found");
        }
        log.info("Service: Deleting rule with ID: {}", id);
        ruleRepository.deleteById(id);
        log.info("Service: Rule deleted with ID: {}", id);
    }

    // TODO change find all to find necessary
    @Override
    public List<RuleDTO> getRulesByParameters(SortedMap<Parameter, String> parameters) {
        List<Rule> rules = ruleRepository.findAll();
        List<Rule> matchedRules = filterRulesByExactMatch(rules, parameters);
        return ruleMapper.toDTOList(matchedRules);
    }

    private List<Rule> filterRulesByExactMatch(List<Rule> rules, SortedMap<Parameter, String> parameters) {
        var paramNames = parameters.keySet().stream().map(Parameter::getName).collect(Collectors.toList());
        return rules.stream()
                .filter(rule -> rule.getParameterValues().size() == paramNames.size())
                .filter(rule -> {
                    var ruleParams = rule.getParameterValues().stream()
                            .map(ParameterValue::getParameter)
                            .map(Parameter::getName)
                            .collect(Collectors.toList());
                    for (var name: ruleParams) {
                        if (!paramNames.contains(name)){
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}