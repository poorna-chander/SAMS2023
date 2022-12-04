package com.sams.samsapi.persistence;

import com.sams.samsapi.crud_utils.PaperPoolUtil;
import com.sams.samsapi.modelTemplates.ResearchPaper;
import com.sams.samsapi.util.CodeSmellFixer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PaperPool {
    public HashMap<Integer, ResearchPaper> getSubmissionDetailsbyUser(Integer userId){
        return PaperPoolUtil.getPaperDetailsBasedOnSubmitterId(userId);
    }

    @SuppressWarnings("unchecked")
    public Boolean createNewPaper(HashMap<String, Object> paperDetails){
        String title = paperDetails.get(CodeSmellFixer.LowerCase.TITLE).toString();
        Integer submitterId = Integer.parseInt(paperDetails.get(CodeSmellFixer.SnakeCase.SUBMITTER_ID).toString());
        List<String> authors =  paperDetails.containsKey(CodeSmellFixer.LowerCase.AUTHORS) ? (ArrayList<String>) paperDetails.get(CodeSmellFixer.LowerCase.AUTHORS) : new ArrayList<String>();
        String contact = paperDetails.get(CodeSmellFixer.LowerCase.CONTACT).toString();
        String fileName = paperDetails.get(CodeSmellFixer.SnakeCase.FILE_NAME).toString();
        String fileExtension = paperDetails.get(CodeSmellFixer.SnakeCase.FILE_EXTENSION).toString();
        Integer paperId = Integer.parseInt(paperDetails.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString());
        Integer revisionNo = Integer.parseInt(paperDetails.get(CodeSmellFixer.SnakeCase.REVISION_NO).toString());
        Boolean status = PaperPoolUtil.insertPaperDetails(title, submitterId, authors, contact, fileName, fileExtension, paperId, revisionNo);
        if(Boolean.TRUE.equals(status)){
            new NotificationsOps().insertPaperSubmissionNotification(PaperPoolUtil.getLatestRevisedPaperDetailsBasedOnPaperId(paperId).getId(), revisionNo, title, paperId);
        }
        return status;
    }

    public Boolean updatePaper(ResearchPaper paper){
        return PaperPoolUtil.updatePaperDetails(paper);
    }

    public Integer getFinalRatingByPaperID(Integer paperId){
        ResearchPaper paper = PaperPoolUtil.getLatestRevisedPaperDetailsBasedOnPaperId(paperId);
        return (paper != null) ? paper.getRating() : null;
    }

    public ResearchPaper getPaperDetails(Integer paperId){
        return PaperPoolUtil.getLatestRevisedPaperDetailsBasedOnPaperId(paperId);
    }

    public ArrayList<ResearchPaper> getAllPapersOfSubmitter(Integer userId){
        HashMap<Integer, ResearchPaper> paperDtls = PaperPoolUtil.getPaperDetailsBasedOnSubmitterId(userId);
        ArrayList<ResearchPaper> papers = new ArrayList<>();
        for(Integer id : paperDtls.keySet()){
            papers.add(paperDtls.get(id));
        }

        return papers;
    }

    public ArrayList<ResearchPaper> getAllLatestPapersOfSubmitter(Integer userId){
        HashMap<Integer, ResearchPaper> paperDtls = PaperPoolUtil.getLatestPaperDetailsBasedOnSubmitterId(userId);
        ArrayList<ResearchPaper> papers = new ArrayList<>();
        for(Integer id : paperDtls.keySet()){
            papers.add(paperDtls.get(id));
        }

        return papers;
    }

    public Boolean isGivenPaperSubmittedByUser(Integer userId, Integer paperId){
        HashMap<Integer, ResearchPaper> paperDtls = PaperPoolUtil.getLatestPaperDetailsBasedOnSubmitterId(userId);
        for(Integer id : paperDtls.keySet()){
            if(Objects.equals(paperDtls.get(id).getPaperId(), paperId) && Objects.equals(paperDtls.get(id).getSubmitterId(), userId)){
                    return true;
            }
        }

        return false;
    }

    public ResearchPaper getPaperByPaperId(Integer paperId){
        return PaperPoolUtil.getLatestRevisedPaperDetailsBasedOnPaperId(paperId);
    }

}
