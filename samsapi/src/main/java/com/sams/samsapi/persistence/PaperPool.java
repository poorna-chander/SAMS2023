package com.sams.samsapi.persistence;

import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.util.CodeSmellFixer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sams.samsapi.json_crud_utils.PapersUtil;

public class PaperPool {
    public HashMap<Integer, ResearchPaper> getSubmissionDetailsbyUser(Integer userId){
        return PapersUtil.getPaperDetailsBasedOnSubmitterId(userId);
    }

    @SuppressWarnings("unchecked")
    public Boolean createNewPaper(HashMap<String, Object> paperDetails){
        String title = paperDetails.get(CodeSmellFixer.LowerCase.TITLE).toString();
        Integer submitterId = Integer.parseInt(paperDetails.get(CodeSmellFixer.SnakeCase.SUBMITTER_ID).toString());
        List<String> authors =  paperDetails.containsKey(CodeSmellFixer.LowerCase.TITLE) ? (ArrayList<String>) paperDetails.get(CodeSmellFixer.LowerCase.TITLE) : new ArrayList<String>();
        String contact = paperDetails.get(CodeSmellFixer.LowerCase.CONTACT).toString();
        String fileName = paperDetails.get(CodeSmellFixer.SnakeCase.FILE_NAME).toString();
        String fileExtension = paperDetails.get(CodeSmellFixer.SnakeCase.FILE_EXTENSION).toString();
        Integer paperId = Integer.parseInt(paperDetails.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString());
        Integer revisionNo = Integer.parseInt(paperDetails.get(CodeSmellFixer.SnakeCase.REVISION_NO).toString());
        Boolean status = PapersUtil.insertPaperDetails(title, submitterId, authors, contact, fileName, fileExtension, paperId, revisionNo);
        if(Boolean.TRUE.equals(status)){
            new NotificationsOps().insertPaperSubmissionNotification(PapersUtil.getLatestRevisedPaperDetailsBasedOnPaperId(paperId).getId(), revisionNo, title, paperId);
        }
        return status;
    }

    public Boolean updatePaper(ResearchPaper paper){
        return PapersUtil.updatePaperDetails(paper);
    }

    public Integer getFinalRatingByPaperID(Integer paperId){
        ResearchPaper paper = PapersUtil.getLatestRevisedPaperDetailsBasedOnPaperId(paperId);
        return (paper != null) ? paper.getRating() : null;
    }

    public ResearchPaper getPaperDetails(Integer paperId){
        return PapersUtil.getLatestRevisedPaperDetailsBasedOnPaperId(paperId);
    }

}
