package kr.co.kcc.itmgr.domain.employee.controller;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import kr.co.kcc.itmgr.domain.employee.model.Employee;
import kr.co.kcc.itmgr.domain.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class EmployeeController {
	
	static final Logger logger = LoggerFactory.getLogger(Employee.class);
	
	private final IEmployeeService employeeService;
	
	@RequestMapping(value = "/employeeview")
	public String selectAllEmployee(Model model) {
		List<Employee> employeeList = employeeService.selectAllEmployee();
		model.addAttribute("employeeList", employeeList);
		return "employee/employeeview";
	}

	@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
	public String saveAll(@RequestBody List<Employee> employee) {
		try {
			logger.info("employee: " + employee);
			// int result = employeeService.insertEmployeeInformation(employee); -> Mapper에서 for문 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "employee/employeeview";
	}
}