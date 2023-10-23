package kr.co.kcc.itmgr.global.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import kr.co.kcc.itmgr.global.common.ApiResponse;
import kr.co.kcc.itmgr.global.common.ApiResponseStatus;
import lombok.extern.slf4j.Slf4j;

//모든 컨트롤러에서 발생하는 예외를 잡음
@ControllerAdvice 
@Slf4j
public class ExceptionController{

	// 400
	@ExceptionHandler({
		RuntimeException.class
	})
	public ResponseEntity<Object> BadRequestException(final RuntimeException ex){
		log.warn("error", ex);
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	// 401
	@ExceptionHandler({
		AccessDeniedException.class
	})
	public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex) {
		log.warn("error", ex);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	}

	// 500
	@ExceptionHandler({ Exception.class }) 
	public ResponseEntity<?> handleAll(final Exception ex) { 
		log.info(ex.getClass().getName());
		log.error("error??", ex); 
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(500, ApiResponseStatus.FAIL.getMessage())); 
	}


}
