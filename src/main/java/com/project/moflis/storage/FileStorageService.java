package com.project.moflis.storage;

public interface FileStorageService {

    boolean isFileNameConflict(String fileName, String path);

    String generateUniqueFileName(String fileName, String path);
}
