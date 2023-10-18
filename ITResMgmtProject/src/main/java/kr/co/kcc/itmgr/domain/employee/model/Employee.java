package kr.co.kcc.itmgr.domain.employee.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;


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
	private String useYN;
	
	private String employeeType;
	private String employeeStatus;
	private List<Employee> employee;
	private List<String> deletedEmployeeIds;
	private List<Employee> updatedEmployeeInfo;

}
