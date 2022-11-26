package com.sams.samsapi.persistence;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import com.sams.samsapi.model.Submitter;
import com.sams.samsapi.persistence.Users;

public class SubmitterOps implements SubmitterInterface {

    private int id;
    private String name;

    public SubmitterOps(int id) {
        this.id = id;
        Users users = new Users();
        this.name = users.getSubmitterName(id);
    }

    @Override
    public boolean SubmitPaperForm(HashMap<String, String> data, MultipartFile fileBytes) {
        return false;
    }

    @Override
    public boolean updateSubmission(HashMap<String, String> data, MultipartFile fileBytes) {
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
        return false;
    }

}
