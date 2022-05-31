package com.athena.tech.api.resources.orm;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SavedCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSaving;

    private Integer idUser;

    private Integer idCourse;
    private LocalDateTime addDate = LocalDateTime.now();

    public Integer getIdSaving() {
        return idSaving;
    }

    public void setIdSaving(Integer idSaving) {
        this.idSaving = idSaving;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}
