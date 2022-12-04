package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.modelTemplates.ResearchPaper;
import com.sams.samsapi.modelTemplates.ReviewTemplate;

public interface PccInterface {
    ArrayList<ResearchPaper> getAllSubmissions();
    ArrayList<ResearchPaper> getPaperPendingPCCAssignments();
    ArrayList<HashMap<String,Object>> getRatingCompletedPaperDetails();
    ArrayList<ResearchPaper> getAllReviewCompletedPapersDetails();
    Boolean assignPaperToPCM(Integer paperId, Integer pcmId);
    HashMap<Integer,ReviewTemplate> viewPCMReviews(Integer paperId);
    void ratePaper(Integer paperId, Integer rating);
    HashMap<Integer, ArrayList<ResearchPaper>> getPCMChoices();
    ArrayList<HashMap<String,Object>> getAvailablePCMs();
}
