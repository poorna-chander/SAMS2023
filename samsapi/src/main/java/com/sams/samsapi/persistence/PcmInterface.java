package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.model.ReviewTemplate;
import com.sams.samsapi.model.ReviewTemplate.Reviews;

public interface PcmInterface {
    Boolean submitPaperChoices(ArrayList<Integer> paperIds, Integer pcmId);
    HashMap<Integer, HashMap<String, String>> getMetaAvailablePaperDetails(Integer pcmId);
    Boolean submitReview(Integer paperId, ReviewTemplate reviewTemplate);
    Reviews[] getReviewData(HashMap<Integer,String> reviewData);
    ReviewTemplate getReviewTemplate(Integer pcmId, Integer paperId);
}
