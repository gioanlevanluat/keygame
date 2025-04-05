package com.keygame.config.exceptionhandler;

import com.keygame.config.ResponseConfig;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlingController {
    public static final String SYSTEM_ERROR = "The system encountered a problem. Please contact us for processing.";
    public static final String MAX_UPLOAD_SIZE = "The uploaded file is larger than allowed. Please upload a file with a smaller size.";

    @ExceptionHandler({IllegalArgumentException.class, UnexpectedTypeException.class, BindException.class, HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<?> handleBadRequestException(Exception ex) {
        log.error("handleBadRequestException: ", ex);
        return ResponseConfig.error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception ex) {
        log.error("handleException: ", ex);
        return ResponseConfig.error(SYSTEM_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<?> handleBusinessException(BusinessException ex) {
        log.error("handleBusinessException: ", ex);
        return ResponseConfig.error(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(value = AuthException.class)
    protected ResponseEntity<?> handleAuthException(AuthException ex) {
        log.error("handleAuthException: ", ex);
        return ResponseConfig.error(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        log.error("handleMaxSizeException: ", ex);
        return ResponseConfig.error(MAX_UPLOAD_SIZE);
    }
}
