package com.sams.samsapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sams.samsapi.model.ReviewTemplate;
import com.sams.samsapi.model.User.USER_TYPE;
import com.sams.samsapi.persistence.PccInterface;
import com.sams.samsapi.persistence.PccOps;
import com.sams.samsapi.persistence.SubmitterInterface;
import com.sams.samsapi.persistence.SubmitterOps;
import com.sams.samsapi.persistence.UsersInterface;
import com.sams.samsapi.persistence.UsersOps;
import com.sams.samsapi.util.CodeSmellFixer;

@RestController
@RequestMapping("/")
public class SamsController {
    private static final Logger LOG = Logger.getLogger(SamsController.class.getName());
    private SubmitterInterface submitterInterface = new SubmitterOps();
    private UsersInterface usersInterface = new UsersOps();
    private PccInterface pccInterface = new PccOps();

    public SamsController() {

    }

    @PostMapping("/paper/submit")
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

    @PostMapping("/paper/revise")
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

    @GetMapping("/papers")
    public ResponseEntity<Object> getAllPapers(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getAllSubmissions(), HttpStatus.OK);
    }

    @GetMapping("/papers/pcc/assignment/pending")
    public ResponseEntity<Object> getAllPapersPendingAssignment(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getPaperPendingPCCAssignments(), HttpStatus.OK);
    }


    @GetMapping("/papers/pcc/assignment/completed")
    public ResponseEntity<Object> getAllPapersCompletedAssignment(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getPaperAssignmentDetails(), HttpStatus.OK);
    }

    @PutMapping("/papers/pcc/assignment/assign")
    public ResponseEntity<Object> updatePapersAssignment(@RequestHeader Object userId, @RequestBody HashMap<String, Object> details) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!details.containsKey(CodeSmellFixer.SnakeCase.PCM_ID) || !details.containsKey(CodeSmellFixer.SnakeCase.PAPER_ID)) {
            CodeSmellFixer.ExceptionThrower.throwInvalidBody();
        }
        Boolean status = pccInterface.assignPaperToPCM(Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()), Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PCM_ID).toString()));
        return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/papers/pcc/assignment/view")
    public ResponseEntity<Object> getPaperAssignment(@RequestHeader Object userId, @RequestParam Integer paperId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        HashMap<Integer,ReviewTemplate> pcmReviews = pccInterface.viewPCMReviews(paperId);
        return pcmReviews.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(pcmReviews, HttpStatus.OK);
    }

    @GetMapping("/papers/pcc/assignment/choice")
    public ResponseEntity<Object> getPaperChoicesMade(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getPCMChoices(), HttpStatus.OK);
    }

    @PutMapping("/papers/pcc/rate")
    public ResponseEntity<Object> updatePaperRating(@RequestHeader Object userId, @RequestBody HashMap<String, Object> details) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!details.containsKey(CodeSmellFixer.LowerCase.RATING) || !details.containsKey(CodeSmellFixer.SnakeCase.PAPER_ID)) {
            CodeSmellFixer.ExceptionThrower.throwInvalidBody();
        }
        pccInterface.ratePaper(Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()), Integer.parseInt(details.get(CodeSmellFixer.LowerCase.RATING).toString()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
