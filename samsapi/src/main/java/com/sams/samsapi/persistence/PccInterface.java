package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.ReviewTemplate;

public interface PccInterface {
    ArrayList<ResearchPaper> getAllSubmissions();
    ArrayList<ResearchPaper> getPaperPendingPCCAssignments();
    HashMap<Integer,HashMap<Integer,Integer>> getpaperAssignmentDetails();
    Boolean assignPaperToPCM(Integer paperId, Integer pcmId);
    HashMap<Integer,ReviewTemplate> viewPCMReviews(Integer paperId);
    void ratePaper(Integer paperId, Integer rating);
    HashMap<Integer, ArrayList<Integer>> getPCMChoices();
    ResearchPaper getPaperDetails(Integer paperId);

}
