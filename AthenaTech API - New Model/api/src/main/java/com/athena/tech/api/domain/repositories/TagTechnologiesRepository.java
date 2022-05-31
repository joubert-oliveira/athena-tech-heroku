package com.athena.tech.api.domain.repositories;

import com.athena.tech.api.resources.orm.TagTechnologies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagTechnologiesRepository
        extends JpaRepository<TagTechnologies, Integer> {
}
