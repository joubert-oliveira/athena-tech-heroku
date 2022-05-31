package com.athena.tech.api.application.web.response.video;

import java.time.LocalDateTime;

public class VideosResponse {

    private Integer idVideo;
    private String title;
    private String description;
    private String url;
    private LocalDateTime postedAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private int likes;
    private int views;
    private Integer idCourse;
    private String courseTitle;

    public VideosResponse(Integer idVideo, String title, String description, String url, LocalDateTime postedAt, LocalDateTime updatedAt, int likes, int views, Integer idCourse, String courseTitle) {
        this.idVideo = idVideo;
        this.title = title;
        this.description = description;
        this.url = url;
        this.postedAt = postedAt;
        this.updatedAt = updatedAt;
        this.likes = likes;
        this.views = views;
        this.idCourse = idCourse;
        this.courseTitle = courseTitle;
    }

    public Integer getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Integer idVideo) {
        this.idVideo = idVideo;
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

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }

    public String getcourseTitle() {
        return courseTitle;
    }

    public void setcourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
