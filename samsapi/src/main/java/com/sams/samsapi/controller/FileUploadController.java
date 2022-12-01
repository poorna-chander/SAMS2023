package com.sams.samsapi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import com.sams.samsapi.service.FileUploadService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/")
public class FileUploadController {
    @Autowired
    FileUploadService fileuploadservice;

    @PostMapping("/upload")
    public void uploadFile(@Value("${filelocation.savepath}") String uploadPath, MultipartFile file)
            throws IllegalStateException, IOException {
        fileuploadservice.uploadFile(uploadPath, file);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Object> downloadFile(@Value("${filelocation.savepath}") String filepath,
            @PathVariable String filename)
            throws FileNotFoundException {

        File file = fileuploadservice.downloadFile(filepath, filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

}
