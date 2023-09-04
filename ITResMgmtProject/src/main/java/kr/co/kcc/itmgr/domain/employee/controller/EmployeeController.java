package kr.co.kcc.itmgr.domain.employee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.kcc.itmgr.domain.employee.model.Employee;
import kr.co.kcc.itmgr.domain.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

	static final Logger logger = LoggerFactory.getLogger(Employee.class);
	private final IEmployeeService employeeService;

	
	@RequestMapping(value ="/employeeview")
	public String selectEmployee(Model model) {
		List<Employee> employeeList = employeeService.selectAllEmployee();
		model.addAttribute("employeeList", employeeList);
		return "employee/employeeview";
	}
	
//	@RequestMapping(value = "/employeeview", method=RequestMethod.GET)
//	public String allSave(Model model) {
//		return "employee/employeeview";
//	}
	
	@RequestMapping(value = "/employeeview", method=RequestMethod.POST)
	public String allSave(Employee employee) {
		try {
			employee.setEmployeeId(employee.getEmployeeId());
			employee.setEmployeeName(employee.getEmployeeName());
			employee.setEmployeeStatusCode(employee.getEmployeeStatusCode());
			employee.setEmployeeTypeCode(employee.getEmployeeTypeCode());
			logger.info("Employee: " + employee);
			employeeService.insertEmployee(employee);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "employee/employeeview";
		
	}

}
