package com.sams.samsapi.crud_utils;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.sams.samsapi.modelTemplates.PaperMapping;

@Component
@DependsOn({"paperDetailsUtil"})
public class PaperChoicesUtil {
    private static final Logger LOG = Logger.getLogger(PaperChoicesUtil.class.getName());
    private static HashMap<Integer, PaperMapping> idVsPaperChoices;
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

        PaperMapping paperChoice = new PaperMapping(id, paperId, pcmId);
        idVsPaperChoices.put(id, paperChoice);
        return isSavePaperChoicesSuccessful();
    }

    public static Boolean updatePaperChoice(PaperMapping paperChoice){
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

    public static HashMap<Integer,PaperMapping> getAllPaperChoices(){
        return idVsPaperChoices;
    }

    public static PaperMapping getPaperChoice(Integer id){
        return idVsPaperChoices.get(id);
    }

    public static ArrayList<PaperMapping> getPaperChoiceBasedOnType(Integer id, Boolean isBasedOnPaperId){
        ArrayList<PaperMapping> paperMappingList = new ArrayList<>();
        for(Integer primaryId : idVsPaperChoices.keySet()){
            PaperMapping paperMapping = idVsPaperChoices.get(primaryId);
            if( (isBasedOnPaperId && paperMapping.getPaperId().equals(id)) || (!isBasedOnPaperId && paperMapping.getPcmId().equals(id))){
                paperMappingList.add(paperMapping);
            }
        }
        return paperMappingList;
    }

    public static Boolean isPaperChoicePresent(Integer pcmId, Integer paperId){
        for(Integer id : idVsPaperChoices.keySet()){
            if(Objects.equals(idVsPaperChoices.get(id).getPcmId(), pcmId) && Objects.equals(idVsPaperChoices.get(id).getPaperId(), paperId)){
                return true;
            }
        }
        return false;
    }
}
