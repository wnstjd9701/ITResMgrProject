package kr.co.kcc.itmgr.domain.employee.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.employee.model.Employee;

public interface IEmployeeService {
	//사원 전체조회
	List<Employee> selectAllEmployee();
}
