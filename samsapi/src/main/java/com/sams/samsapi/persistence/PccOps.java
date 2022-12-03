package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.sams.samsapi.crud_utils.AssignedPapersUtil;
import com.sams.samsapi.crud_utils.PaperChoicesUtil;
import com.sams.samsapi.crud_utils.PaperPoolUtil;
import com.sams.samsapi.crud_utils.UserUtils;
import com.sams.samsapi.modelTemplates.PaperMapping;
import com.sams.samsapi.modelTemplates.ResearchPaper;
import com.sams.samsapi.modelTemplates.ReviewTemplate;
import com.sams.samsapi.modelTemplates.User;
import com.sams.samsapi.modelTemplates.User.USER_TYPE;
import com.sams.samsapi.util.CodeSmellFixer;

public class PccOps implements PccInterface {

    @Override
    public ArrayList<ResearchPaper> getAllSubmissions() {
        HashMap<Integer,ResearchPaper> idVsPaper = PaperPoolUtil.getAllPaperDetailsBasedOnLatestRevision(true);
        ArrayList<ResearchPaper> papers = new ArrayList<>();
        for(Integer paperId : idVsPaper.keySet()){
            papers.add(idVsPaper.get(paperId));
        }

        return papers;
    }

    @Override
    public ArrayList<ResearchPaper> getPaperPendingPCCAssignments() {
        ArrayList<ResearchPaper> paperDtls = getAllSubmissions();
        List<Integer> parsedPaperIds = new ArrayList<>();
        HashMap<Integer,ReviewTemplate> idVsAssignedPaperDtls =  AssignedPapersUtil.getAllAssignedPapers();
        for(Integer id : idVsAssignedPaperDtls.keySet()){
            ReviewTemplate assignedPaper = idVsAssignedPaperDtls.get(id);
            if(!parsedPaperIds.contains(assignedPaper.getPaperId())){
                HashMap<Integer, ResearchPaper> papers = PaperPoolUtil.getPaperDetailsBasedOnPaperId(assignedPaper.getPaperId());
                for(Integer paperPrimaryId : papers.keySet()){
                    if(paperDtls.contains(papers.get(paperPrimaryId))){
                        paperDtls.remove(papers.get(paperPrimaryId));
                    }
                }
                parsedPaperIds.add(assignedPaper.getPaperId());
            }
        }

        return paperDtls;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HashMap<String,Object>> getRatingCompletedPaperDetails() {
        ArrayList<HashMap<String,Object>> paperDetailsList = new ArrayList<>();
        HashMap<ResearchPaper, HashMap<String, Object>> paperAssignmentDtls = new HashMap<>();
        HashMap<Integer,ReviewTemplate> idVsAssignedPaperDtls =  AssignedPapersUtil.getAllAssignedPapers();
        for(Integer id : idVsAssignedPaperDtls.keySet()){
            ReviewTemplate assignedPaper = idVsAssignedPaperDtls.get(id);
            ResearchPaper paper = PaperPoolUtil.getLatestRevisedPaperDetailsBasedOnPaperId(assignedPaper.getPaperId());
            if(paper.getRating() == null){
                continue;
            }
            HashMap<String, Object> paperDtl = new HashMap<>();
            List<Integer> pcmIds = new ArrayList<>();
            List<Integer> rating = new ArrayList<>();
            if(paperAssignmentDtls.containsKey(paper)){
                paperDtl = paperAssignmentDtls.get(paper);
                pcmIds = (ArrayList<Integer>)paperDtl.get(CodeSmellFixer.SnakeCase.PCM_ID);
                rating = (ArrayList<Integer>)paperDtl.get(CodeSmellFixer.LowerCase.RATING);
            }
            pcmIds.add(assignedPaper.getPcmId());
            rating.add(assignedPaper.getRating());
            paperDtl.put(CodeSmellFixer.SnakeCase.PCM_ID, pcmIds);
            paperDtl.put(CodeSmellFixer.LowerCase.RATING, rating);
            paperAssignmentDtls.put(paper, paperDtl);
        }
        for(ResearchPaper paper : paperAssignmentDtls.keySet()){
            HashMap<String,Object> data = new HashMap<>();
            data.put(CodeSmellFixer.SnakeCase.PAPER_DETAILS, paper);
            
            HashMap<String, Object> paperDtl = paperAssignmentDtls.get(paper);
            ArrayList<HashMap<String,Object>> pcmDataList = new ArrayList<>();

            List<Integer> pcmIds = (ArrayList<Integer>)paperDtl.get(CodeSmellFixer.SnakeCase.PCM_ID);
            List<Integer> rating = (ArrayList<Integer>)paperDtl.get(CodeSmellFixer.LowerCase.RATING);

            for(int index = 0; index < pcmIds.size(); index++){
                HashMap<String,Object> pcmData = new HashMap<>();
                pcmData.put(CodeSmellFixer.SnakeCase.PCM_ID, pcmIds.get(index));
                pcmData.put(CodeSmellFixer.LowerCase.RATING, rating.get(index));
                pcmDataList.add(pcmData);
            }

            data.put(CodeSmellFixer.SnakeCase.PCM_DETAILS, pcmDataList);
            paperDetailsList.add(data);
        }
        return paperDetailsList;
    }

    @Override
    public Boolean assignPaperToPCM(Integer paperId, Integer pcmId) {
        HashMap<Integer,ReviewTemplate> idVsAssignedPaperDtls =  AssignedPapersUtil.getAssignedPaperBasedOnPaperId(paperId);
        Boolean isCreate = true;
        ReviewTemplate updatedReviewTemplate = null;
        if(idVsAssignedPaperDtls.size() != 0){
           for(Integer id : idVsAssignedPaperDtls.keySet()){
                if(Objects.equals(idVsAssignedPaperDtls.get(id).getPcmId(), pcmId)){
                    isCreate = false;
                    updatedReviewTemplate = new ReviewTemplate(id, paperId, pcmId, null, null);
                }
           }
        }
        if(idVsAssignedPaperDtls.size() >= AssignedPapersUtil.getAssignPaperLimit()){
            return false;
        }
        Boolean status = true;
        status = Boolean.TRUE.equals(isCreate) ? AssignedPapersUtil.insertAssignedPaper(paperId, pcmId, null, null) : AssignedPapersUtil.updateAssignedPaper(updatedReviewTemplate);
        if(Boolean.TRUE.equals(status)){
            Integer id = Boolean.TRUE.equals(isCreate) ? AssignedPapersUtil.getAssignedPaperBasedOnPaperIdAndPcmId(paperId, pcmId).getId() : updatedReviewTemplate.getId();
            new NotificationsOps().insertPcmAssignmentNotification(id, paperId, pcmId);
        }
        return status;
    }

    @Override
    public HashMap<Integer, ReviewTemplate> viewPCMReviews(Integer paperId) {
        HashMap<Integer, ReviewTemplate> assignedPaperDtls = AssignedPapersUtil.getAssignedPaperBasedOnPaperId(paperId);
        HashMap<Integer, ReviewTemplate> pcmIdVsAssignedPapers = new HashMap<>();
        for(Integer id : assignedPaperDtls.keySet()){
            pcmIdVsAssignedPapers.put(assignedPaperDtls.get(id).getPcmId(), assignedPaperDtls.get(id));
        }
        return pcmIdVsAssignedPapers;
    }

    @Override
    public ArrayList<ResearchPaper> getAllReviewCompletedPapersDetails() {
        HashMap<Integer, ReviewTemplate> assignedPaperDtls = AssignedPapersUtil.getAllAssignedPapers();
        ArrayList<ResearchPaper> papers = new ArrayList<>();
        for(Integer id : assignedPaperDtls.keySet()){
            ResearchPaper paper = PaperPoolUtil.getLatestRevisedPaperDetailsBasedOnPaperId(assignedPaperDtls.get(id).getPaperId());
            if(!papers.contains(paper) && assignedPaperDtls.get(id).getRating() == null){
                papers.add(paper);
            }
        }
        return papers;
    }

    @Override
    public void ratePaper(Integer paperId, Integer rating) {
        ArrayList<ResearchPaper> paperDtls = getAllSubmissions();
        Integer submitterId = 0;
        Boolean status = false;
        for(ResearchPaper researchPaper : paperDtls){
            if(Objects.equals(researchPaper.getPaperId(), paperId)){
                researchPaper.setRating(rating);
                submitterId = researchPaper.getSubmitterId();
                status = PaperPoolUtil.updatePaperDetails(researchPaper);
                break;
            }
        }
        if(Boolean.TRUE.equals(status)){
            new NotificationsOps().insertFinalRatingNotification(paperId, submitterId);
        }
    }

    @Override
    public HashMap<Integer, ArrayList<ResearchPaper>> getPCMChoices() {
        HashMap<Integer,ResearchPaper> paperIdVsPaper = PaperPoolUtil.getAllPaperDetailsBasedOnLatestRevision(true);
        HashMap<Integer,PaperMapping> idVsPaperChoicesDtls =  PaperChoicesUtil.getAllPaperChoices();
        HashMap<Integer, ArrayList<ResearchPaper>> pcmChoices = new HashMap<>();
        for(Integer id : idVsPaperChoicesDtls.keySet()){
            PaperMapping paperChoice = idVsPaperChoicesDtls.get(id);
            ResearchPaper paper = paperIdVsPaper.get(paperChoice.getPaperId());
            ArrayList<ResearchPaper> paperList = new ArrayList<>();
            if(pcmChoices.containsKey(paperChoice.getPcmId())){
                paperList = pcmChoices.get(paperChoice.getPcmId());
            }
            paperList.add(paper);
            pcmChoices.put(paperChoice.getPcmId(), paperList);
        }
        return pcmChoices;
    }

    void notifyPCMforReview(Integer pcmId){

    }
    void notifyPCMsforChoiceMaking(){

    }
    
    @Override
    public ArrayList<HashMap<String,Object>> getAvailablePCMs(){
        HashMap<Integer,User> pcmDtls = UserUtils.getAllUserDetailsBasedOnType(USER_TYPE.PCM);
        ArrayList<HashMap<String,Object>> pcmData = new ArrayList<>();
        for(Integer id : pcmDtls.keySet()){
            HashMap<String,Object> data = new HashMap<>();
            data.put(CodeSmellFixer.LowerCase.ID, id);
            data.put(CodeSmellFixer.LowerCase.NAME, pcmDtls.get(id).getName());
            pcmData.add(data);
        }
        return pcmData;
    }

    public ArrayList<Integer> getAvailablePCMIds(){
        HashMap<Integer,User> pcmDtls = UserUtils.getAllUserDetailsBasedOnType(USER_TYPE.PCM);
        return new ArrayList<>(pcmDtls.keySet());
    }

    public ArrayList<Integer> getAvailablePCCIds(){
        HashMap<Integer,User> pccDtls = UserUtils.getAllUserDetailsBasedOnType(USER_TYPE.PCC);
        return new ArrayList<>(pccDtls.keySet());
    }

}
