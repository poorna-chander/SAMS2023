package com.sams.samsapi.persistence;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

public interface SubmitterInterface {
    public boolean SubmitPaperForm(String UploadPath, HashMap<String, String> data, MultipartFile fileBytes)
            throws IllegalStateException, IOException;

    public boolean revisePaperForm(String UploadPath, HashMap<String, String> data, MultipartFile fileBytes,
            int paper_id) throws IllegalStateException, IOException;

    public HashMap<String, HashMap<String, String>> getSubmissionDetails();

    public HashMap<String, Integer> viewFinalRating();

    public boolean validateFormFile(HashMap<String, String> data, MultipartFile fileBytes);
}
