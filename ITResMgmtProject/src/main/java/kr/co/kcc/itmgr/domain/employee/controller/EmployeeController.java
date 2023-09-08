package kr.co.kcc.itmgr.domain.employee.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	@RequestMapping(value = "/employeeview")
	public String selectAllEmployee(Model model) {
		List<Employee> employeeList = employeeService.selectAllEmployee();
		model.addAttribute("employeeList", employeeList);
		return "employee/employeeview";
	}
	
//	//사원 검색
//	@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
//	@ResponseBody
//	public List<Employee> selectSearchEmployee(@RequestBody List<Employee> employee){
//
//		List<Employee> employeeList = new ArrayList<>();
//
//		try {
//			logger.info("employee: " + employee);
//			logger.info("size: " + employee.size());
//			if(employee.size() > 0 ) {
//				employeeService.insertEmployee(employee);
//			}
//
//			
//			employeeList = employeeService.selectSearchEmployee();
//		} catch (Exception e) {
//			e.printStackTrace(); 
//		}
//		return employeeList;
//	}
	

	//저장버튼 눌렀을 때
	@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
	@ResponseBody
	public List<Employee> saveAll(@RequestBody List<Employee> employee) {

		List<Employee> employeeList = new ArrayList<>();

		try {
			logger.info("employee: " + employee);
			logger.info("size: " + employee.size());
//			if(employee.size() > 0 ) {
//				employeeService.insertEmployee(employee);
//			}
			String[] empId = employee.get(0).getEmployeeIdArray();
			logger.info("empId: " + Arrays.toString(empId));
//			if(employeeIdArray != null) {
//				System.out.println("employeeIdArray : " + employeeIdArray) ;
//				logger.info("employeeIdArray: " + employeeIdArray);
//				for (String employeeId : employee.get(0).getEmployeeIdArray()) {
//					logger.info("employeeIdArray: " + employeeId);
//					employeeService.deleteEmployeeByUseYN(employeeId);
//				} 
//			}
			
			
			if(empId != null) {
				for (String employeeId : employee.get(0).getEmployeeIdArray()) {
					logger.info("employeeIdArray: " + employeeId);
					logger.info("employee: " + employee);
					employeeService.deleteEmployeeByUseYN(employeeId);
				} 
			}
			employeeList = employeeService.selectAllEmployee();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return employeeList;
	}
	
	
	
	
	
	
//	@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
//	@ResponseBody
//	public List<Employee> saveAll(@RequestBody List<Employee> employee, 
//			@RequestParam(value = "employeeIdArray[]", required=false) List<String> employeeIdArray) {
//
//		List<Employee> employeeList = new ArrayList<>();
//
//		try {
//			logger.info("employee: " + employee);
//			logger.info("size: " + employee.size());
//			if(employee.size() > 0 ) {
//				employeeService.insertEmployee(employee);
//			}
//
//			if(employeeIdArray != null) {
//				System.out.println("employeeIdArray : " + employeeIdArray) ;
//				logger.info("employeeIdArray: " + employeeIdArray);
//				for (String employeeId : employeeIdArray) {
//					employeeService.deleteEmployeeByUseYN(employeeId);
//				} 
//			}
//			
//			
//			employeeList = employeeService.selectAllEmployee();
//		} catch (Exception e) {
//			e.printStackTrace(); 
//		}
//		return employeeList;
//	}
	
	
}


