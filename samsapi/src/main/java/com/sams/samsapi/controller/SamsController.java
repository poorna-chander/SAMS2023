package com.sams.samsapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sams.samsapi.json_crud_utils.AssignedPapersUtil;
import com.sams.samsapi.json_crud_utils.NotificationUtil;
import com.sams.samsapi.json_crud_utils.PaperChoicesUtil;
import com.sams.samsapi.json_crud_utils.PaperDetailsUtil;
import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.json_crud_utils.ReviewQuestionnaireUtil;
import com.sams.samsapi.json_crud_utils.UserUtils;
import com.sams.samsapi.model.Deadline.TYPE;
import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.ReviewTemplate;
import com.sams.samsapi.model.ReviewTemplate.Reviews;
import com.sams.samsapi.model.User;
import com.sams.samsapi.model.User.USER_TYPE;
import com.sams.samsapi.persistence.AdminInterface;
import com.sams.samsapi.persistence.AdminOps;
import com.sams.samsapi.persistence.NotificationsInterface;
import com.sams.samsapi.persistence.NotificationsOps;
import com.sams.samsapi.persistence.PaperPool;
import com.sams.samsapi.persistence.PccInterface;
import com.sams.samsapi.persistence.PccOps;
import com.sams.samsapi.persistence.PcmInterface;
import com.sams.samsapi.persistence.PcmOps;
import com.sams.samsapi.persistence.SubmitterInterface;
import com.sams.samsapi.persistence.SubmitterOps;
import com.sams.samsapi.persistence.UsersInterface;
import com.sams.samsapi.persistence.UsersOps;
import com.sams.samsapi.util.CodeSmellFixer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/")
public class SamsController {
    private static final Logger LOG = Logger.getLogger(SamsController.class.getName());
    private SubmitterInterface submitterInterface = new SubmitterOps();
    private UsersInterface usersInterface = new UsersOps();
    private PccInterface pccInterface = new PccOps();
    private PcmInterface pcmInterface = new PcmOps();
    private AdminInterface adminInterface = new AdminOps();
    private NotificationsInterface notificationInterface = new NotificationsOps();

    public SamsController(AssignedPapersUtil assignedPapersUtil, PaperChoicesUtil paperChoicesUtil,
            PaperDetailsUtil paperDetailsUtil,
            PapersUtil papersUtil, ReviewQuestionnaireUtil reviewQuestionnaireUtil, UserUtils userUtils,
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

    @GetMapping("/pcm")
    public ResponseEntity<Object> getAllPcm(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getAvailablePCMs(), HttpStatus.OK);
    }

