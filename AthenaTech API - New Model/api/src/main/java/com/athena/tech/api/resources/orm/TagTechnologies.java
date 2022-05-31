package com.athena.tech.api.resources.orm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class TagTechnologies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTecnology;
    @Size(max=40)
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Integer getIdTecnology() {
        return idTecnology;
    }

    public void setIdTecnology(Integer idTecnology) {
        this.idTecnology = idTecnology;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
