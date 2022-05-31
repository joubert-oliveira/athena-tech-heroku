package com.athena.tech.api.domain.entity.course;

import com.athena.tech.api.application.web.request.course.CourseRequest;
import com.athena.tech.api.application.web.request.video.VideoRequest;
import com.athena.tech.api.domain.entity.video.VideoObj;

import java.util.List;

public class RegisterCourseAndVideo {
    private List<CourseRequest> courseObjList;
    private List<VideoRequest> videoObjList;

    public RegisterCourseAndVideo(List<CourseRequest> courseObjList, List<VideoRequest> videoObjList) {
        this.courseObjList = courseObjList;
        this.videoObjList = videoObjList;
    }

    public List<CourseRequest> getCourseObjList() {
        return courseObjList;
    }

    public void setCourseObjList(List<CourseRequest> courseObjList) {
        this.courseObjList = courseObjList;
    }

    public List<VideoRequest> getVideoObjList() {
        return videoObjList;
    }

    public void setVideoObjList(List<VideoRequest> videoObjList) {
        this.videoObjList = videoObjList;
    }
}
