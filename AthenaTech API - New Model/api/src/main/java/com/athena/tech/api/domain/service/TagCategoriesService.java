package com.athena.tech.api.domain.service;

import com.athena.tech.api.application.web.response.ErrorMessageResponse;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.domain.commons.authenticate.Authenticate;
import com.athena.tech.api.domain.repositories.TagCategoriesRepository;
import com.athena.tech.api.resources.orm.TagCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TagCategoriesService {

    @Autowired
    TagCategoriesRepository tagCategoriesRepository;


    public ResponseEntity createNewTagCategories(TagCategories tagCategories) {
        try {
            tagCategoriesRepository.save(tagCategories);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
        return ResponseEntity.status(201).body(new GenericMessageResponse("Successfully Created", "Categoria criada com sucesso!"));
    }

    public ResponseEntity getAllTagCategories() {
        try {
            return ResponseEntity.status(201).body(tagCategoriesRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity deleteCategoryById(Integer id) {
        if (tagCategoriesRepository.existsById(id)) {
            try {
                tagCategoriesRepository.deleteById(id);
                return ResponseEntity.status(200).build();
            } catch (Exception e) {
                return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
            }
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}
