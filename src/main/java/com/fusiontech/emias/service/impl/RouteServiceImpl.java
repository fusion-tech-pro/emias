package com.fusiontech.emias.service.impl;

import com.fusiontech.emias.dto.RuleDTO;
import com.fusiontech.emias.exception.RouteException;
import com.fusiontech.emias.model.Parameter;
import com.fusiontech.emias.service.ParameterService;
import com.fusiontech.emias.service.RouteService;
import com.fusiontech.emias.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    private final ParameterService parameterService;
    private final RuleService ruleService;

    @Override
    public RuleDTO getRule(String url) {
        SortedMap<Parameter, String> parameters = parseParametersFromUrl(url);
        if (validateParameters(parameters)) {
            List<RuleDTO> matchedRules = ruleService.getRulesByParameters(parameters);
            for (Map.Entry<Parameter, String> entry : parameters.entrySet()) {
                var parameter = entry.getKey();
                var value = entry.getValue();
                matchedRules.removeIf(rule -> {
                    var param = rule.getParameterValues()
                            .stream().filter(paramValue -> paramValue.getParameterId().equals(parameter.getId()))
                            .findFirst().orElseThrow();
                    if (param.getValue().contains(value) || param.getValue().equals("all")) {
                        return false;
                    } else {
                        return true;
                    }
                });
            }
            if (!matchedRules.isEmpty()) {
                if (matchedRules.size() > 1) {
                    throw new RouteException("Too many rules found");
                }
                return matchedRules.get(0);
            } else {
                throw new RouteException("Rule not found");
            }
        } else {
            throw new RouteException("Invalid parameters");
        }
    }


    private SortedMap<Parameter, String> parseParametersFromUrl(String url) {
        SortedMap<Parameter, String> parameters = new TreeMap<>();
        try {
            URI uri = new URI(url);
            String query = uri.getQuery();

            if (query != null) {
                String[] keyValuePairs = query.split("&");

                for (String keyValuePair : keyValuePairs) {
                    String[] parts = keyValuePair.split("=");
                    String name = parts[0];
                    var parameter = parameterService.findByName(name);
                    if (parameter.isEmpty()) {
                        log.warn("Parameter {} not found", name);
                        continue;
                    }
                    String value = parts.length > 1 ? parts[1] : "";
                    if (value.isEmpty()) {
                        log.warn("Parameter {} has no value", name);
                    }
                    parameters.put(parameter.get(), value);
                }
            }
        } catch (URISyntaxException e) {
            log.error("URL is not correct: {}", url, e);
        }
        return parameters;
    }


    // TODO fix validation logic
    private boolean validateParameters(Map<Parameter, String> parameters) {
        for (Map.Entry<Parameter, String> entry : parameters.entrySet()) {
            var parameter = entry.getKey();
            if (!isValidString(parameter.getName()) ||
                    !isValidString(parameter.getType()) ||
                    !isValidString(parameter.getDescription()) ||
                    !isValidInt(parameter.getRank())) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidString(String value) {
        return value != null && !value.isEmpty();
    }

    private boolean isValidInt(int value) {
        return value > 0;
    }
}
