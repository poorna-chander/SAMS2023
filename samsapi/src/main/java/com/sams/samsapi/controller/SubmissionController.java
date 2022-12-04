package com.sams.samsapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sams.samsapi.crud_utils.AssignedPapersUtil;
import com.sams.samsapi.crud_utils.NotificationUtil;
import com.sams.samsapi.crud_utils.PaperChoicesUtil;
import com.sams.samsapi.crud_utils.PaperDetailsUtil;
import com.sams.samsapi.crud_utils.PaperPoolUtil;
import com.sams.samsapi.crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.crud_utils.UserUtils;
import com.sams.samsapi.modelTemplates.User.USER_TYPE;
import com.sams.samsapi.persistence.PaperPool;
import com.sams.samsapi.persistence.PccInterface;
import com.sams.samsapi.persistence.PccOps;
import com.sams.samsapi.persistence.SubmitterInterface;
import com.sams.samsapi.persistence.SubmitterOps;
import com.sams.samsapi.persistence.UsersInterface;
import com.sams.samsapi.persistence.UsersOps;
import com.sams.samsapi.util.CodeSmellFixer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/")
public class SubmissionController {
    private static final Logger LOG = Logger.getLogger(SubmissionController.class.getName());
    private SubmitterInterface submitterInterface = new SubmitterOps();
    private UsersInterface usersInterface = new UsersOps();
    private PccInterface pccInterface = new PccOps();
    
    public SubmissionController(AssignedPapersUtil assignedPapersUtil, PaperChoicesUtil paperChoicesUtil,
    PaperDetailsUtil paperDetailsUtil,
    PaperPoolUtil papersUtil, ReviewQuestionnaireUtil reviewQuestionnaireUtil, UserUtils userUtils,
    NotificationUtil notificationUtil) {

    }

    @PostMapping("/paper/submit")
    public ResponseEntity<Object> submitPaperForm(@Value("${filelocation.savepath}") String uploadPath,
            @RequestParam("title") String title,
            @RequestParam("authors") String authors,
            @RequestParam("contact") String contact,
            @RequestParam(value = "file") MultipartFile file,
            @RequestHeader String userId) throws IllegalStateException, IOException {

        HashMap<String, String> formData = new HashMap<>();
        formData.put("title", title);
        formData.put("authors", authors);
        formData.put("contact", contact);
        formData.put("submitterId", userId);

        boolean formvalidation = submitterInterface.validateFormFile(formData, file);

        if (!formvalidation) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            boolean submissionStatus = submitterInterface.SubmitPaperForm(uploadPath, formData, file);
            if (submissionStatus) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return null;
    }

    @PostMapping("/paper/revise")
    public ResponseEntity<Object> revisePaperForm(@Value("${filelocation.savepath}") String uploadPath,
            @RequestParam String title,
            @RequestParam String authors,
            @RequestParam String contact,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String paperId,
            @RequestHeader String userId) throws IllegalStateException, IOException {

        int paperIdInt = Integer.parseInt(paperId);
        HashMap<String, String> formData = new HashMap<>();
        formData.put("title", title);
        formData.put("authors", authors);
        formData.put("contact", contact);
        formData.put("submitterId", userId);

        boolean formvalidation = true;
        if(file != null){
            formvalidation = submitterInterface.validateFormFile(formData, file);
        } 

        if (!formvalidation) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            boolean submissionStatus = submitterInterface.revisePaperForm(uploadPath, formData, file, paperIdInt);
            if (submissionStatus) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return null;
    }

    @GetMapping("/papers")
    public ResponseEntity<Object> getAllPapers(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        Boolean isSubmitter =  usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.SUBMITTER);
        if (Boolean.FALSE.equals(isValidUser || isSubmitter)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(Boolean.TRUE.equals(isSubmitter) ? new PaperPool().getAllLatestPapersOfSubmitter(Integer.parseInt(userId.toString())) : pccInterface.getAllSubmissions(), HttpStatus.OK);
    }

    @GetMapping("/paper/{paperId}")
    public ResponseEntity<Object> getPaperByPaperId(@RequestHeader Object userId, @PathVariable int paperId) throws Exception {
        Boolean isSubmitter = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.SUBMITTER);
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) ||
                                usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals( isValidUser || isSubmitter)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(Boolean.TRUE.equals(isSubmitter) && Boolean.FALSE.equals(new PaperPool().isGivenPaperSubmittedByUser(Integer.parseInt(userId.toString()), paperId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<> (new PaperPool().getPaperByPaperId(paperId), HttpStatus.OK);
    }
}
