package com.sams.samsapi.json_crud_utils;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.sams.samsapi.model.ReviewTemplate;
import com.sams.samsapi.model.ReviewTemplate.Reviews;

@Component
public class AssignedPapersUtil {
    private static final Logger LOG = Logger.getLogger(AssignedPapersUtil.class.getName());
    private HashMap<Integer, ReviewTemplate> idVsAssignedPapers;
    private int nextAssignedPaperId = 1;
    private PaperDetailsUtil paperDetailsUtil;
    
    public AssignedPapersUtil(PaperDetailsUtil paperDetailsUtil){
        this.paperDetailsUtil = paperDetailsUtil;
        this.idVsAssignedPapers = paperDetailsUtil.getIdVsAssignedPapers();
        initialize();
    }

    private void initialize(){
        for(Integer id : idVsAssignedPapers.keySet()){
            nextAssignedPaperId = id + 1;
        }
    }

    private Integer getNextAssignedPaperId(){
        return nextAssignedPaperId++;
    }

    private void saveAssignedPapers() throws Exception{
        paperDetailsUtil.setIdVsAssignedPapers(idVsAssignedPapers);
    }

    private Boolean isSaveAssignedPapersSuccessful(){
        try{
            saveAssignedPapers();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public Boolean insertAssignedPaper(Integer paperId, Integer pcmId, Reviews[] reviews, Integer rating){
        Integer id = getNextAssignedPaperId();

        if(paperId == null || pcmId == null || rating == null){
            return false;
        }

        if(reviews == null){
            reviews = new Reviews[0];
        }

        ReviewTemplate assignedPaper = new ReviewTemplate(id, paperId, pcmId, reviews, rating);
        idVsAssignedPapers.put(id, assignedPaper);
        return isSaveAssignedPapersSuccessful();
    }

    public Boolean updateAssignedPaper(ReviewTemplate assignedPaper){
        Integer id = assignedPaper.getId();
        if(!idVsAssignedPapers.containsKey(id)){
            return false;
        }
        idVsAssignedPapers.put(id, assignedPaper);
        return isSaveAssignedPapersSuccessful();
    }

    public Boolean deleteAssignedPaper(Integer id){
        if(!idVsAssignedPapers.containsKey(id)){
            return false;
        }
        idVsAssignedPapers.remove(id);
        return isSaveAssignedPapersSuccessful();
    }

    public HashMap<Integer,ReviewTemplate> getAllAssignedPapers(){
        return idVsAssignedPapers;
    }

    public ReviewTemplate getAssignedPaper(Integer id){
        return idVsAssignedPapers.get(id);
    }
}
