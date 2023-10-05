package kr.co.kcc.itmgr.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode implements EnumModel {

  // COMMON
  INVALID_CODE(400, "C001", "Invalid Code"),
  RESOURCE_NOT_FOUND(204, "C002", "Resource not found"),
  EXPIRED_CODE(400, "C003", "Expired Code"),
  
  // AWS
  AWS_ERROR(400, "A001", "aws client error");

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
