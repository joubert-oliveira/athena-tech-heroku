package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.application.web.request.GenericUpdateRequest;
import com.athena.tech.api.application.web.request.course.CourseRequest;
import com.athena.tech.api.domain.service.CourseService;
import com.athena.tech.api.resources.orm.AthenaUser;
import com.athena.tech.api.resources.orm.Course;
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

@SpringBootTest(classes = {CourseController.class})
class CourseControllerTest {

    @MockBean
    CourseService courseService;

    @Autowired
    CourseController controller;

    ResponseEntity created = new ResponseEntity(HttpStatus.CREATED);
    ResponseEntity conflict = new ResponseEntity(HttpStatus.CONFLICT);
    ResponseEntity badRequest = new ResponseEntity(HttpStatus.BAD_REQUEST);
    ResponseEntity ok = new ResponseEntity(HttpStatus.OK);
    ResponseEntity notFound = new ResponseEntity(HttpStatus.NOT_FOUND);
    ResponseEntity unauthorized = new ResponseEntity(HttpStatus.UNAUTHORIZED);
    ResponseEntity notAcceptable = new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    ResponseEntity noContent = new ResponseEntity(HttpStatus.NO_CONTENT);

    @Test
    @DisplayName("getAllCourses() CRIAR e BUSCAR CORRETOS")
    void getAllCoursesOk() {
        AthenaUser athenaUser = mock(AthenaUser.class);
        CourseRequest course1 = mock(CourseRequest.class);
        CourseRequest course2 = mock(CourseRequest.class);

        when(courseService.createNewCourse(athenaUser.getIdUser(), course1)).thenReturn(created);
        when(courseService.createNewCourse(athenaUser.getIdUser(), course2)).thenReturn(created);

        ResponseEntity responseCreated1 = controller.postNewCourse(athenaUser.getIdUser(), course1);
        ResponseEntity responseCreated2 = controller.postNewCourse(athenaUser.getIdUser(), course2);

        assertEquals(201, responseCreated1.getStatusCodeValue());
        assertEquals(201, responseCreated2.getStatusCodeValue());

        when(courseService.getAllCourses()).thenReturn(ok);

        ResponseEntity responseOk = controller.getAllCurses();

        assertEquals(200, responseOk.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllCourses() SEM CONTEÚDO")
    void getAllCoursesNoContent() {
        when(courseService.getAllCourses()).thenReturn(noContent);

        ResponseEntity response = controller.getAllCurses();

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllCourseByTitle() CORRETO")
    void getAllCourseByTitleOk() {
        Course course = mock(Course.class);

        when(courseService.findAllByTitle(course.getTitle())).thenReturn(ok);

        ResponseEntity response = controller.getAllCouseByTitle(course.getTitle());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllCourseByTitle() NÃO ENCONTRADO")
    void getAllCourseByTitleNoContent() {
        when(courseService.findAllByTitle("teste")).thenReturn(noContent);

        ResponseEntity response = controller.getAllCouseByTitle("teste");

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllCourseByTitle() INVÁLIDO")
    void getAllCourseByTitleBadRequest() {
        when(courseService.findAllByTitle(null)).thenReturn(badRequest);

        ResponseEntity response = controller.getAllCouseByTitle(null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCourseById() CORRETO")
    void deleteCourseByIdOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(courseService.deleteCourseById(course.getIdCourse(), athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.deleteCourse(course.getIdCourse(), athenaUser.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCourseById() NÃO ENCONTRADO")
    void deleteCourseByIdNotFound() {
        when(courseService.deleteCourseById(1, 1)).thenReturn(notFound);

        ResponseEntity response = controller.deleteCourse(1, 1);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCourseById() NÃO AUTORIZADO")
    void deleteCourseByIdUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        athenaUser.setIsActive(0);

        when(courseService.deleteCourseById(course.getIdCourse(), athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.deleteCourse(course.getIdCourse(), athenaUser.getIdUser());

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCourseById() INVÁLIDO")
    void deleteCourseByIdIncorrect() {
        when(courseService.deleteCourseById(null, null)).thenReturn(unauthorized);

        ResponseEntity response = controller.deleteCourse(null, null);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseName() CORRETO")
    void updateCourseNameOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateCourseName(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(ok);

        ResponseEntity response = controller.updateCourseName(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseName() NÃO AUTORIZADO")
    void updateCourseNameUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        athenaUser.setIsActive(0);

        when(courseService.updateCourseName(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(unauthorized);

        ResponseEntity response = controller.updateCourseName(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseName() NÃO ENCONTRADO")
    void updateCourseNameNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateCourseName(1, 1, genericUpdateRequest))
                .thenReturn(notFound);

        ResponseEntity response = controller.updateCourseName(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseDescription() CORRETO")
    void updateCourseDescriptionOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateCourseDescription(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(ok);

        ResponseEntity response = controller.updateCourseDescription(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseDescription() NÃO ENCONTRADO")
    void updateCourseDescriptionNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateCourseDescription(1, 1, genericUpdateRequest))
                .thenReturn(notFound);

        ResponseEntity response = controller.updateCourseDescription(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseDescription() NÃO AUTORIZADO")
    void updateCourseDescriptionUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        athenaUser.setIsActive(0);

        when(courseService.updateCourseDescription(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(unauthorized);

        ResponseEntity response = controller.updateCourseDescription(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseImg() CORRETO")
    void updateCourseImgOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateCourseImg(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(ok);

        ResponseEntity response = controller.updateCourseImg(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseImg() NÃO ENCONTRADO")
    void updateCourseImgNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateCourseImg(1, 1, genericUpdateRequest))
                .thenReturn(notFound);

        ResponseEntity response = controller.updateCourseImg(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCourseImg() NÃO AUTORIZADO")
    void updateCourseImgUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        athenaUser.setIsActive(0);

        when(courseService.updateCourseImg(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(unauthorized);

        ResponseEntity response = controller.updateCourseImg(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateProductorName() CORRETO")
    void updateProductorNameOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateProductorName(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(ok);

        ResponseEntity response = controller.updateProductorName(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateProductorName() NÃO ENCONTRADO")
    void updateProductorNameNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateProductorName(1, 1, genericUpdateRequest))
                .thenReturn(notFound);

        ResponseEntity response = controller.updateProductorName(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateProductorName() NÃO AUTORIZADO")
    void updateProductorNameUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        athenaUser.setIsActive(0);

        when(courseService.updateProductorName(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(unauthorized);

        ResponseEntity response = controller.updateProductorName(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCategory() CORRETO")
    void updateCategoryOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateCategory(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(ok);

        ResponseEntity response = controller.updateCategory(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCategory() NÃO ENCONTRADO")
    void updateCategoryNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateCategory(1, 1, genericUpdateRequest))
                .thenReturn(notFound);

        ResponseEntity response = controller.updateCategory(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateCategory() NÃO AUTORIZADO")
    void updateCategoryUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        athenaUser.setIsActive(0);

        when(courseService.updateCategory(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(unauthorized);

        ResponseEntity response = controller.updateCategory(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTechnology() CORRETO")
    void updateTechnologyOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateTechnology(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(ok);

        ResponseEntity response = controller.updateTechnology(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTechnology() NÃO ENCONTRADO")
    void updateTechnologyNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateTechnology(1, 1, genericUpdateRequest))
                .thenReturn(notFound);

        ResponseEntity response = controller.updateTechnology(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTechnology() NÃO AUTORIZADO")
    void updateTechnologyUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        athenaUser.setIsActive(0);

        when(courseService.updateTechnology(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(unauthorized);

        ResponseEntity response = controller.updateTechnology(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateKnowledgeLevel() CORRETO")
    void updateKnowledgeLevelOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateKnowledgeLevel(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(ok);

        ResponseEntity response = controller.updateKnowledgeLevel(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateKnowledgeLevel() NÃO ENCONTRADO")
    void updateKnowledgeLevelNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(courseService.updateKnowledgeLevel(1, 1, genericUpdateRequest))
                .thenReturn(notFound);

        ResponseEntity response = controller.updateKnowledgeLevel(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateKnowledgeLevel() NÃO AUTORIZADO")
    void updateKnowledgeLevelUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        athenaUser.setIsActive(0);

        when(courseService.updateKnowledgeLevel(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest))
                .thenReturn(unauthorized);

        ResponseEntity response = controller.updateKnowledgeLevel(course.getIdCourse(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("addLike() CORRETO")
    void addLikeOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(courseService.addLike(course.getIdCourse(), athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.addLike(course.getIdCourse(), athenaUser.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("addLike() NÃO AUTORIZADO")
    void addLikeUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        athenaUser.setIsActive(0);

        when(courseService.addLike(course.getIdCourse(), athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.addLike(course.getIdCourse(), athenaUser.getIdUser());

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("addView() CORRETO")
    void addViewOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(courseService.addView(course.getIdCourse(), athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.addView(course.getIdCourse(), athenaUser.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("addView() NÃO AUTORIZADO")
    void addViewUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        athenaUser.setIsActive(0);

        when(courseService.addView(course.getIdCourse(), athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.addView(course.getIdCourse(), athenaUser.getIdUser());

        assertEquals(401, response.getStatusCodeValue());
    }
}