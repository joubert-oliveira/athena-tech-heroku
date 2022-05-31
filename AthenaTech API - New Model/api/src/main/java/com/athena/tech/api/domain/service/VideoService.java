package com.athena.tech.api.domain.service;

import com.athena.tech.api.application.web.request.GenericUpdateRequest;
import com.athena.tech.api.application.web.request.video.VideoRequest;
import com.athena.tech.api.application.web.response.ErrorMessageResponse;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.application.web.response.video.VideosResponse;
import com.athena.tech.api.domain.entity.VideoReponse;
import com.athena.tech.api.domain.repositories.VideoRepository;
import com.athena.tech.api.resources.orm.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class VideoService extends AuthenticateService {

    @Autowired
    VideoRepository videoRepository;

    public ResponseEntity registerVideo(VideoRequest videoRequest, Integer idUser) {

//        if (!authenticate.authenticateInstructor(idUser)){
//            return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Seu usuario não foi autorizado a postar um video"));
//        }
        try {
            videoRepository.createVideo(
                    videoRequest.getTitle(),
                    videoRequest.getDescription(),
                    videoRequest.getUrl().replace("watch?v=", "embed/"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    0,
                    0,
                    videoRequest.getIdCourse()
            );
            return ResponseEntity.status(201).body(new GenericMessageResponse("Successfully Created", "Video criado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity registerVideoFile(VideoRequest videoRequest) {
        try {
            videoRepository.createVideo(
                    videoRequest.getTitle(),
                    videoRequest.getDescription(),
                    videoRequest.getUrl().replace("watch?v=", "embed/"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    0,
                    0,
                    videoRequest.getIdCourse()
            );
            return ResponseEntity.status(201).body(new GenericMessageResponse("Successfully Created", "Video criado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity getAllVideosByIdCourse(Integer idUser, Integer idCourse) {
        if (!authenticate.authenticateInstructor(idUser)){
            return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Seu usuario não foi autorizado a postar um video"));
        }
        try {
            Collection<VideoReponse> response = videoRepository.findAllByIdCourseAndNameCourse(idCourse);
            if (response.isEmpty()){
                return ResponseEntity.status(204).body(new GenericMessageResponse("Not Content", "Nenhum video encontrado"));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity getAllVideosByIdCourseStudent(Integer idCourse) {
        try {
            List<VideoReponse> response = videoRepository.findAllByIdCourse(idCourse);
            if (response.isEmpty()){
                return ResponseEntity.status(204).body(new GenericMessageResponse("Not Content", "Nenhum video encontrado"));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity getVideoByIdCourseAndIdStudent(Integer idCourse,  Integer idVideo) {
        try {
            List<VideoReponse> response = videoRepository.findAllByIdCourseAndIdVideo(idCourse, idVideo);
            if (response.isEmpty()){
                return ResponseEntity.status(204).body(new GenericMessageResponse("Not Content", "Nenhum video encontrado"));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateTitle(GenericUpdateRequest genericUpdateRequest, Integer idVideo, Integer idUser) {
        if (!authenticate.authenticateInstructor(idUser)){
            return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Seu usuario não foi autorizado a postar um video"));
        }
        try {
            Integer response = videoRepository.updateTitle(genericUpdateRequest.getContent(), idVideo);
            if (response == 0){
                return ResponseEntity.status(404).body(new ErrorMessageResponse("Not found", "Video não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Updated", "Nome mudado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateUrl(GenericUpdateRequest genericUpdateRequest, Integer idVideo, Integer idUser) {
        if (!authenticate.authenticateInstructor(idUser)){
            return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Seu usuario não foi autorizado a deletar esse video um video"));
        }
        try {
            Integer response = videoRepository.updateUrl(genericUpdateRequest.getContent().replace("watch?v=", "embed/"), idVideo);
            if (response == 0){
                return ResponseEntity.status(404).body(new ErrorMessageResponse("Not found", "Video não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Updated Successfully", "Video alterado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateDesc(GenericUpdateRequest genericUpdateRequest, Integer idVideo, Integer idUser) {
        if (!authenticate.authenticateInstructor(idUser)){
            return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Seu usuario não foi autorizado a deletar esse video um video"));
        }
        try {
            Integer response = videoRepository.updateDesc(genericUpdateRequest.getContent(), idVideo);
            if (response == 0){
                return ResponseEntity.status(404).body(new ErrorMessageResponse("Not found", "Video não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Updated Successfully", "Video alterado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity deleteVideo(Integer idVideo, Integer idUser) {
        if (!authenticate.authenticateInstructor(idUser)){
            return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Seu usuario não foi autorizado a deletar esse video um video"));
        }
        try {
            Integer response = videoRepository.deleteVideo(idVideo);
            if (response == 0){
                return ResponseEntity.status(404).body(new ErrorMessageResponse("Not found", "Video não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Deleted Successfully", "Video deletado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }
}
