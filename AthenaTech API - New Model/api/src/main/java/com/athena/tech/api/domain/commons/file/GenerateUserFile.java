package com.athena.tech.api.domain.commons.file;

import com.athena.tech.api.application.web.response.course.CourseResponse;
import com.athena.tech.api.domain.commons.list.ListObj;
import com.athena.tech.api.domain.entity.user.AthenaUserReport;
import com.athena.tech.api.domain.repositories.CourseRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GenerateUserFile {

    public void gravaArquivoCsv(ListObj<CourseResponse> list, String arqName) {
        FileWriter arq = null;
        Formatter exit = null;
        arqName += ".csv";
        Boolean catchErro = false;

        try {
            arq = new FileWriter(arqName);
            exit = new Formatter(arq);
        }
        catch (IOException erro) {
            System.out.println("\nErro ao abrir o arquivo");
            System.exit(1);
        }

        try {
            for (int i=0; i < list.getLengt(); i++) {
                CourseResponse courseResponse = list.getObj(i);
                exit.format("%s;%s;%s;%s;%s;%s;%s;%s\n",
                        courseResponse.getIdCourse(),
                        courseResponse.getName(),
                        courseResponse.getDescription(),
                        courseResponse.getCreatedAt().toString(),
                        courseResponse.getUpdatedAt().toString(),
                        courseResponse.getProductorName(),
                        courseResponse.getLikes().toString(),
                        courseResponse.getViews().toString()
                );
            }
        }
        catch (FormatterClosedException erro) {
            System.out.println("\nErro ao gravar no arquivo");
            catchErro = true;
        }
        finally {
            exit.close();
            try {
                arq.close();
            }
            catch (IOException erro) {
                System.out.println("\nErro ao fechar o arquivo");
                catchErro = true;
            }
            if (catchErro) {
                System.exit(1);
            }
        }
    }

    public String exibeArquivoCsv (String arqName) {
        FileReader arq = null;
        Scanner entry = null;
        arqName += ".csv";
        Boolean catchErro = false;
        String arqContent = "";

        try {
            arq = new FileReader(arqName);
            entry = new Scanner(arq).useDelimiter(";|\\n");
        }
        catch (FileNotFoundException erro) {
            System.out.println("\nArquivo não encontrado");
            System.exit(1);
        }

        try {
            arqContent += String.format("%s;%s;%s;%s;%s;%s;%s;%s\n",
                    "ID", "NOME DO CURSO",  "DESCRIÇÃO",  "CRIADO", "ATUALIZADO", "NOME DO PRODUTOR", "LIKES", "VIEWS");

            while (entry.hasNext()) {
                Integer idCourse = entry.nextInt();
                String courseName = entry.next();
                String description = entry.next();
                String createdAt = entry.next();
                String updatedAt = entry.next();
                String productorName = entry.next();
                Integer likes = entry.nextInt();
                Integer views = entry.nextInt();

                arqContent += String.format("%d;%s;%s;%s;%s;%s;%d;%d\n",
                        idCourse,
                        courseName,
                        description,
                        createdAt,
                        updatedAt,
                        productorName,
                        likes,
                        views
                );
            }
        }
        catch (NoSuchElementException erro) {
            System.out.println("\nArquivo com problemas");
            catchErro = true;
        }
        catch (IllegalStateException erro) {
            System.out.println("\nErro na leitura do arquivo");
            catchErro = true;
        }
        finally {
            entry.close();
            try {
                arq.close();
            }
            catch (IOException erro) {
                System.out.println("\nErro ao fechar o arquivo");
                catchErro = true;
            }
            if (catchErro) {
                System.exit(1);
            }
        }
        return arqContent;
    }
}
