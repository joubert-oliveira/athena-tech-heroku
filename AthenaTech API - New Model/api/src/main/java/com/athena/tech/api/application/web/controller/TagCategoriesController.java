package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.domain.service.TagCategoriesService;
import com.athena.tech.api.resources.orm.TagCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tag-categories")
@CrossOrigin(origins = "*")
public class TagCategoriesController {

    @Autowired
    TagCategoriesService tagCategoriesService;

    @PostMapping
    public ResponseEntity postNewTagCategory(
            @RequestBody @Valid TagCategories tagCategories
    ) {
        return tagCategoriesService.createNewTagCategories(tagCategories);
    }

    @GetMapping
    public ResponseEntity getAllTagsCategories() {
        return tagCategoriesService.getAllTagCategories();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id) {
        return tagCategoriesService.deleteCategoryById(id);
    }
}
