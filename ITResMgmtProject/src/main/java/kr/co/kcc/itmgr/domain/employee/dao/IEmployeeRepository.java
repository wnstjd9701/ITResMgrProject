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

	//사원 검색
	List<Employee> selectSearchEmployee(String employeeTypeCode, String employeeStatusCode, String searchText);

	//사원등록
	void insertEmployee(List<Employee> employee);

	//사원삭제(사용여부:N)
	void deleteEmployeeByUseYN(String employeeId); 
	
	//사원수정
//	List<Employee> updateEmployee(String employeeId);
	void updateEmployee(List<Employee> updatedEmployeeInfo);

}