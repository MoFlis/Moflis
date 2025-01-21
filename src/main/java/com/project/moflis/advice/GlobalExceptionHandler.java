package com.project.moflis.advice;

import com.project.moflis.exception.AddressNotFoundException;
import com.project.moflis.exception.ImageUploadException;
import com.project.moflis.exception.UserLocationAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<String> handleImageUploadException(ImageUploadException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("프로필 이미지 업로드 실패 : " + e.getMessage());

    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<String> handleAddressNotFoundException(AddressNotFoundException e) {
        return ResponseEntity.status(e.getStatus())
                .body("주소검색 실패" + e.getMessage());
    }

    @ExceptionHandler(UserLocationAlreadyExistsException.class)
    public ResponseEntity<String> handleUserLocationAlreadyExistsException(UserLocationAlreadyExistsException e) {
        return ResponseEntity.status(e.getStatus())
                .body(e.getMessage());
    }
}
