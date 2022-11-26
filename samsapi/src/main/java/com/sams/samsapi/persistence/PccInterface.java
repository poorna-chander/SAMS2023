package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.ReviewTemplate;
import com.sams.samsapi.model.ReviewTemplate.Reviews;

public interface PccInterface {
    ArrayList<ResearchPaper> getAllSubmissions();
    ArrayList<ResearchPaper> getPaperPendingPCCAssignments();
    HashMap<Integer,HashMap<Integer,Integer>> getPaperAssignmentDetails();
    Boolean assignPaperToPCM(Integer paperId, Integer pcmId);
    HashMap<Integer,ReviewTemplate> viewPCMReviews(Integer paperId);
    void ratePaper(Integer paperId, Integer rating);
    HashMap<Integer, ArrayList<ResearchPaper>> getPCMChoices();
    ResearchPaper getPaperDetails(Integer paperId);

}
