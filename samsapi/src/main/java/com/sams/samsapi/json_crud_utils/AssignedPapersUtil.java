package com.sams.samsapi.json_crud_utils;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.sams.samsapi.model.ReviewTemplate;
import com.sams.samsapi.model.ReviewTemplate.Reviews;

@Component
@DependsOn({"paperDetailsUtil"})
public class AssignedPapersUtil {
    private static final Logger LOG = Logger.getLogger(AssignedPapersUtil.class.getName());
    private static HashMap<Integer, ReviewTemplate> idVsAssignedPapers;
    private static int nextAssignedPaperId = 1;
    
    public AssignedPapersUtil(){
        initialize();
    }

    private static void initialize(){
        idVsAssignedPapers = PaperDetailsUtil.getIdVsAssignedPapers();
        initializeData();
    }

    private static void initializeData(){
        for(Integer id : idVsAssignedPapers.keySet()){
            nextAssignedPaperId = id + 1;
        }
    }

    private static Integer getNextAssignedPaperId(){
        return nextAssignedPaperId++;
    }

    private static void saveAssignedPapers() throws Exception{
        PaperDetailsUtil.setIdVsAssignedPapers(idVsAssignedPapers);
    }

    private static Boolean isSaveAssignedPapersSuccessful(){
        try{
            saveAssignedPapers();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public static Boolean insertAssignedPaper(Integer paperId, Integer pcmId, Reviews[] reviews, Integer rating){
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

    public static Boolean updateAssignedPaper(ReviewTemplate assignedPaper){
        Integer id = assignedPaper.getId();
        if(!idVsAssignedPapers.containsKey(id)){
            return false;
        }
        idVsAssignedPapers.put(id, assignedPaper);
        return isSaveAssignedPapersSuccessful();
    }

    public static Boolean deleteAssignedPaper(Integer id){
        if(!idVsAssignedPapers.containsKey(id)){
            return false;
        }
        idVsAssignedPapers.remove(id);
        return isSaveAssignedPapersSuccessful();
    }

    public static HashMap<Integer,ReviewTemplate> getAllAssignedPapers(){
        return idVsAssignedPapers;
    }

    public static ReviewTemplate getAssignedPaper(Integer id){
        return idVsAssignedPapers.get(id);
    }
}
