package com.sams.samsapi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
    public boolean uploadFile(String uploadPath, MultipartFile file)
            throws IllegalStateException, IOException {
        file.transferTo(new File(uploadPath));
        return true;
    }

    public File downloadFile(String filePath, String filename) {
        File file = new File(filePath + filename);
        return file;

    }
}
