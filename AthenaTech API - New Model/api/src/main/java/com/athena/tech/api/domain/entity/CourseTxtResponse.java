package com.athena.tech.api.domain.entity;


public class CourseTxtResponse {

    private String title;
    private String description;
    private String imgPath;
    private String knowledgeLevel;
    private String productorName;
    private int likes;
    private int views;
    private String category;
    private String tecnology;
    private Integer idUser;

    public CourseTxtResponse(String title, String description, String imgPath, String knowledgeLevel, String productorName, int likes, int views, String category, String tecnology, Integer idUser) {
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.knowledgeLevel = knowledgeLevel;
        this.productorName = productorName;
        this.likes = likes;
        this.views = views;
        this.category = category;
        this.tecnology = tecnology;
        this.idUser = idUser;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTecnology() {
        return tecnology;
    }

    public void setTecnology(String tecnology) {
        this.tecnology = tecnology;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
