package com.fusiontech.emias.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Parameter implements Comparable<Parameter> {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String type;
    private String description;
    private int rank;

    @Override
    public int compareTo(Parameter o) {
        return Integer.compare(rank, o.getRank());
    }
}


