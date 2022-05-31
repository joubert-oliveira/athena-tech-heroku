package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.application.web.request.GenericUpdateRequest;
import com.athena.tech.api.application.web.request.course.CourseRequest;
import com.athena.tech.api.application.web.request.course.CreateFileRequest;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.domain.service.CourseService;
import com.athena.tech.api.resources.orm.Course;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    CourseService courseService = new CourseService();

    //TODO OK
    @PostMapping("/instructor/{idUser}")
    public ResponseEntity<GenericMessageResponse> postNewCourse(
            @PathVariable Integer idUser,
            @RequestBody @Valid CourseRequest courseRequest
            ) {
        return courseService.createNewCourse(idUser, courseRequest);
    }

    //TODO OK
    @GetMapping
    public ResponseEntity<List<Course>> getAllCurses(){
        return courseService.getAllCourses();
    }

    @GetMapping("{title}")
    public ResponseEntity getAllCouseByTitle(@PathVariable String title){
        return courseService.findAllByTitle(title);
    }

    @GetMapping("/category/{idCategory}")
    public ResponseEntity getAllCouseByCategory(@PathVariable Integer idCategory){
        return courseService.findAllByIdCategory(idCategory);
    }

    //TODO OK
    @GetMapping("/instructor/{idUser}")
    public ResponseEntity<List<Course>> getAllCoursesByInstructorId(
            @PathVariable Integer idUser
            ){
        return courseService.getAllCoursesByInstructorId(idUser);
    }

    //TODO Excluir todos os videos atrelados a aquele curso
    @DeleteMapping("/instructor/{idUser}/course/{idCourse}")
    public ResponseEntity <GenericMessageResponse> deleteCourse(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser
    ) {
        return courseService.deleteCourseById(idCourse, idUser);
    }

    //TODO OK
    @PutMapping("/instructor/{idUser}/rename-title/{idCourse}")
    public ResponseEntity<GenericMessageResponse> updateCourseName(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser,
            @RequestBody @Valid GenericUpdateRequest genericUpdateRequest
    ) {
        return courseService.updateCourseName(idCourse, idUser, genericUpdateRequest);
    }

    //TODO OK
    @PutMapping("/instructor/{idUser}/rename-description/{idCourse}")
    public ResponseEntity<GenericMessageResponse> updateCourseDescription(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser,
            @RequestBody @Valid GenericUpdateRequest genericUpdateRequest
    ) {
        return courseService.updateCourseDescription(idCourse, idUser, genericUpdateRequest);
    }

    //TODO OK
    @PutMapping("/instructor/{idUser}/change-img/{idCourse}")
    public ResponseEntity<GenericMessageResponse> updateCourseImg(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser,
            @RequestBody @Valid GenericUpdateRequest genericUpdateRequest
    ) {
        return courseService.updateCourseImg(idCourse, idUser, genericUpdateRequest);
    }

    //TODO OK
    @PutMapping("/instructor/{idUser}/change-productor-name/{idCourse}")
    public ResponseEntity<GenericMessageResponse> updateProductorName(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser,
            @RequestBody @Valid GenericUpdateRequest genericUpdateRequest
    ) {
        return courseService.updateProductorName(idCourse, idUser, genericUpdateRequest);
    }

    //TODO OK
    @PutMapping("/instructor/{idUser}/change-category/{idCourse}")
    public ResponseEntity<GenericMessageResponse> updateCategory(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser,
            @RequestBody @Valid GenericUpdateRequest genericUpdateRequest
    ) {
        return courseService.updateCategory(idCourse, idUser, genericUpdateRequest);
    }

    //TODO OK
    @PutMapping("/instructor/{idUser}/change-technology/{idCourse}")
    public ResponseEntity<GenericMessageResponse> updateTechnology(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser,
            @RequestBody @Valid GenericUpdateRequest genericUpdateRequest
    ) {
        return courseService.updateTechnology(idCourse, idUser, genericUpdateRequest);
    }

    //TODO OK
    @PutMapping("/instructor/{idUser}/change-knowledgeLevel/{idCourse}")
    public ResponseEntity<GenericMessageResponse> updateKnowledgeLevel(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser,
            @RequestBody @Valid GenericUpdateRequest genericUpdateRequest
    ) {
        return courseService.updateKnowledgeLevel(idCourse, idUser, genericUpdateRequest);
    }

    // TODO Desenvolver sistema de um like por pessoa
    @PutMapping("/add-like/{idUser}/course/{idCourse}")
    public ResponseEntity<GenericMessageResponse> addLike(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser
    ) {
        return  courseService.addLike(idCourse, idUser);
    }

    //TODO OK
    @PutMapping("/add-view/{idUser}/course/{idCourse}")
    public ResponseEntity<GenericMessageResponse> addView(
            @PathVariable Integer idCourse,
            @PathVariable Integer idUser
    ) {
        return  courseService.addView(idCourse, idUser);
    }

    //TODO OK
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content( mediaType = "text/csv")
                    )
            }
    )
    @PostMapping("/report/{idUser}")
    public ResponseEntity getReport(
            @PathVariable Integer idUser,
            @RequestBody CreateFileRequest createFileRequest
            ) {
        return courseService.generateReport(idUser, createFileRequest);
    }

    //TODO Concertar retorno
    @PostMapping(value = "/instructor/subir-txt", consumes = { "multipart/form-data" })
    public ResponseEntity postNewCourseFile(
            @RequestParam("courses-and-video") MultipartFile file
    ) throws IOException, InterruptedException {
        return courseService.createNewCourseUsingFile(file);
    }

    //TODO Concertar retorno
    @PostMapping("/execute-queue/{idUser}")
    public ResponseEntity commitFilesEndpoint(
            @PathVariable Integer idUser
    ) {
        return courseService.commitFiles(idUser);
    }

//    ESTÁ OK
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content( mediaType = "text/plain")
                    )
            }
    )
    @PostMapping("/download-txt/{idUser}")
    public ResponseEntity downloadTxt(
            @PathVariable Integer idUser
    ) {
        return courseService.downloadTxtFile(idUser);
    }
}
