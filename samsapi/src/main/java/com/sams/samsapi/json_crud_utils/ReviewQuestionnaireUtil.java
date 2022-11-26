package com.sams.samsapi.json_crud_utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sams.samsapi.model.ReviewQuestionnaire;
import com.sams.samsapi.model.ReviewQuestionnaire.STATUS;

@Component
public class ReviewQuestionnaireUtil {
    private static final Logger LOG = Logger.getLogger(ReviewQuestionnaireUtil.class.getName());
    private ObjectMapper objectMapper;
    private String reviewTemplateFileName;
    private ReviewQuestionnaire[] reviewDtls;
    private HashMap<Integer,ReviewQuestionnaire> idVsReviewQuestionnaire;
    private int nextReviewId = 1;
    
    public ReviewQuestionnaireUtil(@Value("${reviewQuestionnaire.file}") String userFileName, ObjectMapper objectMapper) throws Exception{
        this.objectMapper = objectMapper;
        this.reviewTemplateFileName = userFileName;
        reviewDtls = objectMapper.readValue(new File(userFileName), ReviewQuestionnaire[].class);
        initializeData();
    }

    public void initializeData(){
        idVsReviewQuestionnaire = new HashMap<>();

        for(ReviewQuestionnaire review : reviewDtls){
            idVsReviewQuestionnaire.put(review.getId(), review);
            nextReviewId = review.getId() + 1;
        }
    }

    private Integer getNextReviewId(){
        return nextReviewId++;
    }

    private void saveReviewQuestionnaire() throws Exception{
        List<ReviewQuestionnaire> reviewList = new ArrayList<>();
        for(Integer userId : idVsReviewQuestionnaire.keySet()){
            reviewList.add(idVsReviewQuestionnaire.get(userId));
        }
        reviewDtls = new ReviewQuestionnaire[reviewList.size()];
        reviewList.toArray(reviewDtls); 

        objectMapper.writeValue(new File(reviewTemplateFileName), reviewDtls);
    }

    private Boolean isSaveReviewQuestionnaireSuccessful(){
        try{
            saveReviewQuestionnaire();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public Boolean insertReviewQuestionnaire(String field, STATUS status){
        Integer reviewId = getNextReviewId();
        if(field == null || status == null){
            return false;
        }
        ReviewQuestionnaire review = new ReviewQuestionnaire(reviewId, field, status);
        idVsReviewQuestionnaire.put(reviewId, review);
        return isSaveReviewQuestionnaireSuccessful();
    }

    public Boolean updateReviewQuestionnaire(ReviewQuestionnaire review){
        Integer reviewId = review.getId();
        if(reviewId == null || review.getField() == null || review.getStatus() == null){
            return false;
        }
        idVsReviewQuestionnaire.put(reviewId, review);
        return isSaveReviewQuestionnaireSuccessful();
    }

    public Boolean deleteReviewQuestionnaire(Integer reviewId){
        if(reviewId == null || !idVsReviewQuestionnaire.containsKey(reviewId)){
            return false;
        }
        ReviewQuestionnaire review = idVsReviewQuestionnaire.get(reviewId);
        review.setStatus(STATUS.NOT_LIVE);
        idVsReviewQuestionnaire.put(reviewId, review);
        return isSaveReviewQuestionnaireSuccessful();
    }

    public HashMap<Integer,ReviewQuestionnaire> getAllReviewQuestionnaire(){
        return idVsReviewQuestionnaire;
    }

    public ReviewQuestionnaire getReviewQuestionnaire(Integer reviewId){
        return idVsReviewQuestionnaire.get(reviewId);
    }
}
