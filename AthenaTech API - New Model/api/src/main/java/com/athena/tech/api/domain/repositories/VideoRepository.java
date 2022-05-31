package com.athena.tech.api.domain.repositories;

import com.athena.tech.api.application.web.response.video.VideosResponse;
import com.athena.tech.api.domain.entity.VideoReponse;
import com.athena.tech.api.resources.orm.Video;
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
public interface VideoRepository extends JpaRepository<Video, Integer> {
    @Modifying
    @Query(value = "insert into video (title, description, url, posted_at," +
            "updated_at, likes, views, id_course) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
    void createVideo(String title, String description, String url, LocalDateTime now, LocalDateTime now1, int likes, int views, Integer idCourse);

    @Modifying
    @Query(value = "delete from video where id_video = ?1", nativeQuery = true)
    Integer deleteVideo(Integer idVideo);

    @Modifying
    @Query(value = "update video set title = ?1 where id_video = ?2", nativeQuery = true)
    Integer updateTitle(String content, Integer idVideo);

    @Modifying
    @Query(value = "update video set url = ?1 where id_video = ?2", nativeQuery = true)
    Integer updateUrl(String content, Integer idVideo);

    @Modifying
    @Query(value = "update video set description = ?1 where id_video = ?2", nativeQuery = true)
    Integer updateDesc(String content, Integer idVideo);

    @Query(value = "select v.id_video idVideo, v.title, v.description, v.url, v.posted_at postedAt, v.updated_at updatedAt, v.likes, v.views, v.id_course idCourse, c.title courseTitle" +
            " from video v join course c on c.id_course = v.id_course where v.id_course = ?1", nativeQuery = true)
    Collection<VideoReponse> findAllByIdCourseAndNameCourse(Integer idCourse);

    @Query(value = "select v.id_video idVideo, v.title, v.description, v.url, v.posted_at postedAt, v.updated_at updatedAt, v.likes, v.views, v.id_course idCourse, c.title courseTitle" +
            " from video v join course c on c.id_course = v.id_course where v.id_course = ?1", nativeQuery = true)
    List<VideoReponse> findAllByIdCourse(Integer idCourse);

    @Query(value = "select v.id_video idVideo, v.title, v.description, v.url, v.posted_at postedAt, v.updated_at updatedAt, v.likes, v.views, v.id_course idCourse, c.title courseTitle" +
            " from video v join course c on c.id_course = v.id_course where v.id_course = ?1 and v.id_video = ?2", nativeQuery = true)
    List<VideoReponse> findAllByIdCourseAndIdVideo(Integer idCourse, Integer idVideo);


}
