package com.sams.samsapi.json_crud_utils;

import java.util.logging.Logger;
import java.util.HashMap;
import java.util.logging.Level;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.sams.samsapi.model.PaperChoices;

@Component
@DependsOn({"paperDetailsUtil"})
public class PaperChoicesUtil {
    private static final Logger LOG = Logger.getLogger(PaperChoicesUtil.class.getName());
    private static HashMap<Integer, PaperChoices> idVsPaperChoices;
    private static int nextPaperChoiceId = 1;
    
    public PaperChoicesUtil(){
        initialize();
    }

    private static void initialize(){
        idVsPaperChoices = PaperDetailsUtil.getIdVsPaperChoices();
        initializeData();
    }

    private static void initializeData(){
        for(Integer id : idVsPaperChoices.keySet()){
            nextPaperChoiceId = id + 1;
        }
    }

    private static Integer getNextPaperChoiceId(){
        return nextPaperChoiceId++;
    }

    private static void savePaperChoices() throws Exception{
        PaperDetailsUtil.setIdVsPaperChoices(idVsPaperChoices);
    }

    private static Boolean isSavePaperChoicesSuccessful(){
        try{
            savePaperChoices();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public static Boolean insertPaperChoice(Integer paperId, Integer pcmId){
        Integer id = getNextPaperChoiceId();

        if(paperId == null || pcmId == null){
            return false;
        }

        PaperChoices paperChoice = new PaperChoices(id, paperId, pcmId);
        idVsPaperChoices.put(id, paperChoice);
        return isSavePaperChoicesSuccessful();
    }

    public static Boolean updatePaperChoice(PaperChoices paperChoice){
        Integer id = paperChoice.getId();
        if(!idVsPaperChoices.containsKey(id)){
            return false;
        }
        idVsPaperChoices.put(id, paperChoice);
        return isSavePaperChoicesSuccessful();
    }

    public static Boolean deletePaperChoice(Integer id){
        if(!idVsPaperChoices.containsKey(id)){
            return false;
        }
        idVsPaperChoices.remove(id);
        return isSavePaperChoicesSuccessful();
    }

    public static HashMap<Integer,PaperChoices> getAllPaperChoices(){
        return idVsPaperChoices;
    }

    public static PaperChoices getPaperChoice(Integer id){
        return idVsPaperChoices.get(id);
    }
}
