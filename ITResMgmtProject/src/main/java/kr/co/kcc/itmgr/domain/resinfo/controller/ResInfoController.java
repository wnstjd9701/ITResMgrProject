package kr.co.kcc.itmgr.domain.resinfo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.resclass.controller.ResClassController;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
import kr.co.kcc.itmgr.domain.resinfo.service.IResInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResInfoController {
	
	private final IResInfoService resInfoService;
	static final Logger logger = LoggerFactory.getLogger(ResClassController.class);

	@RequestMapping(value="/resinfo")
	public String selectAllResInfo(Model model) {
		List<ResInfo> selectAllResInfo = resInfoService.selectAllResInfo();
		model.addAttribute("selectAllResInfo", selectAllResInfo);
		
		List<ResInfo> searchResInfoByResClass = resInfoService.searchResInfoByResClass();
		model.addAttribute("search", searchResInfoByResClass);
		List<CommonCodeDetail> selectResStatusCode = resInfoService.selectResStatusCode("RES000");
		model.addAttribute("selectResStatusCode", selectResStatusCode);
		
		List<InstallPlace> selectResInstallPlace = resInfoService.selectResInstallPlace();
		model.addAttribute("selectResInstallPlace", selectResInstallPlace);
		List<ResClass> resClassList = resInfoService.selectAllResClass();
		Map<String, List<Map<String, List<Map<String, String>>>>> resClassMap = new LinkedHashMap<>();
		// 데이터를 반복하면서 맵에 추가
        for (ResClass r : resClassList) {
            if (r.getUpperResClassId() == null) {
                // 상위 클래스 ID가 null이면 하드웨어 또는 보안 항목
                String value = r.getResClassName2(); // 하드웨어 또는 보안 항목 이름
                // Create a new List<Map<String, List<Map<String, String>>>> and populate it
                List<Map<String, List<Map<String, String>>>> tempMapList = new ArrayList<>();
                Map<String, List<Map<String, String>>> innerMap = new LinkedHashMap<>();
                List<Map<String, String>> innerList = new ArrayList<>();
                Map<String, String> innerMapEntry = new HashMap<>();
                innerMapEntry.put("key", "key"); // Modify with appropriate key name
                innerList.add(innerMapEntry);
                innerMap.put(value, innerList); // Modify with appropriate inner key name
                tempMapList.add(innerMap);

                System.out.println(tempMapList);
                resClassMap.put(r.getResClassName(), tempMapList);
            }
        }
        System.out.println(resClassMap);

        // 결과 출력
        return "resinfo/resinfo";
    }

	
	@GetMapping("/resinfo/additem")
	@ResponseBody
	public List<ResInfo> selectMappingAddItem(@RequestParam("resSerialId") String resSerialId){
		System.out.println("asdasdasdasdasdasdasdas"+resSerialId);
		List<ResInfo> selectMappingAddItem = resInfoService.selectMappingAddItem(resSerialId);
		return selectMappingAddItem;
	}
	
	@GetMapping("/resinfo/search")
	@ResponseBody
	public List<ResInfo> searchResInfo(ResInfo resInfo) {
		List<ResInfo> searchResInfo = resInfoService.searchResInfo(resInfo);
		return searchResInfo;
	}
	
	@PostMapping("/resinfo/insert")
	@ResponseBody
	public ResInfo insertResInfo(@RequestBody ResInfo resInfo) {
		ResInfo insertResInfo = resInfoService.insertResInfo(resInfo);
		return insertResInfo;
	}
	
	@GetMapping("/resinfo/detail")
	@ResponseBody
	public ResInfo selectResInfoDetail(@RequestParam("resName")String resName) {
		ResInfo selectResInfoDetail  = resInfoService.selectResInfoDetail(resName);
		return selectResInfoDetail;
	}
}
