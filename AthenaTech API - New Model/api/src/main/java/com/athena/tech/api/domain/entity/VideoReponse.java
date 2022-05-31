package com.athena.tech.api.domain.entity;


import java.time.LocalDateTime;

public interface VideoReponse {
    public Integer getIdVideo();

    public String getTitle();

    public String getDescription();

    public String getUrl();

    public LocalDateTime getPostedAt();

    public LocalDateTime getUpdatedAt();

    public Integer getLikes();

    public Integer getViews();

    public Integer getIdCourse();

    public String getcourseTitle();
}
