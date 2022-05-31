package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.application.web.request.GenericUpdateRequest;
import com.athena.tech.api.application.web.request.video.VideoRequest;
import com.athena.tech.api.domain.service.VideoService;
import com.athena.tech.api.resources.orm.AthenaUser;
import com.athena.tech.api.resources.orm.Course;
import com.athena.tech.api.resources.orm.Video;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {VideoController.class})
class VideoControllerTest {

    @MockBean
    VideoService videoService;

    @Autowired
    VideoController controller;

    ResponseEntity created = new ResponseEntity(HttpStatus.CREATED);
    ResponseEntity badRequest = new ResponseEntity(HttpStatus.BAD_REQUEST);
    ResponseEntity ok = new ResponseEntity(HttpStatus.OK);
    ResponseEntity notFound = new ResponseEntity(HttpStatus.NOT_FOUND);
    ResponseEntity unauthorized = new ResponseEntity(HttpStatus.UNAUTHORIZED);
    ResponseEntity noContent = new ResponseEntity(HttpStatus.NO_CONTENT);

    @Test
    @DisplayName("registerNewVideo() CORRETO")
    void registerNewVideoOk() {
        VideoRequest videoMocked = mock(VideoRequest.class);
        AthenaUser userMocked = mock(AthenaUser.class);

        when(videoService.registerVideo(videoMocked, userMocked.getIdUser())).thenReturn(created);

        ResponseEntity response = controller.registerNewVideo(userMocked.getIdUser(), videoMocked);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("registerNewVideo() INVÁLIDO")
    void registerNewVideoNotOk() {
        when(videoService.registerVideo(null, null)).thenReturn(badRequest);

        ResponseEntity response = controller.registerNewVideo(null, null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTitle() CORRETO")
    void updateTitleOk() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        Video video = mock(Video.class);
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(videoService.updateTitle(genericUpdateRequest, video.getIdVideo(), athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.updateTitle(video.getIdVideo(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTitle() NÃO AUTORIZADO")
    void updateTitleUnauthorized() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        Video video = mock(Video.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        athenaUser.setIsActive(0);

        when(videoService.updateTitle(genericUpdateRequest, video.getIdVideo(), athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.updateTitle(video.getIdVideo(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTitle() NÃO ENCONTRADO")
    void updateTitleNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(videoService.updateTitle(genericUpdateRequest, 1, 1)).thenReturn(notFound);

        ResponseEntity response = controller.updateTitle(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTitle() INVÁLIDO")
    void updateTitleNotOk() {
        when(videoService.updateTitle(null, null, null)).thenReturn(badRequest);

        ResponseEntity response = controller.updateTitle(null, null, null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateUrl() CORRETO")
    void updateUrlOk() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        Video video = mock(Video.class);
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(videoService.updateUrl(genericUpdateRequest, video.getIdVideo(), athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.updateUrl(video.getIdVideo(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateUrl() NÃO AUTORIZADO")
    void updateUrlUnauthorized() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        Video video = mock(Video.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        athenaUser.setIsActive(0);

        when(videoService.updateUrl(genericUpdateRequest, video.getIdVideo(), athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.updateUrl(video.getIdVideo(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTitle() NÃO ENCONTRADO")
    void updateUrlNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(videoService.updateUrl(genericUpdateRequest, 1, 1)).thenReturn(notFound);

        ResponseEntity response = controller.updateUrl(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateTitle() INVÁLIDO")
    void updateUrlNotOk() {
        when(videoService.updateUrl(null, null, null)).thenReturn(badRequest);

        ResponseEntity response = controller.updateUrl(null, null, null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateDesc() CORRETO")
    void updateDescOk() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        Video video = mock(Video.class);
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(videoService.updateDesc(genericUpdateRequest, video.getIdVideo(), athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.updateDesc(video.getIdVideo(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateDesc() NÃO AUTORIZADO")
    void updateDescUnauthorized() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);
        Video video = mock(Video.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        athenaUser.setIsActive(0);

        when(videoService.updateDesc(genericUpdateRequest, video.getIdVideo(), athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.updateDesc(video.getIdVideo(), athenaUser.getIdUser(), genericUpdateRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateDesc() NÃO ENCONTRADO")
    void updateDescNotFound() {
        GenericUpdateRequest genericUpdateRequest = mock(GenericUpdateRequest.class);

        when(videoService.updateDesc(genericUpdateRequest, 1, 1)).thenReturn(notFound);

        ResponseEntity response = controller.updateDesc(1, 1, genericUpdateRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateDesc() INVÁLIDO")
    void updateDescNotOk() {
        when(videoService.updateDesc(null, null, null)).thenReturn(badRequest);

        ResponseEntity response = controller.updateDesc(null, null, null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteVideo() CORRETO")
    void deleteVideoOk() {
        Video video = mock(Video.class);
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(videoService.deleteVideo(video.getIdVideo(), athenaUser.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.deleteVideo(video.getIdVideo(), athenaUser.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteVideo() NÃO AUTORIZADO")
    void deleteVideoUnauthorized() {
        Video video = mock(Video.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        athenaUser.setIsActive(0);

        when(videoService.deleteVideo(video.getIdVideo(), athenaUser.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.deleteVideo(video.getIdVideo(), athenaUser.getIdUser());

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteVideo() NÃO ENCONTRADO E INVÁLIDO")
    void deleteVideoNotFound() {
        when(videoService.deleteVideo(1, 1)).thenReturn(notFound);
        ResponseEntity response = controller.deleteVideo(1, 1);
        assertEquals(404, response.getStatusCodeValue());

        when(videoService.deleteVideo(null, null)).thenReturn(badRequest);
        ResponseEntity response1 = controller.deleteVideo(null, null);
        assertEquals(400, response1.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllVideos() CORRETO")
    void getAllVideosOk() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(videoService.getAllVideosByIdCourse(athenaUser.getIdUser(), course.getIdCourse())).thenReturn(ok);

        ResponseEntity response = controller.getAllVideos(athenaUser.getIdUser(), course.getIdCourse());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllVideos() NÃO AUTORIZADO")
    void getAllVideosUnauthorized() {
        Course course = mock(Course.class);
        AthenaUser athenaUser = mock(AthenaUser.class);
        athenaUser.setIsActive(0);

        when(videoService.getAllVideosByIdCourse(athenaUser.getIdUser(), course.getIdCourse())).thenReturn(unauthorized);

        ResponseEntity response = controller.getAllVideos(athenaUser.getIdUser(), course.getIdCourse());

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllVideos() SEM VÍDEOS")
    void getAllVideosNotFound() {
        AthenaUser athenaUser = mock(AthenaUser.class);

        when(videoService.getAllVideosByIdCourse(athenaUser.getIdUser(), 45634)).thenReturn(noContent);

        ResponseEntity response = controller.getAllVideos(athenaUser.getIdUser(), 45634);

        assertEquals(204, response.getStatusCodeValue());
    }
}