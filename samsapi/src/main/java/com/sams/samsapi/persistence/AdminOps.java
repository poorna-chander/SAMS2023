package com.sams.samsapi.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sams.samsapi.json_crud_utils.DeadlineUtil;
import com.sams.samsapi.json_crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.model.ReviewQuestionnaire;
import com.sams.samsapi.model.Deadline.TYPE;
import com.sams.samsapi.model.ReviewQuestionnaire.STATUS;
import com.sams.samsapi.util.CodeSmellFixer;

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

}
