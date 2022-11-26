package com.sams.samsapi.json_crud_utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.sams.samsapi.model.ResearchPaper;

@Component
public class PapersUtil {
    private static final Logger LOG = Logger.getLogger(PapersUtil.class.getName());
    private HashMap<Integer, ResearchPaper> idVsPaperDetails;
    private int nextPaperId = 1;
    private PaperDetailsUtil paperDetailsUtil;
    
    public PapersUtil(PaperDetailsUtil paperDetailsUtil){
        this.paperDetailsUtil = paperDetailsUtil;
        this.idVsPaperDetails = paperDetailsUtil.getIdVsPaperDetails();
        initialize();
    }

    private void initialize(){
        for(Integer id : idVsPaperDetails.keySet()){
            nextPaperId = id + 1;
        }
    }

    private Integer getNextPaperId(){
        return nextPaperId++;
    }

    private void savePapers() throws Exception{
        paperDetailsUtil.setIdVsPaperDetails(idVsPaperDetails);
    }

    private Boolean isSavePapersSuccessful(){
        try{
            savePapers();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public Boolean insertPaperDetails(String title, Integer submitterId, List<String> authors, String contact, String fileName, String fileExtension, Integer paperId, Integer revisionNo){
        Integer id = getNextPaperId();

        if(title == null || submitterId == null || contact == null || fileName == null || fileExtension == null || paperId == null || revisionNo == null){
            return false;
        }

        if(authors == null){
            authors = new ArrayList<>();
        }

        ResearchPaper paper = new ResearchPaper(id, title, submitterId, authors, contact, fileName, fileExtension, paperId, revisionNo, revisionNo);
        idVsPaperDetails.put(id, paper);
        return isSavePapersSuccessful();
    }

    public Boolean updatePaperDetails(ResearchPaper paper){
        Integer id = paper.getId();
        if(!idVsPaperDetails.containsKey(id)){
            return false;
        }
        idVsPaperDetails.put(id, paper);
        return isSavePapersSuccessful();
    }

    public Boolean deletePaperDetails(Integer id){
        if(!idVsPaperDetails.containsKey(id)){
            return false;
        }
        idVsPaperDetails.remove(id);
        return isSavePapersSuccessful();
    }

    public HashMap<Integer,ResearchPaper> getAllPapers(){
        return idVsPaperDetails;
    }

    public ResearchPaper getPaperDetails(Integer id){
        return idVsPaperDetails.get(id);
    }
}
