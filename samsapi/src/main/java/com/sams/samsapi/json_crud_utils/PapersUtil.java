package com.sams.samsapi.json_crud_utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.sams.samsapi.model.ResearchPaper;

@Component
@DependsOn({ "paperDetailsUtil" })
public class PapersUtil {
    private static final Logger LOG = Logger.getLogger(PapersUtil.class.getName());
    private static HashMap<Integer, ResearchPaper> idVsPaperDetails;
    private static int nextId = 1;
    private static int nextPaperId = 1;

    public PapersUtil() {
        initialize();
    }

    private static void initialize() {
        idVsPaperDetails = PaperDetailsUtil.getIdVsPaperDetails();
        initializeData();
    }

    private static void initializeData() {
        for (Integer id : idVsPaperDetails.keySet()) {
            nextId = id + 1;
            ResearchPaper paper = idVsPaperDetails.get(id);
            if (nextPaperId <= paper.getPaperId()) {
                nextPaperId = paper.getPaperId() + 1;
            }
        }
    }

    private static Integer getNextId() {
        return nextId++;
    }

    private static void savePapers() throws Exception {
        PaperDetailsUtil.setIdVsPaperDetails(idVsPaperDetails);
    }

    private static Boolean isSavePapersSuccessful() {
        try {
            savePapers();
            return true;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public static Boolean insertPaperDetails(String title, Integer submitterId, List<String> authors, String contact,
            String fileName, String fileExtension, Integer paperId, Integer revisionNo) {
        Integer id = getNextId();

        if (title == null || submitterId == null || contact == null || fileName == null || fileExtension == null
                || paperId == null || revisionNo == null) {
            return false;
        }

        if (authors == null) {
            authors = new ArrayList<>();
        }

        ResearchPaper paper = new ResearchPaper(id, title, submitterId, authors, contact, fileName, fileExtension,
                paperId, revisionNo, -1);
        idVsPaperDetails.put(id, paper);
        return isSavePapersSuccessful();
    }

    public static Boolean updatePaperDetails(ResearchPaper paper) {
        Integer id = paper.getId();
        if (!idVsPaperDetails.containsKey(id)) {
            return false;
        }
        idVsPaperDetails.put(id, paper);
        return isSavePapersSuccessful();
    }

    public static Boolean deletePaperDetails(Integer id) {
        if (!idVsPaperDetails.containsKey(id)) {
            return false;
        }
        idVsPaperDetails.remove(id);
        return isSavePapersSuccessful();
    }

    public static HashMap<Integer, ResearchPaper> getAllPapers() {
        return idVsPaperDetails;
    }

    public static ResearchPaper getPaperDetails(Integer id) {
        return idVsPaperDetails.get(id);
    }

    public static Integer getNextPaperId() {
        return nextPaperId++;
    }

    public static HashMap<Integer, ResearchPaper> getPaperDetailsBasedOnPaperId(Integer paperId) {
        HashMap<Integer, ResearchPaper> paperDtls = new HashMap<>();
        for (Integer id : idVsPaperDetails.keySet()) {
            if (Objects.equals(idVsPaperDetails.get(id).getPaperId(), paperId)) {
                paperDtls.put(id, idVsPaperDetails.get(id));
            }
        }
        return paperDtls;
    }

    public static ResearchPaper getLatestRevisedPaperDetailsBasedOnPaperId(Integer paperId){
        return getAllPaperDetailsBasedOnLatestRevision(true).get(paperId);
    }

    public static HashMap<Integer, ResearchPaper> getAllPaperDetailsBasedOnLatestRevision(Boolean isPaperIdPrimary){
        HashMap<Integer,ResearchPaper> idVsPaperDtls =  getAllPapers();
        HashMap<Integer,ResearchPaper> paperIdVsPaper = new HashMap<>();
        HashMap<Integer,ResearchPaper> idVsPaper = new HashMap<>();
        for(Integer id : idVsPaperDtls.keySet()){
            ResearchPaper paper = idVsPaperDtls.get(id);
            Integer paperId = paper.getPaperId();
            if(paperIdVsPaper.containsKey(paperId)){
                ResearchPaper oldPaper = paperIdVsPaper.get(paperId);
                if(oldPaper.getRevisionNo() > paper.getRevisionNo()){
                    paper = oldPaper;
                }
            }
            paperIdVsPaper.put(paperId, paper);
        }
        
        for(Integer paperId : paperIdVsPaper.keySet()){
            idVsPaper.put(paperIdVsPaper.get(paperId).getId(), paperIdVsPaper.get(paperId));
        }
        return Boolean.TRUE.equals(isPaperIdPrimary) ? paperIdVsPaper : idVsPaper;
    }
}
