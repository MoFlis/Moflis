package com.project.moflis.storage;

import com.project.moflis.util.FileNameConflictResolver;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

@Component
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.credentials.accessKey}")
    private String accessKey;

    @Value("${aws.credentials.secretKey}")
    private String secretKey;

    private S3Client s3Client;

    @PostConstruct
    public void init() {
        System.out.println("Bucket Name: " + bucketName);
        System.out.println("Region: " + region);
        System.out.println("Access Key: " + accessKey);
        System.out.println("Secret Key: " + secretKey);

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
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
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            s3Client.putObject(putObjectRequest, tempFile.toPath());
            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3Key;
        } catch (Exception e) {
            throw new IOException("S3 업로드 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
