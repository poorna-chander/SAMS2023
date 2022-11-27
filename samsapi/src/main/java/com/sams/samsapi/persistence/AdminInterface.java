package com.sams.samsapi.persistence;

import java.util.HashMap;

public interface AdminInterface {
    public enum DEADLINE_TYPE{
        PAPER_SUBMISSION,
        REVIEW_SUBMISSION
    }
    Boolean updateDeadlines(DEADLINE_TYPE type, long deadlineInMillis) throws Exception;
    Boolean updateTemplate(HashMap<Integer, String> reviewDetails);
}
