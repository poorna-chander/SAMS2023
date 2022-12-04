package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.sams.samsapi.crud_utils.AssignedPapersUtil;
import com.sams.samsapi.crud_utils.PaperChoicesUtil;
import com.sams.samsapi.crud_utils.PaperPoolUtil;
import com.sams.samsapi.crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.modelTemplates.ResearchPaper;
import com.sams.samsapi.modelTemplates.ReviewTemplate;
import com.sams.samsapi.modelTemplates.ReviewTemplate.Reviews;
import com.sams.samsapi.util.CodeSmellFixer;

public class PcmOps implements PcmInterface {

    @Override
    public Boolean submitPaperChoices(ArrayList<Integer> paperIds, Integer pcmId) {
        for (Integer paperId : paperIds) {
            if (Boolean.TRUE.equals(PaperChoicesUtil.isPaperChoicePresent(pcmId, paperId))) {
                return false;
            }
        }
        for (Integer paperId : paperIds) {
            PaperChoicesUtil.insertPaperChoice(paperId, pcmId);
        }
        return true;
    }

    @Override
    public HashMap<Integer, HashMap<String, String>> getMetaAvailablePaperDetails(Integer pcmId) {
        HashMap<Integer, HashMap<String, String>> metaData = new HashMap<>();
        HashMap<Integer, ResearchPaper> paperIdVsPaper = PaperPoolUtil.getAllPaperDetailsBasedOnLatestRevision(true);
        for(Integer paperId : paperIdVsPaper.keySet()){

            HashMap<String,String> data = new HashMap<>();
            data.put(CodeSmellFixer.LowerCase.TITLE, paperIdVsPaper.get(paperId).getTitle());

            HashMap<Integer,ReviewTemplate> idVsAssignedPaperDtls =  AssignedPapersUtil.getAssignedPaperBasedOnPaperId(paperId);
            if(idVsAssignedPaperDtls.size() < AssignedPapersUtil.getAssignPaperLimit() && !Objects.equals(paperIdVsPaper.get(paperId).getSubmitterId(), pcmId)){
                metaData.put(paperId, data);
            }
        }
        return metaData;
    }

    @Override
    public Boolean submitReview(Integer paperId, ReviewTemplate reviewTemplate) {
        if( AssignedPapersUtil.getAssignedPaperBasedOnPaperIdAndPcmId(paperId, reviewTemplate.getPcmId()) != null ){
            AssignedPapersUtil.updateAssignedPaper(reviewTemplate);
        }
        return false;
    }

    @Override
    public Reviews[] getReviewData(HashMap<Integer, String> reviewData) {
        Reviews[] review;
        List<Reviews> reviewList = new ArrayList<>();
        for(Integer id : reviewData.keySet()){
            if(Boolean.TRUE.equals(ReviewQuestionnaireUtil.isGivenReviewQuestionnaireValid(id))){
                reviewList.add(new Reviews(id, reviewData.get(id)));
            }else{
                return null;
            }
        }
        review = new Reviews[reviewList.size()];
        reviewList.toArray(review);
        return review;
    }

    @Override
    public ReviewTemplate getReviewTemplate(Integer pcmId, Integer paperId) {
        return AssignedPapersUtil.getAssignedPaperBasedOnPaperIdAndPcmId(paperId, pcmId);
    }

    public List<Integer> getPaperIdsWithAllPCMCompletedTasks(){
        HashMap<Integer, ReviewTemplate> idVsAssignedPaperDtls =  AssignedPapersUtil.getAllAssignedPapers();
        HashMap<Integer, Integer> paperIdVsCount = new HashMap<>();
        for(Integer id : idVsAssignedPaperDtls.keySet()){
            ReviewTemplate reviewTemplate = idVsAssignedPaperDtls.get(id);
            if(reviewTemplate.getRating() != null && reviewTemplate.getReviews() != null && reviewTemplate.getReviews().length != 0){
                paperIdVsCount.put(reviewTemplate.getPaperId(), paperIdVsCount.get(reviewTemplate.getPaperId()) + 1);
            }
        }

        List<Integer> completedPaperIds = new ArrayList<>();
        for(Integer paperId : paperIdVsCount.keySet()){
            if(paperIdVsCount.get(paperId) <= AssignedPapersUtil.getAssignPaperLimit()){
                completedPaperIds.add(paperId);
            }
        }

        return completedPaperIds;
    }

}
