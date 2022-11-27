package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sams.samsapi.json_crud_utils.AssignedPapersUtil;
import com.sams.samsapi.json_crud_utils.PaperChoicesUtil;
import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.json_crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.ReviewTemplate;
import com.sams.samsapi.model.ReviewTemplate.Reviews;
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
        HashMap<Integer, ResearchPaper> paperIdVsPaper = PapersUtil.getAllPaperDetailsBasedOnLatestRevision(true);
        for(Integer paperId : paperIdVsPaper.keySet()){

            HashMap<String,String> data = new HashMap<>();
            data.put(CodeSmellFixer.LowerCase.TITLE, paperIdVsPaper.get(paperId).getTitle());

            HashMap<Integer,ReviewTemplate> idVsAssignedPaperDtls =  AssignedPapersUtil.getAssignedPaperBasedOnPaperId(paperId);
            if(idVsAssignedPaperDtls.size() < AssignedPapersUtil.getAssignPaperLimit() && paperIdVsPaper.get(paperId).getSubmitterId() != pcmId){
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

}
