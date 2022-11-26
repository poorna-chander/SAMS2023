package com.sams.samsapi.persistence;

import java.util.HashMap;

public interface SubmitterInterface {
    public boolean SubmitPaperForm(HashMap<String, String> data, byte[] fileBytes);

    public boolean updateSubmission(HashMap<String, String> data, byte[] fileBytes);

    public HashMap<String, HashMap<String, String>> getSubmissionDetails();

    public HashMap<String, Integer> viewFinalRating();
}
