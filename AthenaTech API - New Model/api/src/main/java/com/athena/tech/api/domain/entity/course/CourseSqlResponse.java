package com.athena.tech.api.domain.entity.course;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CourseSqlResponse {

    private Integer idCourse;
    private String title;
    private String description;
    private String imgPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String knowledgeLevel;
    private String productorName;
    private int likes;
    private int views;
    private Integer idCategory;
    private Integer idTecnology;
    private Integer idUser;

    public CourseSqlResponse(Integer idCourse, String title, String description, String imgPath, LocalDateTime createdAt, LocalDateTime updatedAt, String knowledgeLevel, String productorName, int likes, int views, Integer idCategory, Integer idTecnology, Integer idUser) {
        this.idCourse = idCourse;
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.knowledgeLevel = knowledgeLevel;
        this.productorName = productorName;
        this.likes = likes;
        this.views = views;
        this.idCategory = idCategory;
        this.idTecnology = idTecnology;
        this.idUser = idUser;
    }

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
