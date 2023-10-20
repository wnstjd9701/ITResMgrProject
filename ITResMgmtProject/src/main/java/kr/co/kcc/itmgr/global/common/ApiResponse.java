package kr.co.kcc.itmgr.global.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

	private HttpStatus status;
	private int code;
	private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data){
    	return new ApiResponse<>(ApiResponseStatus.SUCCESS.getStatus(), ApiResponseStatus.SUCCESS.getCode(), ApiResponseStatus.SUCCESS.getMessage(), data);
    }

    public static ApiResponse<?> successWithNoData() {
    	return new ApiResponse<>(ApiResponseStatus.SUCCESS.getStatus(), ApiResponseStatus.SUCCESS.getCode(), ApiResponseStatus.SUCCESS.getMessage());
    }
    
    public static ApiResponse<?> fail(int code, String message){
    	return new ApiResponse<>(code, message);
    }
	/*
	 * // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환 public static
	 * ApiResponse<?> validError(BindingResult bindingResult) { Map<String, String>
	 * errors = new HashMap<>();
	 * 
	 * List<ObjectError> allErrors = bindingResult.getAllErrors(); for (ObjectError
	 * error : allErrors) { if (error instanceof FieldError) {
	 * errors.put(((FieldError) error).getField(), error.getDefaultMessage()); }
	 * else { errors.put(error.getObjectName(), error.getDefaultMessage()); } }
	 * return new ApiResponse<>(ApiResponseStatus.FAIL.getStatus(),
	 * ApiResponseStatus.FAIL.getCode(), errors); }
	 */
    
    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<?> error(String errorText) {
        return new ApiResponse<>(ApiResponseStatus.FAIL.getStatus(), ApiResponseStatus.FAIL.getCode(), ApiResponseStatus.FAIL.getMessage());
    }

    private ApiResponse(HttpStatus status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    private ApiResponse(HttpStatus status, int code, String message) {
    	this.status = status;
    	this.code = code;
    	this.message = message;
    }
    
    private ApiResponse(int code, String message) {
    	this.code = code;
    	this.message = message;
    }
}
