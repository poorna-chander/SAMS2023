package com.sams.samsapi.persistence;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

public interface SubmitterInterface {
    public boolean SubmitPaperForm(HashMap<String, String> data, MultipartFile fileBytes);

    public boolean updateSubmission(HashMap<String, String> data, MultipartFile fileBytes, int paper_id);

    public HashMap<String, HashMap<String, String>> getSubmissionDetails();

    public HashMap<String, Integer> viewFinalRating();

    public boolean validateFormFile(HashMap<String, String> data, MultipartFile fileBytes);
}
