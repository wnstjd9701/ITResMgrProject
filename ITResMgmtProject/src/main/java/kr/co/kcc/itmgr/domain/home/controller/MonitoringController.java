package kr.co.kcc.itmgr.domain.home.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import kr.co.kcc.itmgr.domain.home.service.IMoniteringService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MonitoringController {

	static final Logger logger = LoggerFactory.getLogger(MonitoringController.class);
	
	private final IMoniteringService monitoringService;
	
	/*
	 * API No1. 모니터링 화면
	 * Info : 모든 모니터링 자원 정보 보여주는 API (N인 경우도 보여줌)
	 */
	@GetMapping("/")
	public String getAllResourceInformation(Model model) {
		String resClass = "HW";
		List<Monitoring> resourceInfo = monitoringService.getAllResourceInformation();
		
		logger.info("자원정보: " + resourceInfo);
		model.addAttribute("resourceInfo", resourceInfo);
		//
		return "index";
	}
	
	/* 
	 * API No2. 모니터링 화면 검색
	 * Info : 자원 분류를 선택하고 검색 버튼을 누를 때 API 
	 */
	@GetMapping("/monitor/category")
	public String getResourceInformationByCategory(@RequestParam("resUpperClassName") String resUpperClass, 
			@RequestParam("resClassName") String resClassName, @RequestParam("resUseYn") String resUseYn, Model model) {
		return "/";
	}
}
