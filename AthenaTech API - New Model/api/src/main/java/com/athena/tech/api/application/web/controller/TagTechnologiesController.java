package com.athena.tech.api.application.web.controller;


import com.athena.tech.api.domain.service.TagTechnologiesService;
import com.athena.tech.api.resources.orm.TagTechnologies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tag-technologies")
@CrossOrigin(origins = "*")
public class TagTechnologiesController {

    @Autowired
    TagTechnologiesService tagTechnologiesService;

    @PostMapping
    public ResponseEntity postNewTagTechnology(
            @RequestBody @Valid TagTechnologies tagTechnologies
    ) {
        return tagTechnologiesService.createNewTagTechnology(tagTechnologies);
    }

    @GetMapping
    public ResponseEntity getAllTagTechnologies() {
        return tagTechnologiesService.getAllTagTechnologies();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTechnology(@PathVariable Integer id) {
        return tagTechnologiesService.deleteTechnologyById(id);
    }
}
