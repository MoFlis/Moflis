package com.project.moflis.storage;

import com.project.moflis.config.FileConfig;
import com.project.moflis.util.FileNameConflictResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

@Component
public class FileStorageServiceImpl implements FileStorageService {

    private final FileConfig config;
    private S3Client s3Client;

    public FileStorageServiceImpl(FileConfig config) {
        this.config = config;
    }

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

    @Override
    public String uploadToS3(String fileName, MultipartFile file) throws IOException {
        // S3 업로드 로직
        try {
            File tempFile = File.createTempFile("upload", fileName);
            file.transferTo(tempFile);

            String s3Key = "profiles/" + fileName; // S3에 저장될 경로
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(config.getBucketName())
                    .key(s3Key)
                    .build();

            s3Client.putObject(putObjectRequest, tempFile.toPath());
            return config.getBaseUrl() + "/" + s3Key;
        } catch (Exception e) {
            throw new IOException("S3 업로드 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
