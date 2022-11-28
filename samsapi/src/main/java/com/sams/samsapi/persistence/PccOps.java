package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.sams.samsapi.json_crud_utils.AssignedPapersUtil;
import com.sams.samsapi.json_crud_utils.PaperChoicesUtil;
import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.json_crud_utils.UserUtils;
import com.sams.samsapi.model.PaperChoices;
import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.ReviewTemplate;
import com.sams.samsapi.model.User;
import com.sams.samsapi.model.User.USER_TYPE;

public class PccOps implements PccInterface {

    @Override
    public ArrayList<ResearchPaper> getAllSubmissions() {
        HashMap<Integer,ResearchPaper> idVsPaper = PapersUtil.getAllPaperDetailsBasedOnLatestRevision(true);
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
                HashMap<Integer, ResearchPaper> papers = PapersUtil.getPaperDetailsBasedOnPaperId(assignedPaper.getPaperId());
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

    @Override
    public HashMap<Integer, HashMap<Integer, Integer>> getPaperAssignmentDetails() {
        HashMap<Integer, HashMap<Integer, Integer>> paperAssignmentDtls = new HashMap<>();
        HashMap<Integer,ReviewTemplate> idVsAssignedPaperDtls =  AssignedPapersUtil.getAllAssignedPapers();
        for(Integer id : idVsAssignedPaperDtls.keySet()){
            ReviewTemplate assignedPaper = idVsAssignedPaperDtls.get(id);
            HashMap<Integer, Integer> paperDtl = new HashMap<>();
            if(paperAssignmentDtls.containsKey(assignedPaper.getPaperId())){
                paperDtl = paperAssignmentDtls.get(assignedPaper.getPaperId());
            }
            paperDtl.put(assignedPaper.getPcmId(), assignedPaper.getRating());
            paperAssignmentDtls.put(assignedPaper.getPaperId(), paperDtl);
        }
        return paperAssignmentDtls;
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
        status = Boolean.TRUE.equals(isCreate) ? AssignedPapersUtil.insertAssignedPaper(paperId, pcmId, null, pcmId) : AssignedPapersUtil.updateAssignedPaper(updatedReviewTemplate);
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
    public void ratePaper(Integer paperId, Integer rating) {
        ArrayList<ResearchPaper> paperDtls = getAllSubmissions();
        Integer submitterId = 0;
        Boolean status = false;
        for(ResearchPaper researchPaper : paperDtls){
            if(Objects.equals(researchPaper.getPaperId(), paperId)){
                researchPaper.setRating(rating);
                submitterId = researchPaper.getSubmitterId();
                status = PapersUtil.updatePaperDetails(researchPaper);
                break;
            }
        }
        if(Boolean.TRUE.equals(status)){
            new NotificationsOps().insertFinalRatingNotification(paperId, submitterId);
        }
    }

    @Override
    public HashMap<Integer, ArrayList<ResearchPaper>> getPCMChoices() {
        HashMap<Integer,ResearchPaper> paperIdVsPaper = PapersUtil.getAllPaperDetailsBasedOnLatestRevision(true);
        HashMap<Integer,PaperChoices> idVsPaperChoicesDtls =  PaperChoicesUtil.getAllPaperChoices();
        HashMap<Integer, ArrayList<ResearchPaper>> pcmChoices = new HashMap<>();
        for(Integer id : idVsPaperChoicesDtls.keySet()){
            PaperChoices paperChoice = idVsPaperChoicesDtls.get(id);
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
    public ArrayList<Integer> getAvailablePCMs(){
        HashMap<Integer,User> pcmDtls = UserUtils.getAllUserDetailsBasedOnType(USER_TYPE.PCM);
        return new ArrayList<>(pcmDtls.keySet());
    }

}
