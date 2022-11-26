package com.sams.samsapi.json_crud_utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sams.samsapi.model.PaperChoices;
import com.sams.samsapi.model.PaperDetails;
import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.ReviewTemplate;

@Component
public class PaperDetailsUtil {
    private ObjectMapper objectMapper;
    private String papersDetailsFileName;
    private PaperDetails paperDtls;
    private HashMap<Integer, ResearchPaper> idVsPaperDetails;
    private HashMap<Integer, ReviewTemplate> idVsAssignedPapers;
    private HashMap<Integer, PaperChoices> idVsPaperChoices;

    public PaperDetailsUtil(@Value("${paperDetails.file}") String papersDetailsFileName, ObjectMapper objectMapper) throws Exception{
        this.objectMapper = objectMapper;
        this.papersDetailsFileName = papersDetailsFileName;
        paperDtls = objectMapper.readValue(new File(papersDetailsFileName), PaperDetails.class);
        initializeData();
    }

    private void initializeData(){
        idVsPaperDetails = new HashMap<>();
        idVsAssignedPapers = new HashMap<>();
        idVsPaperChoices = new HashMap<>();

        for(ResearchPaper paper : paperDtls.getPapers()){
            idVsPaperDetails.put(paper.getId(), paper);
        }

        for(ReviewTemplate assignedPapers : paperDtls.getAssignedPapers()){
            idVsAssignedPapers.put(assignedPapers.getId(), assignedPapers);
        }

        for(PaperChoices paperChoices : paperDtls.getPaperChoices()){
            idVsPaperChoices.put(paperChoices.getId(), paperChoices);
        }
    }

    private void savePaperDetails() throws Exception{
        List<ResearchPaper> paperDetails = new ArrayList<>();
        List<ReviewTemplate> assignedPapers = new ArrayList<>();
        List<PaperChoices> paperChoices = new ArrayList<>();
        for(Integer id : idVsPaperDetails.keySet()){
            paperDetails.add(idVsPaperDetails.get(id));
        }
        for(Integer id : idVsAssignedPapers.keySet()){
            assignedPapers.add(idVsAssignedPapers.get(id));
        }
        for(Integer id : idVsPaperChoices.keySet()){
            paperChoices.add(idVsPaperChoices.get(id));
        }

        ResearchPaper[] paperArray = new ResearchPaper[paperDetails.size()];
        paperDetails.toArray(paperArray);

        ReviewTemplate[] assignedPapersArray = new ReviewTemplate[assignedPapers.size()];
        assignedPapers.toArray(assignedPapersArray);

        PaperChoices[] paperChoicesArray = new PaperChoices[paperChoices.size()];
        paperChoices.toArray(paperChoicesArray);

        paperDtls.setAssignedPapers(assignedPapersArray);
        paperDtls.setPaperChoices(paperChoicesArray);
        paperDtls.setPapers(paperArray);

        objectMapper.writeValue(new File(papersDetailsFileName), paperDtls);
    }

    public HashMap<Integer, ResearchPaper> getIdVsPaperDetails() {
        return idVsPaperDetails;
    }

    public void setIdVsPaperDetails(HashMap<Integer, ResearchPaper> idVsPaperDetails) throws Exception{
        this.idVsPaperDetails = idVsPaperDetails;
        savePaperDetails();
    }

    public HashMap<Integer, ReviewTemplate> getIdVsAssignedPapers() {
        return idVsAssignedPapers;
    }

    public void setIdVsAssignedPapers(HashMap<Integer, ReviewTemplate> idVsAssignedPapers) throws Exception {
        this.idVsAssignedPapers = idVsAssignedPapers;
        savePaperDetails();
    }

    public HashMap<Integer, PaperChoices> getIdVsPaperChoices() {
        return idVsPaperChoices;
    }

    public void setIdVsPaperChoices(HashMap<Integer, PaperChoices> idVsPaperChoices) throws Exception {
        this.idVsPaperChoices = idVsPaperChoices;
        savePaperDetails();
    }
}
