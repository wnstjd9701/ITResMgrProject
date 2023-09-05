package kr.co.kcc.itmgr.domain.employee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.employee.model.Employee;

@Repository
@Mapper
public interface IEmployeeRepository {
	//사원 전체조회
	List<Employee> selectAllEmployee();
	
	//사원등록
	void insertEmployee(Employee employee);
	
	//사원삭제(사용여부:N)
	void updateEmployeeByUseYN(String employeeId); 

}