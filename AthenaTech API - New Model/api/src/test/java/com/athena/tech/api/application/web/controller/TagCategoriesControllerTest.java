package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.domain.service.TagCategoriesService;
import com.athena.tech.api.resources.orm.TagCategories;
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

@SpringBootTest(classes = {TagCategoriesController.class})
class TagCategoriesControllerTest {

    @MockBean
    TagCategoriesService tagCategoriesService;

    @Autowired
    TagCategoriesController controller;

    ResponseEntity created = new ResponseEntity(HttpStatus.CREATED);
    ResponseEntity badRequest = new ResponseEntity(HttpStatus.BAD_REQUEST);
    ResponseEntity ok = new ResponseEntity(HttpStatus.OK);
    ResponseEntity notFound = new ResponseEntity(HttpStatus.NOT_FOUND);


    @Test
    @DisplayName("postNewTagCategory() CORRETO")
    void postNewTagCategoryOk() {
        TagCategories tagMocked = mock(TagCategories.class);

        when(tagCategoriesService.createNewTagCategories(tagMocked)).thenReturn(created);

        ResponseEntity response = controller.postNewTagCategory(tagMocked);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("postNewTagCategory() INVÁLIDO")
    void postNewTagCategoryNotOk() {
        when(tagCategoriesService.createNewTagCategories(null)).thenReturn(badRequest);

        ResponseEntity response = controller.postNewTagCategory(null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllTagsCategories() CORRETO")
    void getAllTagsCategoriesOk() {
        TagCategories tagMocked = mock(TagCategories.class);
        TagCategories tagMocked1 = mock(TagCategories.class);
        TagCategories tagMocked2 = mock(TagCategories.class);

        when(tagCategoriesService.createNewTagCategories(tagMocked)).thenReturn(created);
        when(tagCategoriesService.createNewTagCategories(tagMocked1)).thenReturn(created);
        when(tagCategoriesService.createNewTagCategories(tagMocked2)).thenReturn(created);

        ResponseEntity response = controller.postNewTagCategory(tagMocked);
        ResponseEntity response1 = controller.postNewTagCategory(tagMocked1);
        ResponseEntity response2 = controller.postNewTagCategory(tagMocked2);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(201, response1.getStatusCodeValue());
        assertEquals(201, response2.getStatusCodeValue());

        when(tagCategoriesService.getAllTagCategories()).thenReturn(ok);

        ResponseEntity response3 = controller.getAllTagsCategories();

        assertEquals(200, response3.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllTagCategories() NÃO ENCONTRADO")
    void getAllTagCategoriesNotFound() {
        when(tagCategoriesService.getAllTagCategories()).thenReturn(notFound);

        ResponseEntity response = controller.getAllTagsCategories();

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCategoryById() CORRETO")
    void deleteCategoryByIdOk() {
        TagCategories tagMocked = mock(TagCategories.class);

        when(tagCategoriesService.deleteCategoryById(tagMocked.getIdCategory())).thenReturn(ok);

        ResponseEntity response = controller.deleteCategory(tagMocked.getIdCategory());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteCategoryById() NÃO ENCONTRADO")
    void deleteCategoryByIdNotFound() {
        when(tagCategoriesService.deleteCategoryById(1)).thenReturn(notFound);

        ResponseEntity response = controller.deleteCategory(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}