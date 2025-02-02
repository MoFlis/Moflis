package com.project.moflis.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    void createDirectoryIfNotExists(String fileName, String path);

    String saveFile(String fileName, String path, MultipartFile file) throws IOException;

    String uploadToS3(String fileName, MultipartFile file) throws IOException;
}
