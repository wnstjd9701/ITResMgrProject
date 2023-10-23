package kr.co.kcc.itmgr.domain.employee.service;

import java.util.List;
import org.springframework.stereotype.Service;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.employee.dao.IEmployeeRepository;
import kr.co.kcc.itmgr.domain.employee.model.Employee;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

	private final IEmployeeRepository employeeRepository;

	@Override
	public List<Employee> selectAllEmployee(){
		return employeeRepository.selectAllEmployee();
	}


	@Override
	public void insertEmployee(List<Employee> employee) {
		employeeRepository.insertEmployee(employee);
	}

	@Override
	public void deleteEmployeeByUseYN(String employeeId) {
		employeeRepository.deleteEmployeeByUseYN(employeeId);		
	}


	@Override
	public List<Employee> selectSearchEmployee(String employeeTypeCode, String employeeStatusCode, String searchText) {
		return employeeRepository.selectSearchEmployee(employeeTypeCode, employeeStatusCode, searchText);
	}

	@Override
	public void updateEmployee(List<Employee> updatedEmployeeInfo) {
		employeeRepository.updateEmployee(updatedEmployeeInfo);
		
	}


	@Override
	public List<CommonCodeDetail> commonCodeEmpType() {
		return employeeRepository.commonCodeEmpType();
	}


	@Override
	public List<CommonCodeDetail> commonCodeEmpStatus() {
		return employeeRepository.commonCodeEmpStatus();
	}


	@Override
	public Employee selectEmployeePwd(String employeeId) {
		return employeeRepository.selectEmployeePwd(employeeId);
	}
}
