package com.athena.tech.api.domain.repositories;

import com.athena.tech.api.domain.entity.CourseResponseRepository;
import com.athena.tech.api.resources.orm.SavedCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface SavedCoursesRepository
        extends JpaRepository<SavedCourses, Integer> {

    @Query("select sc.idCourse.idCourse from SavedCourses sc where sc.idUser = ?1")
    List<Integer> findByUserId(Integer id);


    @Query(value = "select c.id_course idCourse, c.id_user idUser, c.id_tecnology idTecnology, c.img_path imgPath, c.id_category idCategory, c.knowledge_level knowledgeLevel, c.title, c.description, c.created_at createdAt, c.updated_at updatedAt, c.productor_name productorName, c.likes, c.views " +
            "from saved_courses sc join course c on c.id_course = sc.id_course where sc.id_user = ?1", nativeQuery = true)
    Collection<CourseResponseRepository> findByUserIdFullContent(Integer idUser);

    SavedCourses findByIdCourseAndIdUser(Integer idCourse, Integer idUser);
}
