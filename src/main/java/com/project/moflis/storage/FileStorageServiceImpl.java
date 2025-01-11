package com.project.moflis.storage;

import com.project.moflis.util.FileNameConflictResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public void createDirectoryIfNotExists(String fileName, String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }
    }

    @Override
    public String saveFile(String fileName, String path, MultipartFile file) throws IOException {
        if (FileNameConflictResolver.isFileNameConflict(fileName, path)) {
            fileName = FileNameConflictResolver.generateUniqueFileName(fileName, path);
        }
        String fullPath = path + "/" + fileName;
        file.transferTo(new File(fullPath));
        return fullPath;
    }

}
