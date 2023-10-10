package kr.co.kcc.itmgr.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode implements EnumModel {

	INVALID_CODE_GROUP_ID(400, "InvalidParams", "같은 코드 그룹 내에 상세 코드가 중복되었습니다.");
	

	private int status;
	private String code;
	private String message;
	private String detail;

	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	@Override
	public String getKey() {
		return this.code;
	}

	@Override
	public String getValue() {
		return this.message;
	}
}
