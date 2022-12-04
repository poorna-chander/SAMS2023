package com.sams.samsapi.crud_utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sams.samsapi.modelTemplates.Deadline;
import com.sams.samsapi.modelTemplates.Deadline.TYPE;

@Component
public class DeadlineUtil {
    
    private static final Logger LOG = Logger.getLogger(DeadlineUtil.class.getName());
    private static ObjectMapper objectMapper;
    private static String deadlineFileName;
    private static Deadline[] deadlineDtls;
    private static HashMap<TYPE, Deadline> typeVsDeadlines;

    
    public DeadlineUtil(@Value("${deadline.file}") String deadlineFileName, ObjectMapper objectMapper) throws Exception{
        initialize(deadlineFileName, objectMapper);
    }

    private static void initialize( String localDeadlineFileName, ObjectMapper localObjectMapper) throws Exception{
        deadlineFileName = localDeadlineFileName;
        objectMapper = localObjectMapper;
        deadlineDtls = objectMapper.readValue(new File(deadlineFileName), Deadline[].class);
        initializeData();
    }

    private static void initializeData(){
        typeVsDeadlines = new HashMap<>();

        for(Deadline deadline : deadlineDtls){
            typeVsDeadlines.put(deadline.getType(), deadline);
        }
    }

    private static void saveDeadline() throws Exception{
        List<Deadline> deadlineList = new ArrayList<>();
        for(TYPE type : typeVsDeadlines.keySet()){
            deadlineList.add(typeVsDeadlines.get(type));
        }
        deadlineDtls = new Deadline[deadlineList.size()];
        deadlineList.toArray(deadlineDtls); 

        objectMapper.writeValue(new File(deadlineFileName), deadlineDtls);
    }

    private static Boolean isSaveDeadlineSuccessful(){
        try{
            saveDeadline();
            return true;
        }catch(Exception ex){
            LOG.log(Level.SEVERE, "Exception is ::: {0}", ex.getMessage());
        }
        return false;
    }

    public static Boolean updatePaperSubmissionDeadlines(Long timeStamp){
        Deadline deadline = typeVsDeadlines.get(TYPE.PAPER_SUBMISSION_DEADLINE);
        deadline.setTimeStamp(timeStamp);
        typeVsDeadlines.put(TYPE.PAPER_SUBMISSION_DEADLINE, deadline);
        return isSaveDeadlineSuccessful();
    }

    public static Boolean updateReviewSubmissionDeadlines(Long timeStamp){
        Deadline deadline = typeVsDeadlines.get(TYPE.REVIEW_SUBMISSION_DEADLINE);
        deadline.setTimeStamp(timeStamp);
        typeVsDeadlines.put(TYPE.REVIEW_SUBMISSION_DEADLINE, deadline);
        return isSaveDeadlineSuccessful();
    }

    public static Long getPaperSubmissionDeadline(){
        return typeVsDeadlines.get(TYPE.PAPER_SUBMISSION_DEADLINE).getTimeStamp();
    }
    
    public static Long getReviewSubmissionDeadline(){
        return typeVsDeadlines.get(TYPE.REVIEW_SUBMISSION_DEADLINE).getTimeStamp();
    }
}
