package com.sams.samsapi.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sams.samsapi.json_crud_utils.AssignedPapersUtil;
import com.sams.samsapi.json_crud_utils.PaperChoicesUtil;
import com.sams.samsapi.json_crud_utils.PaperDetailsUtil;
import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.json_crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.json_crud_utils.UserUtils;
import com.sams.samsapi.model.PaperDetails;
import com.sams.samsapi.persistence.SubmitterInterface;
import com.sams.samsapi.persistence.SubmitterOps;

@RestController
@RequestMapping("/")
public class SamsController {
    private AssignedPapersUtil assignedPapersUtil;
    private PaperChoicesUtil paperChoicesUtil;
    private PapersUtil papersUtil;
    private ReviewQuestionnaireUtil reviewUtil;
    private UserUtils userUtil;
    private PaperDetailsUtil paperDetailsUtil;
    private SubmitterInterface submitterInterface;

    public SamsController(AssignedPapersUtil assignedPapersUtil, PaperChoicesUtil paperChoicesUtil,
            PapersUtil papersUtil, ReviewQuestionnaireUtil reviewUtil, UserUtils userUtil,
            PaperDetailsUtil paperDetailsUtil) {
        this.assignedPapersUtil = assignedPapersUtil;
        this.paperChoicesUtil = paperChoicesUtil;
        this.papersUtil = papersUtil;
        this.reviewUtil = reviewUtil;
        this.userUtil = userUtil;
        this.paperDetailsUtil = paperDetailsUtil;
    }

    @PostMapping("/submit")
    public ResponseEntity<Object> submitPaperForm(@Value("${filelocation.savepath}") String uploadPath,
            @RequestParam String title,
            @RequestParam String authors,
            @RequestParam String contact,
            @RequestParam MultipartFile file,
            @RequestHeader String submitterId) throws IllegalStateException, IOException {

        HashMap<String, String> formData = new HashMap<>();
        formData.put("title", title);
        formData.put("authors", authors);
        formData.put("contact", contact);
        formData.put("submitterId", submitterId);

        submitterInterface = new SubmitterOps();
        boolean formvalidation = submitterInterface.validateFormFile(formData, file);

        if (!formvalidation) {
            System.out.println("add to notification");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            boolean submissionStatus = submitterInterface.SubmitPaperForm(uploadPath, formData, file);
            if (submissionStatus) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return null;
    }

    @PostMapping("/revise")
    public ResponseEntity<Object> revisePaperForm(@Value("${filelocation.savepath}") String uploadPath,
            @RequestParam String title,
            @RequestParam String authors,
            @RequestParam String contact,
            @RequestParam MultipartFile file,
            @RequestParam String paperId,
            @RequestHeader String submitterId) throws IllegalStateException, IOException {

        int paperIdInt = Integer.parseInt(paperId);
        HashMap<String, String> formData = new HashMap<>();
        formData.put("title", title);
        formData.put("authors", authors);
        formData.put("contact", contact);
        formData.put("submitterId", submitterId);

        submitterInterface = new SubmitterOps();
        boolean formvalidation = submitterInterface.validateFormFile(formData, file);

        if (!formvalidation) {
            System.out.println("add to bad file format notification");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            boolean submissionStatus = submitterInterface.revisePaperForm(uploadPath, formData, file, paperIdInt);
            if (submissionStatus) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return null;
    }
}
