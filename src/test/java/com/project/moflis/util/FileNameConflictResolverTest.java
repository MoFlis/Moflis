package com.project.moflis.util;

import com.project.moflis.storage.FileStorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FileNameConflictResolverTest {

    @Autowired
    private FileStorageService fileStorageService;

    private String tempDir;

    @BeforeEach
    void setUp() {
        // 임시 디렉토리
        tempDir = System.getProperty("java.io.tmpdir") + "/test_file_conflict";
        File dir = new File(tempDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @AfterEach
    void tearDown() {
        // 임시 디렉터리 및 파일 삭제
        File dir = new File(tempDir);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        dir.delete();
    }

    @Test
    void testCheckSameFileNameNoConflict() {
        // Given: 디렉터리에 없는 파일 이름 설정
        String fileName = "test.txt";

        // When: 메서드 호출
        String resolvedName = fileStorageService.generateUniqueFileName(fileName, tempDir);

        // Then: 반환된 이름이 입력한 이름과 동일해야 함
        assertThat(resolvedName).isEqualTo(fileName);
    }

    @Test
    void testCheckSameFileNameConflict() throws IOException {
        // Given: 파일 생성
        String fileName = "test.txt";
        File existingFile = new File(tempDir + "/" + fileName);
        existingFile.createNewFile(); //파일이 없으면 생성 true 반환 있으면 생성안하고 false반환

        // When: 메서드 호출
        String resolvedName = fileStorageService.generateUniqueFileName(fileName, tempDir);

        // Then: resolvedName이 기존에 파일과 이름이 달라야함
        assertThat(resolvedName).isNotEqualTo(fileName);
        assertThat(resolvedName).startsWith("test"); // 이름이 "test"로 시작
        assertThat(resolvedName).endsWith(".txt");  // 확장자가 ".txt"
    }

}
