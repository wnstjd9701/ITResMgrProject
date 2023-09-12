package kr.co.kcc.itmgr.domain.employee.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


	//행추가저장(완료)
//	@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
//	@ResponseBody
//	public List<Employee> saveAll(@RequestBody List<Employee> employee) {
//		List<Employee> employeeList = new ArrayList<>();
//
//		try {
//			logger.info("size: " + employee.size());
//
//			if (employee != null && !employee.isEmpty()) {
//				logger.info("Employee List: " + employee);
//				employeeService.insertEmployee(employee);
//			}
//			employeeList = employeeService.selectAllEmployee();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return employeeList;
//	}
	
		//행바로삭제(완료)
//		@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
//		@ResponseBody
//		public List<Employee> deleteEmployee(@RequestBody List<String> employeeIdList) {
//			System.out.println("employeeIdList1" + employeeIdList);
//			List<Employee> employeeList = new ArrayList<>();
//			
//			try {
//			if(employeeIdList != null) {
//				System.out.println("employeeIdList" + employeeIdList);
//				for (String employeeId : employeeIdList) {
//	                employeeService.deleteEmployeeByUseYN(employeeId);
//	            }
//			}
//			employeeList = employeeService.selectAllEmployee();
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		    return employeeList;
//		}
	
	
	
	
	



//--------------------------------------------------------------------------------------------------------------------------------------------------------
	//저장버튼 눌렀을 때
	//	@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
	//	@ResponseBody
	//	public List<Employee> saveAll(@RequestBody List<Employee> employee) {
	//
	//		List<Employee> employeeList = new ArrayList<>();
	//
	//		try {
	//			logger.info("employee: " + employee);
	//			logger.info("size: " + employee.size());
	//			if(employee.size() > 1) {
	//					employeeService.insertEmployee(employee);
	//			}
	//			
	//			String empName = employee.get(0).getEmployeeName();
	//			logger.info("empName: " + empName);
	//			
	//
	//			String[] empId = employee.get(0).getEmployeeIdArray();
	//			logger.info("empId: " + Arrays.toString(empId));
	//			if(empId != null) {
	//				for (String employeeId : employee.get(0).getEmployeeIdArray()) {
	//					logger.info("employeeIdArray: " + employeeId);
	//					logger.info("employee: " + employee);
	//					employeeService.deleteEmployeeByUseYN(employeeId);
	//				}
	//			}
	//			employeeList = employeeService.selectAllEmployee();
	//		} catch (Exception e) {
	//			e.printStackTrace(); 
	//		}
	//		return employeeList;
	//	}






	//		@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
	//		@ResponseBody
	//		public List<Employee> saveAll(@RequestBody List<Employee> employee, 
	//				@RequestParam(value = "employeeIdArray[]", required=false) List<String> employeeIdArray) {
	//	
	//			List<Employee> employeeList = new ArrayList<>();
	//	
	//			try {
	//				logger.info("employee: " + employee);
	//				logger.info("size: " + employee.size());
	//				if(employee.size() > 0 ) {
	//					employeeService.insertEmployee(employee);
	//				}
	//	
	//				if(employeeIdArray != null) {
	//					System.out.println("employeeIdArray : " + employeeIdArray) ;
	//					logger.info("employeeIdArray: " + employeeIdArray);
	//					for (String employeeId : employeeIdArray) {
	//						employeeService.deleteEmployeeByUseYN(employeeId);
	//					} 
	//				}
	//				
	//				
	//				employeeList = employeeService.selectAllEmployee();
	//			} catch (Exception e) {
	//				e.printStackTrace(); 
	//			}
	//			return employeeList;
	//		}


	//	//검색
	//	@RequestMapping(value = "/employeeview", method = RequestMethod.POST)
	//	public String selectSearchEmployee(@RequestParam("empType") String employeeTypeCode) {
	//		try {
	//			
	//			//사원상태 검색 test
	//			
	//			
	//			employeeService.selectSearchEmployee(employeeTypeCode);
	//			
	//		
	//
	//		} catch (Exception e) {
	//			e.printStackTrace(); 
	//		}
	//
	//		//수정
	//		return "employee/employeeview";
	//	}
}


