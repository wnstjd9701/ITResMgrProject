package kr.co.kcc.itmgr.domain.employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.employee.model.Employee;
import kr.co.kcc.itmgr.domain.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

	static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	private final IEmployeeService employeeService;

	//사원 기본 페이지
	@GetMapping("/employee")
	public String selectAllEmployee(Model model) {
		List<Employee> employeeList = employeeService.selectAllEmployee();
		model.addAttribute("employeeList", employeeList);

		//검색창 사원유형
		Map<String, String> commonCodeTypeList = new HashMap<>();
		List<CommonCodeDetail> commonCodeType = employeeService.commonCodeEmpType();
		for (CommonCodeDetail commonCode : commonCodeType) {
			commonCodeTypeList.put(commonCode.getDetailCode(), commonCode.getDetailCodeName());
		}
		model.addAttribute("commonCodeTypeList", commonCodeTypeList);

		//검색창 사원상태
		Map<String, String> commonCodeStatusList = new HashMap<>();
		List<CommonCodeDetail> commonCodeStatus = employeeService.commonCodeEmpStatus();
		for (CommonCodeDetail commonCode2 : commonCodeStatus) {
			commonCodeStatusList.put(commonCode2.getDetailCode(), commonCode2.getDetailCodeName());
		}
		model.addAttribute("commonCodeStatusList", commonCodeStatusList);

		return "employee/employee";
	}


	//Insert, Update, Delete 저장
	@PostMapping("/save/employee")
	@ResponseBody
	public List<Employee> saveAll(@RequestBody(required = false) Employee requestData) {
		List<Employee> employeeList = new ArrayList<>();

		try {
			List<Employee> employee = requestData.getEmployee();
			List<String> deletedEmployeeIds = requestData.getDeletedEmployeeIds();
			List<Employee> updatedEmployeeInfo = requestData.getUpdatedEmployeeInfo();

			//Insert
			if (employee != null && !employee.isEmpty()) {
				employeeService.insertEmployee(employee);
			}

			//Delete
			if(deletedEmployeeIds != null) {
				for (String employeeId : deletedEmployeeIds) {
					employeeService.deleteEmployeeByUseYN(employeeId);
				}
			}

			//Update
			if(updatedEmployeeInfo != null && !updatedEmployeeInfo.isEmpty()) {
				employeeService.updateEmployee(updatedEmployeeInfo);
			}

			employeeList = employeeService.selectAllEmployee();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return employeeList;
	}


	//검색
	@GetMapping("/search/employee")
	@ResponseBody
	public List<Employee> searchEmployees(@RequestParam Map<String, String> searchData) {
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


	//사원ID 중복체크
	@PostMapping("/checkDuplicateEmployeeIds")
	@ResponseBody
	public List<String> checkDuplicateEmployeeIds(@RequestBody List<String> empIds) {

		List<String> duplicateIds = new ArrayList<>();

		try {
			// 기존 DB에 저장된 사원 ID들을 가져옴
			List<String> existingEmployeeIds = new ArrayList<>();

			List<Employee> employees = employeeService.selectAllEmployee();
			for (Employee employee : employees) {
				existingEmployeeIds.add(employee.getEmployeeId());
			}

			// 중복된 ID 찾기
			Set<String> duplicateIdSet = new HashSet<>();
			for (String empId : empIds) {
			    // 중복된 ID가 아니면서, 이미 검색된 중복 ID 목록에 포함되지 않은 경우에만 추가
				if ((existingEmployeeIds.contains(empId) || duplicateIdSet.contains(empId)) && !duplicateIds.contains(empId)) {
					duplicateIds.add(empId); // 중복된 ID로 판별되면 목록에 추가
				}
				duplicateIdSet.add(empId); // 중복을 방지하기 위해 추가한 중복된 ID를 저장
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return duplicateIds;
	}
	
	//사원비밀번호 가져오기
	@PostMapping("/findEmployeePwd")
	@ResponseBody
	public Employee findEmployeePwd(@RequestParam String employeeId) {
		 Employee employeePwd = new Employee();

		try {
			employeePwd = employeeService.selectEmployeePwd(employeeId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return employeePwd;
	}
}
