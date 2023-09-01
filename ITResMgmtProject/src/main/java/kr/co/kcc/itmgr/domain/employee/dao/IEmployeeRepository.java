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
	

}
