package kr.co.kcc.itmgr.domain.employee.service;

import java.util.List;


import org.springframework.stereotype.Service;

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
	
}
