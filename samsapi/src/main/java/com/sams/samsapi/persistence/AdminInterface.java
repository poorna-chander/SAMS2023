package com.sams.samsapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.sams.samsapi.modelTemplates.Deadline.TYPE;
import com.sams.samsapi.modelTemplates.ReviewQuestionnaire;

public interface AdminInterface {
    Boolean updateDeadlines(TYPE type, long deadlineInMillis) throws Exception;
    Boolean updateTemplate(HashMap<Integer, String> reviewDetails);
    ArrayList<ReviewQuestionnaire> getTemplate();
    HashMap<String,Long> getDeadlines();
}
