package com.fusiontech.emias.controller;

import com.fusiontech.emias.dto.RuleDTO;
import com.fusiontech.emias.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public RuleDTO getMatchingRule(@Validated @RequestBody String url) {
        return routeService.getRule(url);
    }

}
