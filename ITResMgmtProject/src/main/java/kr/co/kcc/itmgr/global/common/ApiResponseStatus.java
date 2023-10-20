package kr.co.kcc.itmgr.global.common;

import org.springframework.http.HttpStatus;

public enum ApiResponseStatus {
	SUCCESS(HttpStatus.OK, 200, "성공"),
	
	FAIL(HttpStatus.BAD_REQUEST, 400, "실패"),
	
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, 1001, "런타임 에러"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, 1002, "접근 권한 에러"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1003, "서버 에러"),
    
    SERVER_PING_ALIVE(2000, "서버가 살아있습니다."),
    SERVER_PING_DIE(2001, "서버가 죽었습니다."),
    SECURITY(HttpStatus.UNAUTHORIZED, 3001, "로그인이 필요합니다"),
    EXCEL_INCORRECT_IP(5001, "잘못된 IP 주소가 존재합니다."),
    EXCEL_INCORRECT_IPCODE(5002, "잘못된 IP 유형이 존재합니다."),
	EXCEL_DUPLICATE_DATA(5003, "중복된 IP가 존재합니다."),
	EXCEL_EMPTY_DATA(5004, "입력할 행이 존재하지 않습니다.");
	
    private HttpStatus status;
    private int code;
    private String message;

    ApiResponseStatus(HttpStatus status, int code) {
        this.status = status;
        this.code = code;
    }

    ApiResponseStatus(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    
    ApiResponseStatus(int code, String message){
    	this.code = code;
    	this.message = message;
    }
	
	public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
