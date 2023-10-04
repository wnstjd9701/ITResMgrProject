package kr.co.kcc.itmgr.domain.employee.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/employee", method=RequestMethod.GET)
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
	@RequestMapping(value = "/search/employee", method=RequestMethod.GET)
	@ResponseBody
	public List<Employee> searchEmployees(@RequestParam Map<String, String> searchData) {
		List<Employee> employeeList = new ArrayList<>(); 

		try {
			String employeeTypeCode = searchData.get("employeeTypeCode");
			String employeeStatusCode = searchData.get("employeeStatusCode");
			String searchText = searchData.get("searchText");
			
			System.out.println("employeeTypeCode" + employeeTypeCode);

			employeeList = employeeService.selectSearchEmployee(employeeTypeCode, employeeStatusCode, searchText);

		}catch(Exception e){
			e.printStackTrace();
		}
		return employeeList;
	}
	
	//기존의 사원ID값 가져오기
//	@RequestMapping(value = "/select/employeeId{employeeIds}", method=RequestMethod.GET)
//	@ResponseBody
//	public Map<String, String> DuplicateCheck(@RequestParam Map<String, String> employeeIds) {
//		Map<String, String> duplicateCheck = new HashMap<>(); 
//
//		try {
//
//
//			duplicateCheck = employeeService.selectEmployeeId();
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return duplicateCheck;
//	}
	
//	@RequestMapping(value = "/select/employeeId", method=RequestMethod.GET)
//    @ResponseBody
//    public List<String> getEmployeeIds() {
//        List<String> employeeIds = employeeService.selectEmployeeId();
//        
//        
//        return employeeIds;
//    }

	@RequestMapping(value = "/select/employeeId", method=RequestMethod.GET)
	public List<String> selectEmployeeId() {
		List<String> employeeIds = new ArrayList<>(); 
		employeeIds = employeeService.selectEmployeeId();

		System.out.println("-----------employeeIds-----------" + employeeIds);

		return employeeIds;
	}
	
	@PostMapping("/checkDuplicateEmployeeIds")
    public List<String> checkDuplicateEmployeeIds(@RequestBody List<String> empIds) {
        List<String> duplicateIds = new ArrayList<>();

        // 기존 DB에 저장된 사원 ID들을 가져옴
        List<String> existingEmployeeIds = new ArrayList<>();

        List<Employee> employees = employeeService.selectAllEmployee();
        for (Employee employee : employees) {
            existingEmployeeIds.add(employee.getEmployeeId());
        }

        System.out.println("기존id값" + existingEmployeeIds);

        // 새로운 사원 ID들과 기존 DB에 저장된 ID들을 비교하여 중복된 ID를 찾음
        for (String empId : empIds) {
            if (existingEmployeeIds.contains(empId)) {
                duplicateIds.add(empId);
            }
        }
        
        System.out.println("반환값"+duplicateIds);

        return duplicateIds;
    }

    // 다른 컨트롤러 메서드와 더 이어질 수 있음
}
