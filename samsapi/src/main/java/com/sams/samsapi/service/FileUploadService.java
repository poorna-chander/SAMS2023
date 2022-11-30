package com.sams.samsapi.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
    private static final String IMAGE_FOLDER = new File(System.getProperty("user.dir")).getParentFile().getPath() + "/samsapi/";
    public boolean uploadFile(String uploadPath, MultipartFile file)
            throws IllegalStateException, IOException {
        file.transferTo(new File(IMAGE_FOLDER + uploadPath));
        return true;
    }

    public File downloadFile(String filePath, String filename) {
        File file = new File(filePath + filename);
        return file;

    }
}
