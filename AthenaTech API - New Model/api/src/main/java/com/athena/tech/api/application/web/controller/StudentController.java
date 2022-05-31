package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.application.web.request.course.save.SaveCouseRequest;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.domain.entity.CourseResponseRepository;
import com.athena.tech.api.domain.service.SavedCourseService;
import com.athena.tech.api.resources.orm.SavedCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    SavedCourseService savedcourseService = new SavedCourseService();

    //TODO OK
    @PostMapping("/save-course/{idUser}")
    public ResponseEntity<GenericMessageResponse> saveNewCourse(
            @PathVariable Integer idUser,
            @RequestBody @Valid SavedCourses saveCouseRequest
    ) {
        return savedcourseService.saveCourse(saveCouseRequest, idUser);
    }

    //TODO OK
    @DeleteMapping("/delete-course/{idCourse}/{idUser}")
    public ResponseEntity deleteCourse(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser
    ) {
        return savedcourseService.deleteCourse(idCourse, idUser);
    }

    //TODO OK
    @GetMapping("/get-all/{idUser}")
    public ResponseEntity<List<Integer>> getAllSavedCourses(
            @PathVariable Integer idUser
    ) {
        return savedcourseService.getAllCoursesId(idUser);
    }

    //TODO OK
    @GetMapping("/get-all/{idUser}/fullContent")
    public ResponseEntity<List<CourseResponseRepository>> getAllSavedCoursesFullInfo(
            @PathVariable Integer idUser
    ) {
        return savedcourseService.getAllCoursesFullInfo(idUser);
    }

    //TODO OK
    @PostMapping("/re-save/{idUser}")
    public ResponseEntity<GenericMessageResponse> resave(
            @PathVariable Integer idUser
    ) {
        return savedcourseService.ctrlY(idUser);
    }

    @DeleteMapping("/undo/{idUser}")
    public ResponseEntity deleteLastCourse(
            @PathVariable Integer idUser
    ) {
        return savedcourseService.ctrlZ(idUser);
    }
}
