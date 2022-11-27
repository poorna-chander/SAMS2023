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
    private static ObjectMapper objectMapper;
    private static String reviewTemplateFileName;
    private static ReviewQuestionnaire[] reviewDtls;
    private static HashMap<Integer,ReviewQuestionnaire> idVsReviewQuestionnaire;
    private static int nextReviewId = 1;
    
    public ReviewQuestionnaireUtil(@Value("${reviewQuestionnaire.file}") String reviewFileName, ObjectMapper objectMapper) throws Exception{
        initialize(reviewFileName, objectMapper);
    }

    private static void initialize(String localReviewFileName, ObjectMapper localObjectMapper) throws Exception{
        reviewTemplateFileName = localReviewFileName;
        objectMapper = localObjectMapper;
        reviewDtls = objectMapper.readValue(new File(reviewTemplateFileName), ReviewQuestionnaire[].class);
        initializeData();
    }

    private static void initializeData(){
        idVsReviewQuestionnaire = new HashMap<>();

        for(ReviewQuestionnaire review : reviewDtls){
            idVsReviewQuestionnaire.put(review.getId(), review);
            nextReviewId = review.getId() + 1;
        }
    }

    private static Integer getNextReviewId(){
        return nextReviewId++;
    }

    private static void saveReviewQuestionnaire() throws Exception{
        List<ReviewQuestionnaire> reviewList = new ArrayList<>();
        for(Integer userId : idVsReviewQuestionnaire.keySet()){
            reviewList.add(idVsReviewQuestionnaire.get(userId));
        }
        reviewDtls = new ReviewQuestionnaire[reviewList.size()];
        reviewList.toArray(reviewDtls); 

        objectMapper.writeValue(new File(reviewTemplateFileName), reviewDtls);
    }

    private static Boolean isSaveReviewQuestionnaireSuccessful(){
        try{
            saveReviewQuestionnaire();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public static Boolean insertReviewQuestionnaire(String field, STATUS status){
        Integer reviewId = getNextReviewId();
        if(field == null || status == null){
            return false;
        }
        ReviewQuestionnaire review = new ReviewQuestionnaire(reviewId, field, status);
        idVsReviewQuestionnaire.put(reviewId, review);
        return isSaveReviewQuestionnaireSuccessful();
    }

    public static Boolean updateReviewQuestionnaire(ReviewQuestionnaire review){
        Integer reviewId = review.getId();
        if(reviewId == null || review.getField() == null || review.getStatus() == null){
            return false;
        }
        if(!idVsReviewQuestionnaire.containsKey(reviewId)){
            return false;
        }
        idVsReviewQuestionnaire.put(reviewId, review);
        return isSaveReviewQuestionnaireSuccessful();
    }

    public static Boolean deleteReviewQuestionnaire(Integer reviewId){
        if(reviewId == null || !idVsReviewQuestionnaire.containsKey(reviewId)){
            return false;
        }
        ReviewQuestionnaire review = idVsReviewQuestionnaire.get(reviewId);
        review.setStatus(STATUS.NOT_LIVE);
        idVsReviewQuestionnaire.put(reviewId, review);
        return isSaveReviewQuestionnaireSuccessful();
    }

    public static HashMap<Integer,ReviewQuestionnaire> getAllReviewQuestionnaire(){
        return idVsReviewQuestionnaire;
    }

    public static ReviewQuestionnaire getReviewQuestionnaire(Integer reviewId){
        return idVsReviewQuestionnaire.get(reviewId);
    }

    public static Boolean isGivenReviewQuestionnaireValid(Integer reviewId){
        if(!idVsReviewQuestionnaire.containsKey(reviewId)){
            return false;
        }
        if(idVsReviewQuestionnaire.get(reviewId).getStatus().equals(STATUS.NOT_LIVE)){
            return false;
        }
        return true;
    }
}
