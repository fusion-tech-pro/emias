package com.fusiontech.emias.controller;

import com.fusiontech.emias.dto.RuleDTO;
import com.fusiontech.emias.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rule")
public class RuleController {
    private final RuleService ruleService;
    private String logMessage = "Controller: Rule: {}";

    @PostMapping("/")
    public ResponseEntity create(@Validated @RequestBody RuleDTO ruleDTO){
        try {
            log.info("Controller: Creating rule: {}", ruleDTO);
            RuleDTO createdRule = ruleService.create(ruleDTO);
            log.info("Controller: Rule created: {}", createdRule);
            return ResponseEntity.ok().body(createdRule);
        } catch (Exception e) {
            log.info(logMessage, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Validated @RequestBody RuleDTO ruleDTO){
        try {
            log.info("Controller: Updating rule with ID {}: {}", id, ruleDTO);
            ruleDTO.setId(id);
            RuleDTO updatedRule = ruleService.update(ruleDTO);
            log.info("Controller: Rule updated: {}", updatedRule);
            return ResponseEntity.ok().body(updatedRule);
        } catch (Exception e) {
            log.info(logMessage, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    @GetMapping("/")
    public ResponseEntity getAllRules(){
        try {
            log.info("Controller: Getting all rules");
            List<RuleDTO> rules = ruleService.findAll();
            log.info("Controller: Retrieved {} rules", rules.size());
            return ResponseEntity.ok().body(rules);
        } catch (Exception e) {
            log.info(logMessage, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getRuleById(@PathVariable Long id) {
        try {
            log.info("Controller: Getting rule with ID {}", id);
            Optional<RuleDTO> rule = ruleService.findById(id);
            if (rule.isPresent()) {
                log.info("Controller: Rule found: {}", rule.get());
                return ResponseEntity.ok(rule.get());
            } else {
                log.info("Controller: Rule not found with ID {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.info(logMessage, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable Long id) {
        try {
            log.info("Controller: Deleting rule with ID {}", id);
            ruleService.delete(id);
            log.info("Controller: Rule deleted with ID {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.info(logMessage, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
     
    }
}
