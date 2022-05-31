package com.athena.tech.api.domain.service;

import com.athena.tech.api.application.web.response.ErrorMessageResponse;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.application.web.response.course.CourseResponse;
import com.athena.tech.api.domain.commons.authenticate.Authenticate;
import com.athena.tech.api.domain.commons.list.StackObj;
import com.athena.tech.api.domain.entity.CourseResponseRepository;
import com.athena.tech.api.domain.repositories.SavedCoursesRepository;
import com.athena.tech.api.resources.orm.Course;
import com.athena.tech.api.resources.orm.SavedCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SavedCourseService extends AuthenticateService {
    @Autowired
    SavedCoursesRepository savedCoursesRepository;
    Authenticate authenticate = super.authenticate;

    StackObj<SavedCourses> lastCourseProcess = new StackObj<>(100);
    StackObj<SavedCourses> auxlastSavedCourse = new StackObj<>(100);

    public ResponseEntity saveCourse(SavedCourses saveCouseRequest, Integer id) {
        try {
            if (!authenticate.authenticateGeneric(id)){
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Voce não tem autorizacao para essa ação"));
            }
            SavedCourses savedCoursesVerify = savedCoursesRepository.findByIdCourseAndIdUser(saveCouseRequest.getIdCourse(), saveCouseRequest.getIdUser());
            if (savedCoursesVerify != null){
                return ResponseEntity.status(206).body(new GenericMessageResponse("Confict" ,"Esse curso ja foi favoritado"));
            }
            savedCoursesRepository.save(saveCouseRequest);
            SavedCourses savedCourses = savedCoursesRepository.findByIdCourseAndIdUser(saveCouseRequest.getIdCourse(), saveCouseRequest.getIdUser());
            if (savedCourses == null){
                return ResponseEntity.status(404).build();
            }
            lastCourseProcess.push(savedCourses);
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Created", "Curso salvo com sucesso"));
        } catch (IncorrectResultSizeDataAccessException e){
            return ResponseEntity.status(206).body(new GenericMessageResponse("Confict" ,"Esse curso ja foi favoritado"));
        }
        catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity deleteCourse(Integer idCourse, Integer idUser) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized", "Voce não tem autorizacao para essa ação"));
            }
            SavedCourses savedCourses = savedCoursesRepository.findByIdCourseAndIdUser(idCourse, idUser);
            savedCoursesRepository.delete(savedCourses);
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Deleted", "Curso salvo deletado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity ctrlY(Integer idUser) {
        try {
            if (!authenticate.authenticateGeneric(idUser)){
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Voce não tem autorizacao para essa ação"));
            }
            if (auxlastSavedCourse.isEmpity()){
                return ResponseEntity.status(404).build();
            }
            auxlastSavedCourse.peek().setIdSaving(null);
            lastCourseProcess.push(auxlastSavedCourse.peek());
            savedCoursesRepository.save(
                    auxlastSavedCourse.pop()
            );
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Created", "Curso salvo com sucesso"));
        }catch (IllegalStateException i) {
            return ResponseEntity.status(400).body(new GenericMessageResponse("Full list", "Está lista está cheia!"));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ErrorMessageResponse("Internal Error", "Exception: " + e));
        }
    }

    public ResponseEntity ctrlZ(Integer idUser) {
        try {
            if (!authenticate.authenticateGeneric(idUser)){
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Voce não tem autorizacao para essa ação"));
            }
            if (lastCourseProcess.isEmpity()){
                return ResponseEntity.status(404).build();
            }
            auxlastSavedCourse.push(lastCourseProcess.peek());
            savedCoursesRepository.delete(
                    lastCourseProcess.pop()
            );
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Deleted", "Curso salvo deletado com sucesso"));
        } catch (IllegalStateException i) {
            return ResponseEntity.status(400).body(new GenericMessageResponse("Full list", "Está lista está cheia!"));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ErrorMessageResponse("Internal Error", "Exception: " + e));
        }
    }

    public ResponseEntity getAllCoursesId(Integer id) {
        try {
            if (!authenticate.authenticateGeneric(id)){
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Voce não tem autorizacao para essa ação"));
            }
            List<Integer> savedCourses = savedCoursesRepository.findByUserId(id);

            return ResponseEntity.status(200).body(savedCourses);
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity getAllCoursesFullInfo(Integer idUser) {
        try {
            if (!authenticate.authenticateGeneric(idUser)){
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized","Voce não tem autorizacao para essa ação"));
            }
            Collection<CourseResponseRepository> savedCourses = savedCoursesRepository.findByUserIdFullContent(idUser);
            if (savedCourses == null){
                return ResponseEntity.status(204).body(new GenericMessageResponse("Not Content", "Nem um curso encontrado"));
            }
            return ResponseEntity.status(200).body(savedCourses);
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }
}
