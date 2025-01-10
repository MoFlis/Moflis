package com.project.moflis.util;

import com.project.moflis.storage.FileStorageService;

public class FileNameConflictResolver {

    private final FileStorageService fileStorageService;

    public FileNameConflictResolver(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public boolean isFileNameConflict(String fileName, String path) {
        return fileStorageService.isFileNameConflict(fileName, path);
    }

    public String generateUniqueFileName(String fileName, String path) {
        return fileStorageService.generateUniqueFileName(fileName, path);
    }
}
