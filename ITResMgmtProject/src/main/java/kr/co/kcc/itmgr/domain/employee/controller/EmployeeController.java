package kr.co.kcc.itmgr.domain.employee.controller;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.employee.model.Employee;
import kr.co.kcc.itmgr.domain.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor

public class EmployeeController {

	static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	private final IEmployeeService employeeService;

	//사원 기본 페이지
	@RequestMapping(value = "/employeeview", method=RequestMethod.GET)
	public String selectAllEmployee(Model model) {
		List<Employee> employeeList = employeeService.selectAllEmployee();
		model.addAttribute("employeeList", employeeList);
		return "employee/employeeview";
	}


	//수정
//	@PostMapping("/update/employee")
//	@ResponseBody
//	public List<Employee> updateEmployee(@RequestBody(required = false) List<Employee> updateEmployeeList) {
//		List<Employee> employeeList = new ArrayList<>();
//
//
//		logger.info("updatedEmployeeInfo: " + updateEmployeeList);
//
//
//		for(Employee emp : updateEmployeeList) {
//			employeeService.updateEmployee(emp);
//		}
//
//		System.out.println("-----------------");
//		employeeList = employeeService.selectAllEmployee();
//
//		return employeeList;
//	}  
	
	
	@PostMapping("/update/employee")
	@ResponseBody
	public List<Employee> updateEmployee(@RequestBody(required = false) List<Employee> updatedEmployeeInfo) {
		List<Employee> employeeList = new ArrayList<>();


		logger.info("updatedEmployeeInfo: " + updatedEmployeeInfo);


	
			employeeService.updateEmployee(updatedEmployeeInfo);
	

		System.out.println("-----------------");
		employeeList = employeeService.selectAllEmployee();

		return employeeList;
	}  

	//검색(완료)
	@RequestMapping(value = "/search/employee", method = RequestMethod.POST)
	@ResponseBody
	public List<Employee> searchEmployees(@RequestBody Map<String, String> searchData) {
		List<Employee> employeeList = new ArrayList<>(); 

		try {
			String employeeTypeCode = searchData.get("employeeTypeCode");
			String employeeStatusCode = searchData.get("employeeStatusCode");
			String searchText = searchData.get("searchText");

			employeeList = employeeService.selectSearchEmployee(employeeTypeCode, employeeStatusCode, searchText);
		}catch(Exception e){
			e.printStackTrace();
		}
		return employeeList;
	}  



	@PostMapping("/save/employee")
	@ResponseBody
	public List<Employee> saveAll(@RequestBody(required = false) Employee requestData) {
		List<Employee> employeeList = new ArrayList<>();

		try {
			List<Employee> employee = requestData.getEmployee();
			List<String> deletedEmployeeIds = requestData.getDeletedEmployeeIds();

			//Insert
			if (employee != null && !employee.isEmpty()) {
				logger.info("Employee List: " + employee);
				employeeService.insertEmployee(employee);
			}

			//Delete
			if(deletedEmployeeIds != null) {
				System.out.println("deletedEmployeeIds" + deletedEmployeeIds);
				for (String employeeId : deletedEmployeeIds) {
					employeeService.deleteEmployeeByUseYN(employeeId);
				}
			}

			employeeList = employeeService.selectAllEmployee();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeList;
	}






}


