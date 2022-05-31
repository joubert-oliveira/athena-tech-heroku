package com.athena.tech.api.domain.service;

import com.athena.tech.api.application.web.response.ErrorMessageResponse;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.domain.repositories.TagTechnologiesRepository;
import com.athena.tech.api.resources.orm.TagTechnologies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TagTechnologiesService {

    @Autowired
    TagTechnologiesRepository tagTechnologiesRepository;

    public ResponseEntity createNewTagTechnology(TagTechnologies tagTechnologies) {
        try {
            tagTechnologiesRepository.save(tagTechnologies);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
        return ResponseEntity.status(201).body(new GenericMessageResponse("Successfully Created", "Tag de tecnologia criada com sucesso!"));
    }

    public ResponseEntity getAllTagTechnologies() {
        try {
            return ResponseEntity.status(201).body(tagTechnologiesRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity deleteTechnologyById(Integer id) {
        if (tagTechnologiesRepository.existsById(id)) {
            try {
                tagTechnologiesRepository.deleteById(id);
                return ResponseEntity.status(200).build();
            } catch (Exception e) {
                return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
            }
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}
