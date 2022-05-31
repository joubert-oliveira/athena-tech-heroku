package com.athena.tech.api.domain.commons.file;

import com.athena.tech.api.application.web.request.course.CourseRequest;
import com.athena.tech.api.application.web.request.video.VideoRequest;
import com.athena.tech.api.domain.commons.list.QueueObj;
import com.athena.tech.api.domain.enums.KnowledgeLevelEnum;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TxtFileManagement {

    private void whriteInFile(String content, String fileName) {
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter(fileName, true));
        } catch (IOException erro) {
            System.out.println("Erro na abertura do arquivo: " + erro);
        }

        try {
            saida.append(content + "\n");
            saida.close();
        } catch (IOException erro) {
            System.out.println("Erro na gravação do arquivo: " + erro);
        }
    }

    public void createArqAndString() {
        List<Object> objectList = new ArrayList<>();

        objectList.add(new CourseRequest("Curso de Java", "Aprenda Java corretamente", "https://constantetecnologia.com.br/wp-content/uploads/2020/06/800px-Java-Debugging-Tips-881x441-1.jpg", KnowledgeLevelEnum.valueOf("BEGINNER"), "Carlos Henrique", 64, 02, 04));
        objectList.add(new VideoRequest("Aula Curso Java: 1", "Aprenda a usar o console.log de maneira objetiva", "https://www.youtube.com/watch?v=sTX0UEplF54&list=PLHz_AreHm4dkI2ZdjTwZA4mPMxWTfNSpR", 999));

        objectList.add(new CourseRequest("Curso de Python", "A nossa priori é fazer você aprender Python", "https://itforum.com.br/wp-content/uploads/2021/07/shutterstock_1447245065-1-scaled.jpg", KnowledgeLevelEnum.valueOf("INTERMEDIATE"), "Bruno Mendes", 64, 04, 03));
        objectList.add(new VideoRequest("Aula Curso Python: 1", "Aprenda Machine Learning", "https://www.youtube.com/watch?v=JyGGMyR3x5I", 999));
        objectList.add(new VideoRequest("Aula Curso Python: 2", "Data Science", "https://www.youtube.com/watch?v=F608hzn_ygo", 999));

        objectList.add(new CourseRequest("Curso React", "Aprenda componentização com o melhor.", "https://miro.medium.com/max/900/1*qZT6HsLWRHftyq5SUaVVyg.png", KnowledgeLevelEnum.valueOf("ADVANCED"), "Isaque Cruz", 64, 03, 07));
        objectList.add(new VideoRequest("Curso React: 1", " Entendendo o JSX", "https://www.youtube.com/watch?v=9iKNxnFJY_Q&list=PLnDvRpP8BneyVA0SZ2okm-QBojomniQVO&index=3", 999));
        objectList.add(new VideoRequest("Curso React: 2", "Criando componentes no React", "https://www.youtube.com/watch?v=-wrsG0IGc-M&list=PLnDvRpP8BneyVA0SZ2okm-QBojomniQVO&index=4", 999));
        objectList.add(new VideoRequest("Curso React: 3", "Trabalhando com props", "https://www.youtube.com/watch?v=ZLtBdpwg8tI&list=PLnDvRpP8BneyVA0SZ2okm-QBojomniQVO&index=5", 999));

        String nomeArq = "courses-and-video.txt";
        int contaRegistroCorpo = 0;

        // Monta o registro de header
        String header = "00CURSO";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";
        // Grava o registro de header
        whriteInFile(header, nomeArq);

        for (Object obj : objectList) {

            if(obj instanceof CourseRequest) {
                String corpo;
                corpo = "02";
                corpo += String.format("%-40.40s", ((CourseRequest) obj).getTitle());
                corpo += String.format("%-255.255s", ((CourseRequest) obj).getDescription());
                corpo += String.format("%-255.255s", ((CourseRequest) obj).getImgPath());
                corpo += String.format("%-15.15s", ((CourseRequest) obj).getKnowledgeLevel());
                corpo += String.format("%-80.80s", ((CourseRequest) obj).getProductorName());
                corpo += String.format("%03d", ((CourseRequest) obj).getIdUser());
                corpo += String.format("%03d", ((CourseRequest) obj).getIdCategory());
                corpo += String.format("%03d", ((CourseRequest) obj).getIdTecnology());
                whriteInFile(corpo, nomeArq);
                contaRegistroCorpo++;
            }

            if(obj instanceof VideoRequest) {
                String corpo2;
                corpo2 = "03";
                corpo2 += String.format("%-60.60s", ((VideoRequest) obj).getDescription());
                corpo2 += String.format("%-255.255s", ((VideoRequest) obj).getDescription());
                corpo2 += String.format("%-255.255s", ((VideoRequest) obj).getUrl());
                whriteInFile(corpo2, nomeArq);
                contaRegistroCorpo++;
            }
        }


        // Monta e grava o registro de trailer
        String trailer = "01";
        trailer += String.format("%05d", contaRegistroCorpo);
        whriteInFile(trailer, nomeArq);

    }

    public QueueObj<Object> read(String arqName) {
        BufferedReader entry = null;
        String registry, registryType;

        // Header
        String arqType, dateTime, version;
        // trailer
        Integer quantityRegisters;
        // Corpo 2 (Course)
        String title, description, imgPath, knowledgeLevel, productorName;
        int idUser = 0;
        int idCategory, idTechnology;
        // Corpo 3 (Video)
        String videotitle, videodescription, url;
        int idUserVideo;

        //Return
        QueueObj<Object> lista = new QueueObj<>(100000);

        int countRegisters = 0;

        try {
            entry = new BufferedReader(new FileReader(arqName));
        } catch (IOException erro) {
            System.out.println("Erro na abertura do arquivo: " + erro);
        }
        try {
            registry = entry.readLine();

            while (registry != null) {
                registryType = registry.substring(0, 2);
                if (registryType.equals("00")) {

                    arqType = registry.substring(2, 7).trim();
                    System.out.println(arqType);
                    dateTime = registry.substring(7, 26).trim();
                    System.out.println(dateTime);
                    version = registry.substring(26, 28).trim();
                    System.out.println(version);

                } else if (registryType.equals("01")) {
                    quantityRegisters = Integer.parseInt(registry.substring(2, 7));
                    if (countRegisters == quantityRegisters) {
                        System.out.println("Tudo OK");
                    } else {
                        System.out.println("Diferente");
                    }

                } else if (registryType.equals("02")) {
                    title = registry.substring(02, 42).trim();
                    System.out.println("Titulo " + title);
                    description = registry.substring(42, 297).trim();
                    System.out.println("desc " + description);
                    imgPath = registry.substring(297, 552).trim();
                    System.out.println("img " + imgPath);
                    knowledgeLevel = registry.substring(552, 567).trim();
                    System.out.println("knolog " + knowledgeLevel);
                    productorName = registry.substring(567, 647).trim();
                    System.out.println("prodName " + productorName);
                    idUser = (int) Integer.valueOf(registry.substring(647, 650));
                    System.out.println("idUser " + idUser);
                    idCategory = (int) Integer.valueOf(registry.substring(650, 653));
                    System.out.println("idCate " + idCategory);
                    idTechnology = (int) Integer.valueOf(registry.substring(653, 656));
                    System.out.println("idTech " + idTechnology);

                    lista.insert(new CourseRequest(title, description, imgPath, KnowledgeLevelEnum.valueOf(knowledgeLevel), productorName, idUser, idCategory, idTechnology));

                    countRegisters++;

                } else if (registryType.equals("03")) {

                    videotitle = registry.substring(02, 62).trim();
                    System.out.println("VideoTitulo " + videotitle);
                    videodescription = registry.substring(62, 317).trim();
                    System.out.println("VideoDesc " + videodescription);
                    url = registry.substring(317, 572).trim();
                    System.out.println("VideoUrl " + url);

                    lista.insert(new VideoRequest(videotitle, videodescription, url, null));

                    countRegisters++;

                } else {
                    System.out.println("Tipo de registro inválido");
                }
                registry = entry.readLine();
            }
            entry.close();
            return lista;
        } catch (IOException erro) {
            System.out.println("Erro ao ler arquivo: " + erro);
            return null;
        }
    }
}
