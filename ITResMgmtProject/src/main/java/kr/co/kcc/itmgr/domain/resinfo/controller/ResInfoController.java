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
		
		Map<String, Map<String, List<String>>> resClassMap = new LinkedHashMap<>();
		Map<String, Map<String, List<String>>> resClassMap2 = new LinkedHashMap<>();

		
		return "resinfo/resinfo";
	}
	
	@GetMapping("/resinfo/resclass")
	@ResponseBody
	public String selectAllResClass(Model model, @RequestParam("resClassName")String resClassName) {
		List<ResClass> resClassList = resInfoService.selectAllResClass(resClassName);
		return "resinfo/resinfo";
	}
	
	@GetMapping("/resinfo/search")
	@ResponseBody
	public List<ResInfo> searchResInfo(ResInfo resInfo) {
		List<ResInfo> searchResInfo = resInfoService.searchResInfo(resInfo);
		return searchResInfo;
	}
	
	@PostMapping("/resinfo/insert")
	public String insertResInfo(ResInfo resInfo, Model model) {
		model.addAttribute("resInfo", resInfo);
		resInfoService.insertResInfo(resInfo);
		return "resinfo/resinfo";
	}
	
	@GetMapping("/resinfo/detail")
	@ResponseBody
	public ResInfo selectResInfoDetail(@RequestParam("resName")String resName) {
		ResInfo selectResInfoDetail  = resInfoService.selectResInfoDetail(resName);
		return selectResInfoDetail;
	}
}
