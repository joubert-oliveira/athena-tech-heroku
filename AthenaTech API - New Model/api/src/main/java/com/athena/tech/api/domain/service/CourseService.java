package com.athena.tech.api.domain.service;

import com.athena.tech.api.application.web.request.GenericUpdateRequest;
import com.athena.tech.api.application.web.request.course.CourseRequest;
import com.athena.tech.api.application.web.request.course.CreateFileRequest;
import com.athena.tech.api.application.web.request.video.VideoRequest;
import com.athena.tech.api.application.web.response.AthenaUser.AthenaUserFormatedResponse;
import com.athena.tech.api.application.web.response.ErrorMessageResponse;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.application.web.response.course.CourseResponse;
import com.athena.tech.api.domain.commons.adapters.CourseAdapter;
import com.athena.tech.api.domain.commons.authenticate.Authenticate;
import com.athena.tech.api.domain.commons.file.FileUploadUtil;
import com.athena.tech.api.domain.commons.file.GenerateTxtFile;
import com.athena.tech.api.domain.commons.file.GenerateUserFile;
import com.athena.tech.api.domain.commons.file.TxtFileManagement;
import com.athena.tech.api.domain.commons.list.ListObj;
import com.athena.tech.api.domain.commons.list.QueueObj;
import com.athena.tech.api.domain.entity.CourseTxtResponse;
import com.athena.tech.api.domain.repositories.CourseRepository;
import com.athena.tech.api.domain.repositories.TagCategoriesRepository;
import com.athena.tech.api.domain.repositories.TagTechnologiesRepository;
import com.athena.tech.api.resources.orm.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService extends AuthenticateService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    VideoService videoService;

    @Autowired
    TagCategoriesRepository tagCategoriesRepository;
    @Autowired
    TagTechnologiesRepository tagTechnologiesRepository;

    CourseAdapter adapter = new CourseAdapter();
    Authenticate authenticate = super.authenticate;

    TxtFileManagement txtFileManagement = new TxtFileManagement();

    QueueObj<String> allInQueue = new QueueObj<>(10000);


    public ResponseEntity createNewCourseUsingFile(MultipartFile file) throws IOException, InterruptedException {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "O arquivo enviado está vazio"));
            }

            String filePathName = FileUploadUtil.saveFile(file.getOriginalFilename(), file);

            allInQueue.insert(filePathName);
            return ResponseEntity.status(200).build();

        }catch (IllegalStateException i){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "A fila de arquivos está cheia"));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }

    }

    public ResponseEntity commitFiles(Integer idUser) {
        try {
            if(!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).build();
            }
            if (allInQueue.isEmpty()){
                return ResponseEntity.status(204).build();
            }
            loopFiles(idUser);
            return ResponseEntity.status(200).build();

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(404).body(e.toString());
        }
    }

    private void loopFiles(Integer idUser) {
        while (!allInQueue.isEmpty()) {
            try {
                String file = allInQueue.pool();
                QueueObj<Object> value = txtFileManagement.read("Read-files/" + file);
                createInList(value, idUser);
            }
            catch (Exception e) {
                System.out.println("Erro: " + e);
            }
        }
    }

    private void createInList(QueueObj<Object> value, Integer idUser) throws Exception {
        Integer lastCourse = 0;

        try {
            while (!value.isEmpty()) {
                if (value.peek() instanceof CourseRequest) {

                    CourseRequest courseRequest = (CourseRequest) value.pool();

                    courseRepository.createNewCourse(
                            courseRequest.getTitle(),
                            courseRequest.getDescription(),
                            courseRequest.getImgPath(),
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            adapter.adapterKnowlegeLevelToString(courseRequest.getKnowledgeLevel()),
                            courseRequest.getProductorName(),
                            0,
                            0,
                            idUser,
                            courseRequest.getIdCategory(),
                            courseRequest.getIdTecnology()
                    );

                    List<Integer> ids = courseRepository.findByTitleAndDescription(courseRequest.getTitle(), courseRequest.getDescription());
                    lastCourse = ids.get(ids.size() - 1);
                    Thread.sleep(30000L);

                } else if (value.peek() instanceof VideoRequest) {

                    VideoRequest videoRequest = (VideoRequest) value.pool();
                    videoRequest.setIdCourse(lastCourse);
                    videoService.registerVideoFile(videoRequest);

                    Thread.sleep(30000L);

                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }
    }

    public ResponseEntity createNewCourse(Integer idUser, CourseRequest courseRequest) {

        if (!authenticate.authenticateInstructor(idUser)) {
            return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized", "Seu usuario não foi autorizado a cria um curso"));
        }
        try {
            courseRepository.createNewCourse(
                    courseRequest.getTitle(),
                    courseRequest.getDescription(),
                    courseRequest.getImgPath(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    adapter.adapterKnowlegeLevelToString(courseRequest.getKnowledgeLevel()),
                    courseRequest.getProductorName(),
                    0,
                    0,
                    courseRequest.getIdUser(),
                    courseRequest.getIdCategory(),
                    courseRequest.getIdTecnology()
            );
            return ResponseEntity.status(201).body(new GenericMessageResponse("Successfully Created", "Curso criado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity getAllCourses() {
        try {
            List<Course> response = courseRepository.findAll();
            if (response == null) {
                return ResponseEntity.status(204).body(new GenericMessageResponse("Not Content", "Nenhum curso foi encontrado"));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity getAllCoursesByInstructorId(Integer id) {
        try {
            if (!authenticate.authenticateInstructor(id)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized", "Seu usuario não foi autorizado a cria um curso"));
            }
            List<Course> response = courseRepository.findAllByIdUser(id);
            if (response == null) {
                return ResponseEntity.status(204).body(new GenericMessageResponse("Not Content", "Nenhum curso foi encontrado"));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity deleteCourseById(Integer idCourse, Integer idUser) {
        try {
            if (!authenticate.authenticateInstructor(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized", "Seu usuario não foi autorizado a cria um curso"));
            }
            Integer deletedCount = courseRepository.deleteCourse(idCourse);
            System.out.println(deletedCount);
            if (deletedCount == 0) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Curso não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully deleted", "Curso deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateCourseName(Integer idCourse, Integer idUser, GenericUpdateRequest content) {
        try {
            if (!authenticate.authenticateInstructor(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized", "Seu usuario não foi autorizado a cria um curso"));
            }
            Integer updatedCount = courseRepository.updateCourseTitle(idCourse, content.getContent());
            if (updatedCount == 0) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Curso não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully updated", "Nome alterado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateCourseDescription(Integer idCourse, Integer idUser, GenericUpdateRequest content) {
        try {
            if (!authenticate.authenticateInstructor(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized", "Seu usuario não foi autorizado a cria um curso"));
            }
            Integer updatedCount = courseRepository.updateCourseDesc(idCourse, content.getContent());
            if (updatedCount == 0) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Curso não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully updated", "Descrição alterada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateCourseImg(Integer idCourse, Integer idUser, GenericUpdateRequest content) {
        try {
            if (!authenticate.authenticateInstructor(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not authorized", "Seu usuario não foi autorizado a cria um curso"));
            }
            Integer updatedCount = courseRepository.updateCourseImg(idCourse, content.getContent());
            if (updatedCount == 0) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Curso não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully updated", "Imagem alterada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity addLike(Integer idCourse, Integer idUser) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            Integer findLikes = courseRepository.countLikes(idCourse);
            courseRepository.updateLikes(idCourse, (findLikes + 1));
            if (findLikes == null) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Id não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully liked", "Like registrado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity addView(Integer idCourse, Integer idUser) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            Integer findViews = courseRepository.countViews(idCourse);
            courseRepository.updateViews(idCourse, (findViews + 1));
            if (findViews == null) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Id não encontrado"));
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully view", "view registrado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }


    public ResponseEntity generateReport(Integer idUser, CreateFileRequest createFileRequest) {
        try {
            if (!authenticate.authenticateInstructor(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            List<Course> findCourses = courseRepository.findAllByIdUser(idUser);
            if (findCourses == null) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Id não encontrado"));
            }
            List<CourseResponse> courseResponses = new ArrayList<>();
            GenerateUserFile generateUserFile = new GenerateUserFile();
            findCourses.stream().forEach(
                    course -> courseResponses.add(adapter.adapterCourseToCourseResponse(course))
            );
            ListObj<CourseResponse> courseResponseListObj = new ListObj<>(courseResponses.size());
            for (CourseResponse courseResponse : courseResponses) {
                courseResponseListObj.add(courseResponse);
            }
            generateUserFile.gravaArquivoCsv(courseResponseListObj, createFileRequest.getName());
            return ResponseEntity
                    .status(201)
                    .header("content-type", "text/csv")
                    .header("content-disposition",
                            String.format("filename=\"%s.csv\"", createFileRequest.getName()
                            )
                    )
                    .body(generateUserFile.exibeArquivoCsv(createFileRequest.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateProductorName(Integer idCourse, Integer idUser, GenericUpdateRequest genericUpdateRequest) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            courseRepository.updateProductorName(idCourse, genericUpdateRequest.getContent());

            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully view", "view registrado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateCategory(Integer idCourse, Integer idUser, GenericUpdateRequest genericUpdateRequest) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            courseRepository.updateCategory(idCourse, Integer.valueOf(genericUpdateRequest.getContent()));

            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully view", "view registrado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateTechnology(Integer idCourse, Integer idUser, GenericUpdateRequest genericUpdateRequest) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            courseRepository.updateTechnology(idCourse, Integer.valueOf(genericUpdateRequest.getContent()));

            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully view", "view registrado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updateKnowledgeLevel(Integer idCourse, Integer idUser, GenericUpdateRequest genericUpdateRequest) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            courseRepository.updateKnowledgeLevel(idCourse, genericUpdateRequest.getContent());

            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully view", "view registrado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity findAllByTitle(String title) {
        try {
            List<Course> response = courseRepository.findAllByTitle(title);
            if (response == null) {
                return ResponseEntity.status(204).body(new GenericMessageResponse("Not Content", "Nem um curso foi encontrado"));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity findAllByIdCategory(Integer idCategory) {
        try {
            List<Course> response = courseRepository.findAllByIdCategory(idCategory);
            if (response == null) {
                return ResponseEntity.status(204).body(new GenericMessageResponse("Not Content", "Nenhum curso foi encontrado"));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity downloadTxtFile(Integer idUser) {
        if (!authenticate.authenticateGeneric(idUser)) {
            return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
        }
        try{
            List<CourseTxtResponse> bodyResponse = new ArrayList<>();
            List<Course> repositoryResponse = courseRepository.findAllByIdUser(idUser);
            repositoryResponse.stream().forEach(
                    course ->
                            bodyResponse.add(
                                    new CourseTxtResponse(
                                            course.getTitle(),
                                            course.getDescription(),
                                            course.getImgPath(),
                                            course.getKnowledgeLevel(),
                                            course.getProductorName(),
                                            course.getLikes(),
                                            course.getViews(),
                                            tagCategoriesRepository.getById(course.getIdCategory()).getName(),
                                            tagTechnologiesRepository.getById(course.getIdTecnology()).getName(),
                                            course.getIdUser()
                                    )
                            )
            );

            if (repositoryResponse.isEmpty()){
                return ResponseEntity.status(204).build();
            }
            GenerateTxtFile generateTxtFile = new GenerateTxtFile();
            String response = generateTxtFile.createArqAndContent(bodyResponse);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }
}
