package com.athena.tech.api.application.web.request.video;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class VideoRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String url;
    @PositiveOrZero
    private Integer idCourse;

    public VideoRequest(String title, String description, String url, Integer idCourse) {
        this.title = title;
        this.description = description;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }
}
