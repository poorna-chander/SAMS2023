package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.ReviewTemplate;

public class PccOps implements PccInterface {

    @Override
    public ArrayList<ResearchPaper> getAllSubmissions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<ResearchPaper> getPaperPendingPCCAssignments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<Integer, HashMap<Integer, Integer>> getpaperAssignmentDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean assignPaperToPCM(Integer paperId, Integer pcmId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<Integer, ReviewTemplate> viewPCMReviews(Integer paperId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void ratePaper(Integer paperId, Integer rating) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public HashMap<Integer, ArrayList<Integer>> getPCMChoices() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResearchPaper getPaperDetails(Integer paperId) {
        // TODO Auto-generated method stub
        return null;
    }

    void notifyPCMforReview(Integer pcmId){

    }
    void notifyPCMsforChoiceMaking(){

    }
    ArrayList<Integer> getAvailablePCMs(){

        return null;
    }

}
