package com.athena.tech.api.domain.entity.course;

import com.athena.tech.api.domain.commons.adapters.GenericAdapter;

import java.time.LocalDateTime;

public class CourseObj {

    private Integer idCourse;
    private String name;
    private String description;
    private String imgPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String knowledgeLevel;
    private String productorName;
    private Boolean isPublished;
    private int likes;
    private int views;
    private Integer idUser;
    private Integer idCategory;
    private Integer idTecnology;

    public CourseObj(Integer idCourse, String name, String description, String imgPath, LocalDateTime createdAt, LocalDateTime updatedAt, String knowledgeLevel, String productorName, Integer isPublished, int likes, int views, Integer idUser, Integer idCategory, Integer idTecnology) {
        GenericAdapter genericAdapter = new GenericAdapter();

        this.idCourse = idCourse;
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.knowledgeLevel = knowledgeLevel;
        this.productorName = productorName;
        this.isPublished = genericAdapter.adapterBitToBolean(isPublished);
        this.likes = likes;
        this.views = views;
        this.idUser = idUser;
        this.idCategory = idCategory;
        this.idTecnology = idTecnology;
    }

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getKnowledgeLevel() {
        return knowledgeLevel;
    }

    public void setKnowledgeLevel(String knowledgeLevel) {
        this.knowledgeLevel = knowledgeLevel;
    }

    public String getProductorName() {
        return productorName;
    }

    public void setProductorName(String productorName) {
        this.productorName = productorName;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
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
