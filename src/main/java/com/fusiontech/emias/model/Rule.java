package com.fusiontech.emias.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Rule {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String url;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rule")
    private List<ParameterValue> parameterValues = new ArrayList<>();

}
