package com.athena.tech.api.domain.commons.adapters;

import com.athena.tech.api.application.web.response.course.CourseResponse;
import com.athena.tech.api.domain.enums.KnowledgeLevelEnum;
import com.athena.tech.api.resources.orm.Course;

public class CourseAdapter {
    public String adapterKnowlegeLevelToString(KnowledgeLevelEnum value) {
        if (value.equals(KnowledgeLevelEnum.BEGINNER)) {
            return "BEGINNER";
        } else if (value.equals(KnowledgeLevelEnum.INTERMEDIATE)) {
            return "INTERMEDIATE";
        } else if (value.equals(KnowledgeLevelEnum.ADVANCED)) {
            return "ADVANCED";
        } else {
            return null;
        }
    }

    public CourseResponse adapterCourseToCourseResponse(Course course) {
        try{
            return new CourseResponse(
                    course.getIdCourse(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getCreatedAt(),
                    course.getUpdatedAt(),
                    course.getProductorName(),
                    course.getLikes(),
                    course.getViews()
            );
        }catch (Exception e) {
            throw e;
        }
    }
}
