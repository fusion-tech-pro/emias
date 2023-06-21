package com.fusiontech.emias.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ParameterValue {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Parameter parameter;

    private String value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rule_id")
    private Rule rule;
}
