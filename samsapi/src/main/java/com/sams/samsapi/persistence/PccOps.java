package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.ReviewTemplate;

public class PccOps implements PccInterface {

    @Override
    public ArrayList<ResearchPaper> getAllSubmissions() {
        HashMap<Integer,ResearchPaper> idVsPaperDtls =  PapersUtil.getAllPapers();
        HashMap<Integer,ResearchPaper> paperIdVsPaper = new HashMap<>();
        for(Integer id : idVsPaperDtls.keySet()){
            ResearchPaper paper = idVsPaperDtls.get(id);
            Integer paperId = paper.getPaperId();
            if(paperIdVsPaper.containsKey(paperId)){
                ResearchPaper oldPaper = paperIdVsPaper.get(paperId);
                if(oldPaper.getRevisionNo() > paper.getRevisionNo()){
                    paper = oldPaper;
                }
            }
            paperIdVsPaper.put(paperId, paper);
        }

        ArrayList<ResearchPaper> papers = new ArrayList<>();
        for(Integer paperId : paperIdVsPaper.keySet()){
            papers.add(paperIdVsPaper.get(paperId));
        }

        return papers;
    }

    @Override
    public ArrayList<ResearchPaper> getPaperPendingPCCAssignments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<Integer, HashMap<Integer, Integer>> getPaperAssignmentDetails() {
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
