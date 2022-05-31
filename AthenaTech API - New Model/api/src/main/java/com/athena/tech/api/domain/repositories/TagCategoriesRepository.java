package com.athena.tech.api.domain.repositories;

import com.athena.tech.api.resources.orm.TagCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagCategoriesRepository extends JpaRepository<TagCategories, Integer> {
}
