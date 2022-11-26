package com.sams.samsapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sams.samsapi.json_crud_utils.AssignedPapersUtil;
import com.sams.samsapi.json_crud_utils.PaperChoicesUtil;
import com.sams.samsapi.json_crud_utils.PaperDetailsUtil;
import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.json_crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.json_crud_utils.UserUtils;
import com.sams.samsapi.model.PaperDetails;
import com.sams.samsapi.persistence.SubmitterInterface;

@RestController
@RequestMapping("/")
public class SamsController {
    public SamsController(AssignedPapersUtil assignedPapersUtil, PaperChoicesUtil paperChoicesUtil, PapersUtil papersUtil, ReviewQuestionnaireUtil reviewUtil, UserUtils userUtil, PaperDetailsUtil paperDetailsUtil) {
      
    }
}
