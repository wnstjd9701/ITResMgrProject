package kr.co.kcc.itmgr.domain.user.model;

import lombok.Data;

@Data
public class User {
	private String employeeId;
	private String employeePwd;
	private String employeeName;
	private String employeeTypeCode;
	private String employeeStatusCode;
	private String useYN;
}
