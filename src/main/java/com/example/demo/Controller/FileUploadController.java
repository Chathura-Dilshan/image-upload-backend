package com.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class FileUploadController {
    private static final Logger logger = Logger.getLogger(FileUploadController.class.getName());
    private static String UPLOADED_FOLDER = "E://temp//"; // Change this

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file ) throws Exception {
        if (file == null) {
            throw new RuntimeException("You must select the a file for uploading");
        }
        String name = this.imageUpload(file);
        return new ResponseEntity<String>(name, HttpStatus.OK);
    }

    @PostMapping("/uploadMultipleData")
    public ResponseEntity<ArrayList<String>> uploadMultipleData(@RequestParam("files") MultipartFile[] file) throws Exception {
        if (file.length == 0) {
            throw new RuntimeException("You must select the a file for uploading");
        }
        ArrayList<String> name = new ArrayList<>();

        for (MultipartFile multipartFile : file) {
           String orName=this.imageUpload(multipartFile);
           name.add(orName);
        }
        return new ResponseEntity<ArrayList<String>>(name, HttpStatus.OK);
    }


    private String imageUpload(MultipartFile multipartFile){
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String originalName = multipartFile.getOriginalFilename();
        String name = multipartFile.getName();
        String contentType = multipartFile.getContentType();
        long size = multipartFile.getSize();
        logger.info("inputStream: " + inputStream);
        logger.info("originalName: " + originalName);
        logger.info("name: " + name);
        logger.info("contentType: " + contentType);
        logger.info("size: " + size);
        // Do processing with uploaded file data in Service layer

        try {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + multipartFile.getOriginalFilename());
            Files.write(path, bytes);

            logger.info("successful: " + "successful");


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return originalName;
    }
}
