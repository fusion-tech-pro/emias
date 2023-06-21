package com.fusiontech.emias.controller;

import com.fusiontech.emias.dto.ParameterDTO;
import com.fusiontech.emias.service.ParameterService;
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
@RequestMapping("/parameter")
public class ParameterController {
  private final ParameterService parameterService;
  private String logMessage = "Controller: Parameter get: {}";

  @PostMapping("/")
  public ResponseEntity create(@Validated @RequestBody ParameterDTO parameterDTO) {
      try {
          log.info("Controller: Creating parameter: {}", parameterDTO);
          ParameterDTO createdParameter = parameterService.create(parameterDTO);
          log.info("Controller: Parameter created: {}", createdParameter);
          return ResponseEntity.ok().body(createdParameter);
      } catch (Exception e) {
          log.info(logMessage, e.getMessage());
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@PathVariable Long id, @Validated @RequestBody ParameterDTO parameterDTO) {
      try {
          log.info("Controller: Updating parameter with ID {}: {}", id, parameterDTO);
          parameterDTO.setId(id);
          ParameterDTO updatedParameter = parameterService.update(parameterDTO);
          log.info("Controller: Parameter updated: {}", updatedParameter);
          return ResponseEntity.ok().body(updatedParameter);
      } catch (Exception e) {
          log.info(logMessage, e.getMessage());
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @GetMapping("/")
  public ResponseEntity getAllParameters() {
      try {
          log.info("Controller: Getting all parameters");
          List<ParameterDTO> parameters = parameterService.findAll();
          log.info("Controller: Retrieved {} parameters", parameters.size());
          return ResponseEntity.ok().body(parameters);
      } catch (Exception e) {
          log.info(logMessage, e.getMessage());
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @GetMapping("/{id}")
  public ResponseEntity getParameterById(@PathVariable Long id) {
      try {
        log.info("Controller: Getting parameter with ID {}", id);
        Optional<ParameterDTO> parameter = parameterService.findById(id);
        if (parameter.isPresent()) {
            log.info("Controller: Parameter found: {}", parameter.get());
            return ResponseEntity.ok(parameter.get());
        } else {
            log.info("Controller: Parameter not found with ID {}", id);
            return ResponseEntity.notFound().build();
        }
      } catch (Exception e) {
          log.info(logMessage, e.getMessage());
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            log.info("Controller: Deleting parameter with ID {}", id);
            parameterService.delete(id);
            log.info("Controller: Parameter deleted with ID {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.info(logMessage, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
  }
