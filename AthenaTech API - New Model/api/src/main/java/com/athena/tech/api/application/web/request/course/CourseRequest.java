package com.athena.tech.api.application.web.request.course;

import com.athena.tech.api.domain.enums.KnowledgeLevelEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class CourseRequest {
    private String title;
    @NotBlank
    @Size(max=255)
    private String description;
    @Size(max=255)
    private String imgPath;
    private KnowledgeLevelEnum knowledgeLevel;
    @NotBlank
    private String productorName;
    @PositiveOrZero
    private Integer idUser;
    @PositiveOrZero
    private Integer idCategory;
    @PositiveOrZero
    private Integer idTecnology;

    public CourseRequest(String title, String description, String imgPath, KnowledgeLevelEnum knowledgeLevel, String productorName, Integer idUser, Integer idCategory, Integer idTecnology) {
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.knowledgeLevel = knowledgeLevel;
        this.productorName = productorName;
        this.idUser = idUser;
        this.idCategory = idCategory;
        this.idTecnology = idTecnology;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public KnowledgeLevelEnum getKnowledgeLevel() {
        return knowledgeLevel;
    }

    public void setKnowledgeLevel(KnowledgeLevelEnum knowledgeLevel) {
        this.knowledgeLevel = knowledgeLevel;
    }

    public String getProductorName() {
        return productorName;
    }

    public void setProductorName(String productorName) {
        this.productorName = productorName;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Integer getIdTecnology() {
        return idTecnology;
    }

    public void setIdTecnology(Integer idTecnology) {
        this.idTecnology = idTecnology;
    }

}
