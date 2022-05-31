package com.athena.tech.api.domain.repositories;

import com.athena.tech.api.resources.orm.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Modifying
    @Query(value = "insert into course (title, description, img_path, created_at, updated_at, knowledge_level, " +
            "productor_name, likes, views, id_user, id_category, id_tecnology) " +
            "values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12)", nativeQuery = true)
    void createNewCourse(String title, String description, String imgPath, LocalDateTime createAt,
                         LocalDateTime updateAt, String knowledgeLevel, String productorName,
                         int likes, int views, int idUser, int idCategory, int idTecnology);

    List<Course> findAllByIdUser(Integer id);

    @Modifying
    @Query("delete Course c where c.idCourse = ?1")
    Integer deleteCourse(Integer id);

    @Modifying
    @Query("update Course c set c.title = ?2 where idCourse = ?1")
    Integer updateCourseTitle(Integer id, String content);

    @Modifying
    @Query("update Course c set c.description = ?2 where idCourse = ?1")
    Integer updateCourseDesc(Integer id, String content);

    @Modifying
    @Query("update Course c set c.imgPath = ?2 where idCourse = ?1")
    Integer updateCourseImg(Integer id, String content);

    @Query("select c.likes from Course c where idCourse = ?1")
    Integer countLikes(Integer id);

    @Modifying
    @Query("update Course c set c.likes = ?2 where idCourse = ?1")
    Integer updateLikes(Integer id, Integer newValue);

    @Query("select c.views from Course c where idCourse = ?1")
    Integer countViews(Integer id);

    @Modifying
    @Query("update Course c set c.views = ?2 where idCourse = ?1")
    Integer updateViews(Integer id, Integer newValue);

    @Query("select c.idCourse from Course c where c.title = ?1 and c.description = ?2")
    List<Integer> findByTitleAndDescription(String title, String description);

    @Query("update Course c set c.productorName = ?2 where idCourse = ?1")
    Integer updateProductorName(Integer id, String content);

    @Modifying
    @Query("update Course c set c.idCategory = ?2 where idCourse = ?1")
    Integer updateCategory(Integer id, Integer content);

    @Modifying
    @Query("update Course c set c.idTecnology = ?2 where idCourse = ?1")
    Integer updateTechnology(Integer id, Integer content);

    @Modifying
    @Query("update Course c set c.knowledgeLevel = ?2 where idCourse = ?1")
    Integer updateKnowledgeLevel(Integer id, String content);

    @Query("select c from Course c where c.title like %?1%")
    List<Course> findAllByTitle(String title);

    @Query("select c from Course c where c.idCategory = ?1")
    List<Course> findAllByIdCategory(Integer idCategory);

}
