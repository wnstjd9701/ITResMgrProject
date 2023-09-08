package kr.co.kcc.itmgr.domain.employee.model;

import java.sql.Timestamp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Employee {
	private String employeeId;
	private String employeePwd;
	private String employeeName;
	private String employeeTypeCode;
	private String employeeStatusCode;
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	
	private String employeeType;
	private String employeeStatus;
}
