package com.sams.samsapi.json_crud_utils;

import java.util.logging.Logger;
import java.util.HashMap;
import java.util.logging.Level;

import org.springframework.stereotype.Component;

import com.sams.samsapi.model.PaperChoices;

@Component
public class PaperChoicesUtil {
    private static final Logger LOG = Logger.getLogger(PaperChoicesUtil.class.getName());
    private HashMap<Integer, PaperChoices> idVsPaperChoices;
    private int nextPaperChoiceId = 1;
    private PaperDetailsUtil paperDetailsUtil;
    
    public PaperChoicesUtil(PaperDetailsUtil paperDetailsUtil){
        this.paperDetailsUtil = paperDetailsUtil;
        this.idVsPaperChoices = paperDetailsUtil.getIdVsPaperChoices();
        initialize();
    }

    private void initialize(){
        for(Integer id : idVsPaperChoices.keySet()){
            nextPaperChoiceId = id + 1;
        }
    }

    private Integer getNextPaperChoiceId(){
        return nextPaperChoiceId++;
    }

    private void savePaperChoices() throws Exception{
        paperDetailsUtil.setIdVsPaperChoices(idVsPaperChoices);
    }

    private Boolean isSavePaperChoicesSuccessful(){
        try{
            savePaperChoices();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public Boolean insertPaperChoice(Integer paperId, Integer pcmId){
        Integer id = getNextPaperChoiceId();

        if(paperId == null || pcmId == null){
            return false;
        }

        PaperChoices paperChoice = new PaperChoices(id, paperId, pcmId);
        idVsPaperChoices.put(id, paperChoice);
        return isSavePaperChoicesSuccessful();
    }

    public Boolean updatePaperChoice(PaperChoices paperChoice){
        Integer id = paperChoice.getId();
        if(!idVsPaperChoices.containsKey(id)){
            return false;
        }
        idVsPaperChoices.put(id, paperChoice);
        return isSavePaperChoicesSuccessful();
    }

    public Boolean deletePaperChoice(Integer id){
        if(!idVsPaperChoices.containsKey(id)){
            return false;
        }
        idVsPaperChoices.remove(id);
        return isSavePaperChoicesSuccessful();
    }

    public HashMap<Integer,PaperChoices> getAllPaperChoices(){
        return idVsPaperChoices;
    }

    public PaperChoices getPaperChoice(Integer id){
        return idVsPaperChoices.get(id);
    }
}
