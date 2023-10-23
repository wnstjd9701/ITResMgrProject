package kr.co.kcc.itmgr.global.common;

import org.springframework.http.HttpStatus;

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
    
    public static ApiResponse<?> successDetailMessage(int code, String message){
    	return new ApiResponse<>(ApiResponseStatus.SUCCESS.getStatus(), code, message);
    }
    
    public static ApiResponse<?> fail(int code, String message){
    	return new ApiResponse<>(code, message);
    }
    
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
