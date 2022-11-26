package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.Submitter;
import com.sams.samsapi.model.User;
import com.sams.samsapi.persistence.Users;
import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.json_crud_utils.UserUtils;

public class SubmitterOps implements SubmitterInterface {

    private int id;
    private User name;

    public SubmitterOps() {
        this.id = 1; // TODO: MAP TO USER USING
        this.name = UserUtils.getUserDetails(this.id);
    }

    @Override
    public boolean SubmitPaperForm(HashMap<String, String> data, MultipartFile fileBytes) {
        int next_paper_id = PapersUtil.getNextPaperId();
        String uploadedFileName = fileBytes.getOriginalFilename();
        String[] uploadedFileExtension = uploadedFileName.split("\\.");
        String extension = uploadedFileExtension[uploadedFileExtension.length - 1];
        String fileName = data.get("title") + 1 + ".pdf";
        List<String> authors = new ArrayList<>(Arrays.asList(data.get("authors").split(",")));
        PapersUtil.insertPaperDetails(data.get("title"),
                4,
                authors,
                data.get("contact"),
                fileName,
                extension,
                next_paper_id,
                1);

        return true;
    }

    @Override
    public boolean updateSubmission(HashMap<String, String> data, MultipartFile fileBytes, int paper_id) {
        HashMap<Integer, ResearchPaper> paperSubmissions = PapersUtil.getPaperDetailsBasedOnPaperId(paper_id);
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public HashMap<String, HashMap<String, String>> getSubmissionDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<String, Integer> viewFinalRating() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateFormFile(HashMap<String, String> data, MultipartFile fileBytes) {
        // TODO Auto-generated method stub

        // extension chesk
        String uploadedFileName = fileBytes.getOriginalFilename();
        String[] uploadedFileExtension = uploadedFileName.split("\\.");
        String extension = uploadedFileExtension[uploadedFileExtension.length - 1];
        if (!extension.equals("png")) {
            return false;
        }
        return true;
    }

}
