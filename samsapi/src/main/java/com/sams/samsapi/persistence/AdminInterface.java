package com.sams.samsapi.persistence;

import java.util.HashMap;

import com.sams.samsapi.model.Deadline.TYPE;

public interface AdminInterface {
    Boolean updateDeadlines(TYPE type, long deadlineInMillis) throws Exception;
    Boolean updateTemplate(HashMap<Integer, String> reviewDetails);
}
