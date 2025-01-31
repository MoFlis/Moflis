package com.project.moflis.advice;

import com.project.moflis.exception.AddressNotFoundException;
import com.project.moflis.exception.ImageUploadException;
import com.project.moflis.exception.UserLocationAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, Exception e) {
        logger.error("Error occurred: {}", message, e);

        ErrorResponse errorResponse = new ErrorResponse(message, status.value());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<ErrorResponse> handleImageUploadException(ImageUploadException e) {
        return buildErrorResponse("프로필 이미지 업로드 중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException e) {
        return buildErrorResponse("주소를 찾을 수 없습니다.", e.getStatus(), e);
    }

    @ExceptionHandler(UserLocationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserLocationAlreadyExistsException(UserLocationAlreadyExistsException e) {
        return buildErrorResponse("이미 해당 위치가 등록되어 있습니다.", HttpStatus.CONFLICT, e);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        return buildErrorResponse("서버에서 알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
}
