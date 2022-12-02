package com.sams.samsapi.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    public boolean copyFile(String uploadPath, String oldFilePath)
            throws IllegalStateException, IOException {
        Path copied = Paths.get(IMAGE_FOLDER + uploadPath);
        Path originalPath = Paths.get(IMAGE_FOLDER + oldFilePath);
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        return true;
    }

    public File downloadFile(String filePath, String filename) {
        File file = new File(filePath + filename);
        return file;

    }
}
