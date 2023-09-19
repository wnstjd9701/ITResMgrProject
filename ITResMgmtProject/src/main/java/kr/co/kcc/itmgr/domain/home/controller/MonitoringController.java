package kr.co.kcc.itmgr.domain.home.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import kr.co.kcc.itmgr.domain.home.model.SearchCondition;
import kr.co.kcc.itmgr.domain.home.service.IMoniteringService;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MonitoringController {

	static final Logger logger = LoggerFactory.getLogger(MonitoringController.class);
	
	private final IMoniteringService monitoringService;
	
	/*
	 * API No1-1. 모니터링 화면
	 * Info : 모든 모니터링 자원 정보 보여주는 API (N인 경우도 보여줌)
	 */
	@GetMapping("/")
	public String getAllResourceInformation(Model model) {
		List<Monitoring> resourceInfo = monitoringService.selectAllResourceInformation();
		List<ResClass> resClassLevel2 = monitoringService.selectResClassInformationByLevel(2);
		List<ResClass> resClassLevel3 = monitoringService.selectResClassInformationByLevel(3);
		
//		logger.info("Level2: " + resClassLevel2);
//		logger.info("Level3: " + resClassLevel3);
//		logger.info("자원정보: " + resourceInfo);
		model.addAttribute("resourceInfo", resourceInfo);

		model.addAttribute("resClassLevel2", resClassLevel2);
		model.addAttribute("resClassLevel3", resClassLevel3);
	
		return "index"; 
	}
	
	/* 
	 * API No1-2. 모니터링 화면 검색
	 * Info : 자원 분류를 선택하고 검색 버튼을 누를 때 API 
	 */
	@PostMapping("/search")
	@ResponseBody
	public List<Monitoring> getResourceInformationByCategory(Model model, SearchCondition searchCondition) {
		logger.info("searchCondition: " + searchCondition);
		List<Monitoring> searchResult = monitoringService.selectResInformationBySearchCondition(searchCondition);
		logger.info("SearchResult: " + searchResult);
		return searchResult;
	}
}
