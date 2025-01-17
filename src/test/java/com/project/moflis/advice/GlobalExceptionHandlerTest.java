package com.project.moflis.advice;

import com.project.moflis.controller.ProfileController;
import com.project.moflis.exception.ImageUploadException;
import com.project.moflis.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    void handleImageUploadException() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                new byte[0]
        );

        // 서비스가 호출될 때 예외를 던지도록 설정
        doThrow(new ImageUploadException("테스트 실패 케이스")).when(profileService).addProfileImage(anyInt(), any(MultipartFile.class));

        mockMvc.perform(multipart("/api/v1/users/{userId}/profileImage", 1)
                        .file(file))
                .andDo(print()) // 디버깅용 출력
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("프로필 이미지 업로드 실패 : 테스트 실패 케이스"));
    }
}

