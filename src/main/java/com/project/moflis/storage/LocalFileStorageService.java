package com.project.moflis.storage;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LocalFileStorageService implements FileStorageService {

    @Override
    public boolean isFileNameConflict(String fileName, String path) {
        String saveFilePath = path + "/" + fileName;
        File file = new File(saveFilePath);
        return file.exists();
    }

    @Override
    public String generateUniqueFileName(String fileName, String path) {
        int period = fileName.lastIndexOf(".");
        String fileBaseName = fileName.substring(0, period); // 파일명 (확장자 제외)
        String fileSuffix = fileName.substring(period); // 확장자

        String saveFilePath = path + "/" + fileName;
        File file = new File(saveFilePath);

        int idx = 1;

        while (file.exists()) {
            StringBuilder newFileName = new StringBuilder();
            newFileName.append(fileBaseName);
            newFileName.append(idx++);
            newFileName.append(fileSuffix);

            fileName = newFileName.toString();
            saveFilePath = path + "/" + fileName;
            file = new File(saveFilePath);
        }
        return fileName;
    }
}
