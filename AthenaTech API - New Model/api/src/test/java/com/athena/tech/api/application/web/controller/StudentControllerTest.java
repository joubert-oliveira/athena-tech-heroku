package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.domain.service.SavedCourseService;
import com.athena.tech.api.resources.orm.AthenaUser;
import com.athena.tech.api.resources.orm.SavedCourses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {StudentController.class})
class StudentControllerTest {

    @MockBean
    SavedCourseService savedCourseService;

    @Autowired
    StudentController controller;

    ResponseEntity created = new ResponseEntity(HttpStatus.CREATED);
    ResponseEntity conflict = new ResponseEntity(HttpStatus.CONFLICT);
    ResponseEntity badRequest = new ResponseEntity(HttpStatus.BAD_REQUEST);
    ResponseEntity ok = new ResponseEntity(HttpStatus.OK);
    ResponseEntity notFound = new ResponseEntity(HttpStatus.NOT_FOUND);
    ResponseEntity unauthorized = new ResponseEntity(HttpStatus.UNAUTHORIZED);
    ResponseEntity notAcceptable = new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

    @Test
    @DisplayName("saveNewCourse() CORRETO")
    void saveNewCourseOk() {
        AthenaUser athenaUser = mock(AthenaUser.class);
        SavedCourses savedCourses = mock(SavedCourses.class);

        when(savedCourseService.saveCourse(savedCourses, athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.saveNewCourse(athenaUser.getIdUser(), savedCourses);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("saveNewCourse() NÃO AUTORIZADO")
    void saveNewCourseUnauthorized() {
        AthenaUser athenaUser = mock(AthenaUser.class);
        SavedCourses savedCourses = mock(SavedCourses.class);
        athenaUser.setIsActive(0);

        when(savedCourseService.saveCourse(savedCourses, athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.saveNewCourse(athenaUser.getIdUser(), savedCourses);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("saveNewCourse() INVÁLIDO")
    void saveNewCourseBadRequest() {
        when(savedCourseService.saveCourse(null, null)).thenReturn(badRequest);

        ResponseEntity response = controller.saveNewCourse(null, null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCourse() CORRETO")
    void deleteCourseOk() {
        AthenaUser athenaUser = mock(AthenaUser.class);
        SavedCourses savedCourses = mock(SavedCourses.class);

        when(savedCourseService.deleteCourse(savedCourses.getIdCourse(), athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.deleteCourse(savedCourses.getIdCourse(), athenaUser.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCourse() NÃO AUTORIZADO")
    void deleteCourseUnauthorized() {
        AthenaUser athenaUser = mock(AthenaUser.class);
        SavedCourses savedCourses = mock(SavedCourses.class);
        athenaUser.setIsActive(0);

        when(savedCourseService.deleteCourse(savedCourses.getIdCourse(), athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.deleteCourse(savedCourses.getIdCourse(), athenaUser.getIdUser());

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCourse() INVÁLIDO")
    void deleteCourseBadRequest() {
        when(savedCourseService.deleteCourse(1, 1)).thenReturn(badRequest);

        ResponseEntity response = controller.deleteCourse(1, 1);

        assertEquals(400, response.getStatusCodeValue());
    }
}