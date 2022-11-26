package com.sams.samsapi.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import com.sams.samsapi.model.ResearchPaper;
import com.sams.samsapi.model.Submitter;
import com.sams.samsapi.model.User;
import com.sams.samsapi.persistence.Users;
import com.sams.samsapi.service.FileUploadService;
import com.sams.samsapi.json_crud_utils.PapersUtil;
import com.sams.samsapi.json_crud_utils.UserUtils;

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
        int next_paper_id = PapersUtil.getNextPaperId();
        String uploadedFileName = fileBytes.getOriginalFilename();
        String[] uploadedFileExtension = uploadedFileName.split("\\.");
        String extension = uploadedFileExtension[uploadedFileExtension.length - 1];
        int revisionNo = 1;
        String fileName = data.get("title") + revisionNo + "." + extension;
        List<String> authors = new ArrayList<>(Arrays.asList(data.get("authors").split(",")));
        boolean insertedInfo = PapersUtil.insertPaperDetails(data.get("title"),
                Integer.parseInt(data.get("submitterId")), // TODO:
                authors,
                data.get("contact"),
                fileName,
                extension,
                next_paper_id,
                revisionNo);

        FileUploadService fileuploadservice = new FileUploadService();
        boolean uploadedFile = fileuploadservice.uploadFile(uploadPath + fileName, fileBytes);

        if (insertedInfo && uploadedFile) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean revisePaperForm(String uploadPath, HashMap<String, String> data, MultipartFile fileBytes,
            int paper_id) throws IllegalStateException, IOException {
        HashMap<Integer, ResearchPaper> paperSubmissions = PapersUtil.getPaperDetailsBasedOnPaperId(paper_id);
        // fetching latest revision
        int max = 1;
        for (ResearchPaper paper : paperSubmissions.values()) {
            if (paper.getRevisionNo() > max) {
                max = paper.getRevisionNo();
            }
        }
        String uploadedFileName = fileBytes.getOriginalFilename();
        String[] uploadedFileExtension = uploadedFileName.split("\\.");
        String extension = uploadedFileExtension[uploadedFileExtension.length - 1];
        int revisionNo = max + 1;
        String fileName = data.get("title") + revisionNo + "." + extension;
        List<String> authors = new ArrayList<>(Arrays.asList(data.get("authors").split(",")));
        boolean insertedInfo = PapersUtil.insertPaperDetails(data.get("title"),
                Integer.parseInt(data.get("submitterId")),
                authors,
                data.get("contact"),
                fileName,
                extension,
                paper_id,
                revisionNo);

        FileUploadService fileuploadservice = new FileUploadService();
        boolean uploadedFile = fileuploadservice.uploadFile(uploadPath + fileName, fileBytes);

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
