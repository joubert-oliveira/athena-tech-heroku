package com.athena.tech.api.domain.entity;


import java.time.LocalDateTime;

public interface CourseResponseRepository {

    public Integer getIdCourse();

    public String getTitle();

    public String getDescription();

    public String getImgPath();

    public LocalDateTime getCreatedAt();

    public LocalDateTime getUpdatedAt();

    public String getKnowledgeLevel();

    public String getProductorName();

    public int getLikes();

    public int getViews();

    public Integer getIdCategory();

    public Integer getIdTecnology();

    public Integer getIdUser();
}
