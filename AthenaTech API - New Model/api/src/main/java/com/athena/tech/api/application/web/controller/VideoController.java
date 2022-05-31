package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.application.web.request.GenericUpdateRequest;
import com.athena.tech.api.application.web.request.video.VideoRequest;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.domain.service.VideoService;
import com.athena.tech.api.resources.orm.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/videos")
@CrossOrigin(origins = "*")
public class VideoController {
    @Autowired
    private VideoService videoService = new VideoService();

    @PostMapping("/{idUser}")
    public ResponseEntity<GenericMessageResponse> registerNewVideo(
            @PathVariable Integer idUser,
            @RequestBody @Valid VideoRequest videoRequest
    ) {
        return videoService.registerVideo(videoRequest, idUser);
    }

    @GetMapping("/{idUser}/{idCourse}")
    public ResponseEntity<List<Video>> getAllVideos(
            @PathVariable Integer idUser,
            @PathVariable Integer idCourse
    ) {
        return videoService.getAllVideosByIdCourse(idUser, idCourse);
    }

    @GetMapping("/student/{idCourse}")
    public ResponseEntity<List<Video>> getAllVideosStudent(
            @PathVariable Integer idCourse
    ) {
        return videoService.getAllVideosByIdCourseStudent(idCourse);
    }

    @GetMapping("/student/{idCourse}/{idVideo}")
    public ResponseEntity<List<Video>> getVideoStudent(
            @PathVariable Integer idCourse,
            @PathVariable Integer idVideo
    ) {
        return videoService.getVideoByIdCourseAndIdStudent(idCourse, idVideo);
    }

    @PutMapping("/update-name/{idUser}/video/{idVideo}")
    public ResponseEntity<GenericMessageResponse> updateTitle(
            @PathVariable Integer idVideo,
            @PathVariable Integer idUser,
            @RequestBody GenericUpdateRequest genericUpdateRequest
            ) {
        return videoService.updateTitle(genericUpdateRequest, idVideo, idUser);
    }

    @PutMapping("/update-url/{idUser}/video/{idVideo}")
    public ResponseEntity<GenericMessageResponse> updateUrl(
            @PathVariable Integer idVideo,
            @PathVariable Integer idUser,
            @RequestBody GenericUpdateRequest genericUpdateRequest
    ) {
        return videoService.updateUrl(genericUpdateRequest, idVideo, idUser);
    }

    @PutMapping("/update-desc/{idUser}/video/{idVideo}")
    public ResponseEntity<GenericMessageResponse> updateDesc(
            @PathVariable Integer idVideo,
            @PathVariable Integer idUser,
            @RequestBody GenericUpdateRequest genericUpdateRequest
    ) {
        return videoService.updateDesc(genericUpdateRequest, idVideo, idUser);
    }

    @DeleteMapping("/{idVideo}/{idUser}")
    public ResponseEntity deleteVideo(
            @PathVariable Integer idVideo,
            @PathVariable Integer idUser
    ) {
        return videoService.deleteVideo(idVideo, idUser);
    }
}
