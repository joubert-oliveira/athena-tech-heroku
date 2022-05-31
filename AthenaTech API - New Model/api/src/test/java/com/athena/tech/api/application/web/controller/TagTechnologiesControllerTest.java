package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.domain.service.TagTechnologiesService;
import com.athena.tech.api.resources.orm.TagCategories;
import com.athena.tech.api.resources.orm.TagTechnologies;
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

@SpringBootTest(classes = {TagTechnologiesController.class})
class TagTechnologiesControllerTest {

    @MockBean
    TagTechnologiesService tagTechnologiesService;

    @Autowired
    TagTechnologiesController controller;

    ResponseEntity created = new ResponseEntity(HttpStatus.CREATED);
    ResponseEntity badRequest = new ResponseEntity(HttpStatus.BAD_REQUEST);
    ResponseEntity ok = new ResponseEntity(HttpStatus.OK);
    ResponseEntity notFound = new ResponseEntity(HttpStatus.NOT_FOUND);

    @Test
    @DisplayName("postNewTagCategory() CORRETO")
    void postNewTagCategoryOk() {
        TagTechnologies tagMocked = mock(TagTechnologies.class);

        when(tagTechnologiesService.createNewTagTechnology(tagMocked)).thenReturn(created);

        ResponseEntity response = controller.postNewTagTechnology(tagMocked);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("postNewTagCategory() INVÁLIDO")
    void postNewTagCategoryNotOk() {
        when(tagTechnologiesService.createNewTagTechnology(null)).thenReturn(badRequest);

        ResponseEntity response = controller.postNewTagTechnology(null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllTagTechnologies() CORRETO")
    void getAllTagsCategoriesOk() {
        TagTechnologies tagMocked = mock(TagTechnologies.class);
        TagTechnologies tagMocked1 = mock(TagTechnologies.class);
        TagTechnologies tagMocked2 = mock(TagTechnologies.class);

        when(tagTechnologiesService.createNewTagTechnology(tagMocked)).thenReturn(created);
        when(tagTechnologiesService.createNewTagTechnology(tagMocked1)).thenReturn(created);
        when(tagTechnologiesService.createNewTagTechnology(tagMocked2)).thenReturn(created);

        ResponseEntity response = controller.postNewTagTechnology(tagMocked);
        ResponseEntity response1 = controller.postNewTagTechnology(tagMocked1);
        ResponseEntity response2 = controller.postNewTagTechnology(tagMocked2);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(201, response1.getStatusCodeValue());
        assertEquals(201, response2.getStatusCodeValue());

        when(tagTechnologiesService.getAllTagTechnologies()).thenReturn(ok);

        ResponseEntity response3 = controller.getAllTagTechnologies();

        assertEquals(200, response3.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllTagTechnologies() NÃO ENCONTRADO")
    void getAllTagCategoriesNotFound() {
        when(tagTechnologiesService.getAllTagTechnologies()).thenReturn(notFound);

        ResponseEntity response = controller.getAllTagTechnologies();

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteTechnology() CORRETO")
    void deleteCategoryByIdOk() {
        TagCategories tagMocked = mock(TagCategories.class);

        when(tagTechnologiesService.deleteTechnologyById(tagMocked.getIdCategory())).thenReturn(ok);

        ResponseEntity response = controller.deleteTechnology(tagMocked.getIdCategory());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteTechnology() NÃO ENCONTRADO")
    void deleteCategoryByIdNotFound() {
        when(tagTechnologiesService.deleteTechnologyById(1)).thenReturn(notFound);

        ResponseEntity response = controller.deleteTechnology(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}