    @GetMapping("/papers/pcc/assignment/pending")
    public ResponseEntity<Object> getAllPapersPendingAssignment(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getPaperPendingPCCAssignments(), HttpStatus.OK);
    }
    @GetMapping("/papers/pcc/rating/completed")
    public ResponseEntity<Object> getAllPapersCompletedAssignment(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getRatingCompletedPaperDetails(), HttpStatus.OK);
    }
    @PutMapping("/papers/pcc/assignment/assign")
    public ResponseEntity<Object> updatePapersAssignment(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                    || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (!details.containsKey(CodeSmellFixer.SnakeCase.PCM_ID)
                    || !details.containsKey(CodeSmellFixer.SnakeCase.PAPER_ID)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            Boolean status = pccInterface.assignPaperToPCM(
                    Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()),
                    Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PCM_ID).toString()));
            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/papers/pcc/assignment/review/completed")
    public ResponseEntity<Object> getAllPaperAssignment(@RequestHeader Object userId)
            throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ArrayList<ResearchPaper> papers = pccInterface.getAllReviewCompletedPapersDetails();
        return new ResponseEntity<>(papers, HttpStatus.OK);
    }

    @GetMapping("/papers/pcc/assignment/view/{paperId}")
    public ResponseEntity<Object> getPaperAssignment(@RequestHeader Object userId, @PathVariable Integer paperId)
            throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        HashMap<Integer, ReviewTemplate> pcmReviews = pccInterface.viewPCMReviews(paperId);
        return pcmReviews.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(pcmReviews, HttpStatus.OK);
    }

    @GetMapping("/papers/pcc/assignment/choice")
    public ResponseEntity<Object> getPaperChoicesMade(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(pccInterface.getPCMChoices(), HttpStatus.OK);
    }

    @PutMapping("/papers/pcc/rate")
    public ResponseEntity<Object> updatePaperRating(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC)
                    || usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (!details.containsKey(CodeSmellFixer.LowerCase.RATING)
                    || !details.containsKey(CodeSmellFixer.SnakeCase.PAPER_ID)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            pccInterface.ratePaper(Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()),
                    Integer.parseInt(details.get(CodeSmellFixer.LowerCase.RATING).toString()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/papers/pcm/choice")
    public ResponseEntity<Object> createPaperChoices(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (!details.containsKey(CodeSmellFixer.SnakeCase.PAPER_IDS)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            ArrayList<Integer> paperIds = new ArrayList<>();
            Object paperIdsObj = details.get(CodeSmellFixer.SnakeCase.PAPER_IDS);
            if (!(paperIdsObj instanceof ArrayList<?>)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            ArrayList<?> ids = (ArrayList<?>) paperIdsObj;
            for (Object paperId : ids) {
                paperIds.add(Integer.parseInt(paperId.toString()));
            }
            Boolean status = pcmInterface.submitPaperChoices(paperIds, Integer.parseInt(userId.toString()));
            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/papers/pcm/meta")
    public ResponseEntity<Object> getPaperMetaData(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(pcmInterface.getMetaAvailablePaperDetails(Integer.parseInt(userId.toString())),
                HttpStatus.OK);
    }

    @PostMapping("/papers/pcm/review")
    public ResponseEntity<Object> createPaperReview(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (!details.containsKey(CodeSmellFixer.SnakeCase.PAPER_ID)
                    || !details.containsKey(CodeSmellFixer.LowerCase.REVIEWS)
                    || !details.containsKey(CodeSmellFixer.LowerCase.RATING)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            HashMap<Integer, String> reviewData = new HashMap<>();
            Object reviewDataObj = details.get(CodeSmellFixer.LowerCase.REVIEWS);
            if (!(reviewDataObj instanceof HashMap<?, ?>)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            HashMap<?, ?> ids = (HashMap<?, ?>) reviewDataObj;
            for (Object id : ids.keySet()) {
                reviewData.put(Integer.parseInt(id.toString()), ids.get(id).toString());
            }

            Reviews[] review = pcmInterface.getReviewData(reviewData);
            if (review == null) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            ReviewTemplate template = pcmInterface.getReviewTemplate(Integer.parseInt(userId.toString()),
                    Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()));

            template.setRating(Integer.parseInt(details.get(CodeSmellFixer.LowerCase.RATING).toString()));
            template.setReviews(review);

            Boolean status = pcmInterface.submitReview(
                    Integer.parseInt(details.get(CodeSmellFixer.SnakeCase.PAPER_ID).toString()), template);

            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/papers/deadline")
    public ResponseEntity<Object> updateDeadline(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (!details.containsKey(CodeSmellFixer.LowerCase.TYPE)
                    || !details.containsKey(CodeSmellFixer.LowerCase.DEADLINE)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            Boolean status = adminInterface.updateDeadlines(
                    TYPE.valueOf(details.get(CodeSmellFixer.LowerCase.TYPE).toString()),
                    Long.valueOf(details.get(CodeSmellFixer.LowerCase.DEADLINE).toString()));
            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/papers/template")
    public ResponseEntity<Object> updateTemplate(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (!details.containsKey(CodeSmellFixer.LowerCase.REVIEW)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            HashMap<Integer, String> reviewData = new HashMap<>();
            Object reviewDataObj = details.get(CodeSmellFixer.LowerCase.REVIEW);
            if (!(reviewDataObj instanceof HashMap<?, ?>)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }

            HashMap<?, ?> ids = (HashMap<?, ?>) reviewDataObj;
            for (Object id : ids.keySet()) {
                reviewData.put(Integer.parseInt(id.toString()), ids.get(id).toString());
            }

            Boolean status = adminInterface.updateTemplate(reviewData);
            return Boolean.TRUE.equals(status) ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("user/authenticate")
    public ResponseEntity<HashMap<String, Object>> authenticateUser(@RequestBody HashMap<String, String> credentials) {
        LOG.log(Level.INFO, "POST /user/authenticate {0}", new Object[] { credentials });
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (!credentials.containsKey(CodeSmellFixer.LowerCase.USERNAME)
                    || !credentials.containsKey(CodeSmellFixer.LowerCase.PASSWORD)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            String userName = credentials.get(CodeSmellFixer.LowerCase.USERNAME);
            String password = credentials.get(CodeSmellFixer.LowerCase.PASSWORD);

            User user = usersInterface.authenticateUser(userName, password);

            if (user != null) {
                response.put(CodeSmellFixer.SnakeCase.USER_ID, user.getId());
                response.put(CodeSmellFixer.LowerCase.USERNAME, user.getName());
                response.put(CodeSmellFixer.LowerCase.PASSWORD, user.getPassword());
                response.put(CodeSmellFixer.LowerCase.TYPE, user.getType());
            }

            return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("user/register")
    public ResponseEntity<HashMap<String, Object>> registeruser(@RequestBody HashMap<String, String> credentials) {
        LOG.log(Level.INFO, "POST /user/register {0}", new Object[] { credentials });
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (!credentials.containsKey(CodeSmellFixer.LowerCase.USERNAME)
                    || !credentials.containsKey(CodeSmellFixer.LowerCase.PASSWORD)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            String userName = credentials.get(CodeSmellFixer.LowerCase.USERNAME);
            String password = credentials.get(CodeSmellFixer.LowerCase.PASSWORD);
            String type = credentials.get(CodeSmellFixer.LowerCase.TYPE);
            User user = usersInterface.registerUser(userName, password, USER_TYPE.valueOf(type));
            if (user != null) {
                response.put(CodeSmellFixer.LowerCase.REGISTRATION, CodeSmellFixer.LowerCase.SUCCESS);
                response.put(CodeSmellFixer.LowerCase.USERNAME, user.getName());
                response.put(CodeSmellFixer.LowerCase.ID, user.getId());
                response.put(CodeSmellFixer.LowerCase.TYPE, user.getType());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/notification")
    public ResponseEntity<Object> getNotification(@RequestHeader Object userId) throws Exception {
        Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM) ||
                usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) ||
                usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN) ||
                usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.SUBMITTER);
        if (Boolean.FALSE.equals(isValidUser)) {
            LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(notificationInterface.getNotificationForUser(Integer.parseInt(userId.toString())),
                HttpStatus.OK);
    }

    @PutMapping("user/notification")
    public ResponseEntity<Object> updateNotification(@RequestHeader Object userId,
            @RequestBody HashMap<String, Object> details) throws Exception {
        try {
            Boolean isValidUser = usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCM) ||
                    usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.PCC) ||
                    usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.ADMIN) ||
                    usersInterface.authenticateUser(Integer.parseInt(userId.toString()), USER_TYPE.SUBMITTER);
            if (Boolean.FALSE.equals(isValidUser)) {
                LOG.log(Level.SEVERE, CodeSmellFixer.LoggerCase.USER_UN_AUTHORIZED, userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (!details.containsKey(CodeSmellFixer.LowerCase.IDS)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            ArrayList<Integer> notificationIds = new ArrayList<>();
            Object notificationIdsObj = details.get(CodeSmellFixer.LowerCase.IDS);
            if (!(notificationIdsObj instanceof ArrayList<?>)) {
                CodeSmellFixer.ExceptionThrower.throwInvalidBody();
            }
            ArrayList<?> ids = (ArrayList<?>) notificationIdsObj;
            for (Object notificationId : ids) {
                notificationIds.add(Integer.parseInt(notificationId.toString()));
            }
            notificationInterface.updateNotificationForUser(Integer.parseInt(userId.toString()), notificationIds);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage() != null) {
                LOG.log(Level.INFO, CodeSmellFixer.LoggerCase.EXCEPTION, ex.getMessage());
            }
            LOG.log(Level.SEVERE, CodeSmellFixer.UpperCase.INVALID_BODY);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
