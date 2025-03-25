package com.project.moflis.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class AwsConfig {

    private final FileConfig fileConfig;

    public AwsConfig(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(fileConfig.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(fileConfig.getAccessKey(), fileConfig.getSecretKey())))
                .serviceConfiguration(S3Configuration.builder().checksumValidationEnabled(false).build())
                .build();
    }
}
