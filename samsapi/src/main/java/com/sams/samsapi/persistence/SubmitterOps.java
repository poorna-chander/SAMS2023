package com.sams.samsapi.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sams.samsapi.crud_utils.PaperPoolUtil;
import com.sams.samsapi.crud_utils.UserUtils;
import com.sams.samsapi.modelTemplates.ResearchPaper;
import com.sams.samsapi.modelTemplates.User;
import com.sams.samsapi.service.FileUploadService;
import com.sams.samsapi.util.CodeSmellFixer;

public class SubmitterOps implements SubmitterInterface {

    private int id;
    private User name;

    public SubmitterOps() {
        this.id = 1; // TODO: MAP TO USER USING
        this.name = UserUtils.getUserDetails(this.id);
    }

    @Override
    public boolean SubmitPaperForm(String uploadPath, HashMap<String, String> data, MultipartFile fileBytes)
            throws IllegalStateException, IOException {
        int next_paper_id = PaperPoolUtil.getNextPaperId();
        String uploadedFileName = fileBytes.getOriginalFilename();
        String[] uploadedFileExtension = uploadedFileName.split("\\.");
        String extension = uploadedFileExtension[uploadedFileExtension.length - 1];
        int revisionNo = 1;
        String fileName = data.get("title") + revisionNo;
        List<String> authors = new ArrayList<>(Arrays.asList(data.get("authors").split(",")));
        FileUploadService fileuploadservice = new FileUploadService();
        boolean uploadedFile = fileuploadservice.uploadFile(uploadPath + fileName + "." + extension, fileBytes);
        boolean insertedInfo = false;
        if (uploadedFile) {
            // insertedInfo = PapersUtil.insertPaperDetails(data.get("title"),
            //         Integer.parseInt(data.get("submitterId")), // TODO:
            //         authors,
            //         data.get("contact"),
            //         fileName,
            //         extension,
            //         next_paper_id,
            //         revisionNo);
                    HashMap<String, Object> paperDetails = new HashMap<>();
                    paperDetails.put(CodeSmellFixer.LowerCase.TITLE, data.get(CodeSmellFixer.LowerCase.TITLE));
                    paperDetails.put(CodeSmellFixer.SnakeCase.SUBMITTER_ID, Integer.parseInt(data.get(CodeSmellFixer.CamelCase.SUBMITTER_ID)));
                    paperDetails.put(CodeSmellFixer.LowerCase.AUTHORS, authors);
                    paperDetails.put(CodeSmellFixer.LowerCase.CONTACT, data.get(CodeSmellFixer.LowerCase.CONTACT));
                    paperDetails.put(CodeSmellFixer.SnakeCase.FILE_NAME, fileName);
                    paperDetails.put(CodeSmellFixer.SnakeCase.FILE_EXTENSION, extension);
                    paperDetails.put(CodeSmellFixer.SnakeCase.PAPER_ID, next_paper_id);
                    paperDetails.put(CodeSmellFixer.SnakeCase.REVISION_NO, revisionNo);
                    insertedInfo = new PaperPool().createNewPaper(paperDetails);
        }

        if (insertedInfo && uploadedFile) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean revisePaperForm(String uploadPath, HashMap<String, String> data, MultipartFile fileBytes,
            int paper_id) throws IllegalStateException, IOException {
        HashMap<Integer, ResearchPaper> paperSubmissions = PaperPoolUtil.getPaperDetailsBasedOnPaperId(paper_id);
        // fetching latest revision
        int max = 1;
        for (ResearchPaper paper : paperSubmissions.values()) {
            if (paper.getRevisionNo() > max) {
                max = paper.getRevisionNo();
            }
        }
        Boolean isNewFile = fileBytes != null;
        String oldRevisionFileName = new PaperPool().getPaperDetails(paper_id).getFileName() + "." + new PaperPool().getPaperDetails(paper_id).getFileExtension();
        String uploadedFileName = Boolean.TRUE.equals(isNewFile) ? fileBytes.getOriginalFilename() : oldRevisionFileName;
        String[] uploadedFileExtension = uploadedFileName.split("\\.");
        String extension = uploadedFileExtension[uploadedFileExtension.length - 1];
        int revisionNo = max + 1;
        String fileName = data.get("title") + revisionNo;
        List<String> authors = new ArrayList<>(Arrays.asList(data.get("authors").split(",")));
        FileUploadService fileuploadservice = new FileUploadService();
        boolean uploadedFile = Boolean.TRUE.equals(isNewFile) ? fileuploadservice.uploadFile(uploadPath + fileName + "." + extension, fileBytes) : fileuploadservice.copyFile(uploadPath + fileName + "." + extension, uploadPath + oldRevisionFileName);

        boolean insertedInfo = false;
        if (uploadedFile) {
            // insertedInfo = PapersUtil.insertPaperDetails(data.get("title"),
            //         Integer.parseInt(data.get("submitterId")),
            //         authors,
            //         data.get("contact"),
            //         fileName,
            //         extension,
            //         paper_id,
            //         revisionNo);

                    HashMap<String, Object> paperDetails = new HashMap<>();
                    paperDetails.put(CodeSmellFixer.LowerCase.TITLE, data.get(CodeSmellFixer.LowerCase.TITLE));
                    paperDetails.put(CodeSmellFixer.SnakeCase.SUBMITTER_ID, Integer.parseInt(data.get(CodeSmellFixer.CamelCase.SUBMITTER_ID)));
                    paperDetails.put(CodeSmellFixer.LowerCase.AUTHORS, authors);
                    paperDetails.put(CodeSmellFixer.LowerCase.CONTACT, data.get(CodeSmellFixer.LowerCase.CONTACT));
                    paperDetails.put(CodeSmellFixer.SnakeCase.FILE_NAME, fileName);
                    paperDetails.put(CodeSmellFixer.SnakeCase.FILE_EXTENSION, extension);
                    paperDetails.put(CodeSmellFixer.SnakeCase.PAPER_ID, paper_id);
                    paperDetails.put(CodeSmellFixer.SnakeCase.REVISION_NO, revisionNo);
                    insertedInfo = new PaperPool().createNewPaper(paperDetails);
        }

        if (insertedInfo && uploadedFile) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public HashMap<String, HashMap<String, String>> getSubmissionDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<String, Integer> viewFinalRating() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateFormFile(HashMap<String, String> data, MultipartFile fileBytes) {
        // TODO Auto-generated method stub

        // extension chesk
        String uploadedFileName = fileBytes.getOriginalFilename();
        String[] uploadedFileExtension = uploadedFileName.split("\\.");
        String extension = uploadedFileExtension[uploadedFileExtension.length - 1];
        if (!(extension.equals("pdf") || extension.equals("docx"))) {
            return false;
        }
        return true;
    }

}
