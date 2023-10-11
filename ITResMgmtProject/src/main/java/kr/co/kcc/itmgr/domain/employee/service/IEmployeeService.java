package kr.co.kcc.itmgr.domain.employee.service;

import java.util.List;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.employee.model.Employee;

public interface IEmployeeService {
	//사원 전체조회
	List<Employee> selectAllEmployee();
	
	//사원 검색
	List<Employee> selectSearchEmployee(String employeeTypeCode, String employeeStatusCode, String searchText);

	//사원등록
	void insertEmployee(List<Employee> employee);

	//사원삭제(사용여부:N)
	void deleteEmployeeByUseYN(String employeeId);
	
	//사원수정
	void updateEmployee(List<Employee> employee);

	//공통코드 사원유형
	List<CommonCodeDetail> commonCodeEmpType();

	//공통코드 사원상태
	List<CommonCodeDetail> commonCodeEmpStatus();

	//사원 비밀번호조회
	Employee selectEmployeePwd(String employeeId);
}
