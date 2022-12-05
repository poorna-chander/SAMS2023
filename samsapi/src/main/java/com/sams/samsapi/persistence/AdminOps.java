package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.sams.samsapi.crud_utils.DeadlineUtil;
import com.sams.samsapi.crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.modelTemplates.Deadline.TYPE;
import com.sams.samsapi.modelTemplates.ReviewQuestionnaire;
import com.sams.samsapi.modelTemplates.ReviewQuestionnaire.STATUS;

public class AdminOps implements AdminInterface {
    private static final Logger LOG = Logger.getLogger(AdminOps.class.getName());

    @Override
    public Boolean updateDeadlines(TYPE type, long deadlineInMillis) throws Exception {

        if(type.equals(TYPE.PAPER_SUBMISSION_DEADLINE)){
            return DeadlineUtil.updatePaperSubmissionDeadlines(deadlineInMillis);
        }else if(type.equals(TYPE.REVIEW_SUBMISSION_DEADLINE)){
            return DeadlineUtil.updateReviewSubmissionDeadlines(deadlineInMillis);
        }

        return false;
    }

    @Override
    public Boolean updateTemplate(HashMap<Integer, String> reviewDetails) {
        HashMap<Integer,ReviewQuestionnaire> idVsReviewQuestionnaire = ReviewQuestionnaireUtil.getAllReviewQuestionnaire();
        for(Integer id : idVsReviewQuestionnaire.keySet()){
            if(!reviewDetails.containsKey(id)){
                ReviewQuestionnaireUtil.deleteReviewQuestionnaire(id);
            }else{
                ReviewQuestionnaire review = new ReviewQuestionnaire(id, reviewDetails.get(id), STATUS.IN_LIVE);
                ReviewQuestionnaireUtil.updateReviewQuestionnaire(review);
            }
        }
        for(Integer id : reviewDetails.keySet()){
            if(!idVsReviewQuestionnaire.containsKey(id)){
                ReviewQuestionnaireUtil.insertReviewQuestionnaire(reviewDetails.get(id), STATUS.IN_LIVE);
            }
        }
        return true;
    }

    @Override
    public ArrayList<ReviewQuestionnaire> getTemplate() {
        ArrayList<ReviewQuestionnaire> reviews = new ArrayList<>();
        HashMap<Integer,ReviewQuestionnaire> idVsReviewQuestionnaire = ReviewQuestionnaireUtil.getAllReviewQuestionnaire();
        for(Integer id : idVsReviewQuestionnaire.keySet()){
            if(idVsReviewQuestionnaire.get(id).getStatus() == STATUS.IN_LIVE){
                reviews.add(idVsReviewQuestionnaire.get(id));
            }
        }
        return reviews;
    }

    @Override
    public HashMap<String,Long> getDeadlines() {
        HashMap<String,Long> deadlineData = new HashMap<>();
        deadlineData.put(TYPE.PAPER_SUBMISSION_DEADLINE.name(), DeadlineUtil.getPaperSubmissionDeadline());
        deadlineData.put(TYPE.REVIEW_SUBMISSION_DEADLINE.name(), DeadlineUtil.getReviewSubmissionDeadline());
        return deadlineData;
    }

}
