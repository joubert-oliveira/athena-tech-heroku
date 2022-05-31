package com.athena.tech.api.domain.commons.file;

import com.athena.tech.api.domain.entity.CourseTxtResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GenerateTxtFile {

    public String createArqAndContent(List<CourseTxtResponse> list) {
        int contaRegistroCorpo = 0;

        String fileInTxt = "00CURSO";
        fileInTxt += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        fileInTxt += "01";

        fileInTxt += "\n";
        for (CourseTxtResponse listBody : list){
            fileInTxt += String.format("%-40.40s", listBody.getTitle());

            fileInTxt += String.format("%-255.255s", listBody.getDescription());

            fileInTxt += String.format("%-255.255s", listBody.getImgPath());

            fileInTxt += String.format("%-15.15s", listBody.getKnowledgeLevel());

            fileInTxt += String.format("%-80.80s", listBody.getProductorName());

            fileInTxt += String.format("%010d", listBody.getLikes());

            fileInTxt += String.format("%010d", listBody.getViews());

            fileInTxt += String.format("%40s", listBody.getCategory());

            fileInTxt += String.format("%20s", listBody.getTecnology());

            fileInTxt += String.format("%05d", listBody.getIdUser());
            contaRegistroCorpo++;
            fileInTxt += "\n";
        }

        fileInTxt += "01";
        fileInTxt += String.format("%05d", contaRegistroCorpo);

        return fileInTxt;
    }
}
