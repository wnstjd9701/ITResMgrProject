package kr.co.kcc.itmgr.domain.home.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import kr.co.kcc.itmgr.domain.home.model.SearchCondition;
import kr.co.kcc.itmgr.domain.home.model.ServerStatusResponse;
import kr.co.kcc.itmgr.domain.home.service.IMoniteringService;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.global.common.ApiResponse;
import kr.co.kcc.itmgr.global.common.ApiResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MonitoringController {
	private final IMoniteringService monitoringService;

	/*
	 * Author: [윤준성]
	 * API No1-1. 모니터링 페이지
	 * Info : 모든 모니터링 자원 정보 보여주는 API (N인 경우도 보여줌)
	 */
	@GetMapping("/")
	public String selectAllResourceInformation(Model model) throws IOException {
		int page = 1;
		int start = 1;
		int end = start + 9;
		List<Monitoring> resourceInfo = monitoringService.selectAllResourceInformation(start, end);
		
		int totalCount = monitoringService.selectResCount();
		Map<String,Object> monitoringPaging = monitoringService.monitoringPaging(page, totalCount);
		
		List<ResClass> resClassLevel2 = monitoringService.selectResClassInformationByLevel(2);
		List<ResClass> resClassLevel3 = monitoringService.selectResClassInformationByLevel(3);
		for(Monitoring resInfo : resourceInfo) {
			String ip = resInfo.getIp();
			boolean isAlive = monitoringService.serverPingCheck(ip);
			resInfo.setIpStatus(isAlive);
		}
		model.addAttribute("resourceInfo", resourceInfo);
		model.addAttribute("paging", monitoringPaging);
		
		model.addAttribute("resClassLevel2", resClassLevel2);
		model.addAttribute("resClassLevel3", resClassLevel3);

		return "index"; 
	}

	/* 
	 * Author: [윤준성]
	 * API No1-2. 모니터링 화면 검색 [비동기]
	 * Info : 자원 분류를 선택하고 검색 버튼 클릭 
	 */
	@PostMapping("/search")
	@ResponseBody
	public ResponseEntity<ApiResponse<?>> selectResourceInformationByCategory(Model model, SearchCondition searchCondition) {
		log.info("searchCondition: " + searchCondition);
		int page = 1;
		int start = 1;
		int end = start + 9;
		
		searchCondition.setStart(start);
		searchCondition.setEnd(end);
		
		List<Monitoring> searchResult = monitoringService.selectResInformationBySearchCondition(searchCondition);
		int totalCount = monitoringService.selectResCountBySearch(searchCondition);
		
		Map<String,Object> paging = monitoringService.monitoringPaging(page, totalCount);
		
		Map<String,Object> data = new HashedMap<String, Object>();
		data.put("resInfo", searchResult);
		data.put("paging", paging);
		
		for(Monitoring resInfo : searchResult) {
			String ip = resInfo.getIp();
			boolean isAlive = monitoringService.serverPingCheck(ip);
			resInfo.setIpStatus(isAlive);
		}
		
		log.info("SearchResult: " + searchResult);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
	}

	/*
	 * @Author: [윤준성]
	 * @API No1-3: 서버 Ping 확인
	 * @Info: 해당 IP 상태 체크
	 */
	@GetMapping("/ping/check")
	public ResponseEntity<ApiResponse<?>> getServerStatus(@RequestParam("ip") String ip) throws IOException {
		boolean isAlive = monitoringService.serverPingCheck(ip);
		if (isAlive) {
			return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successDetailMessage(ApiResponseStatus.SERVER_PING_ALIVE.getCode(), ApiResponseStatus.SERVER_PING_ALIVE.getMessage()));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successDetailMessage(ApiResponseStatus.SERVER_PING_DIE.getCode(), ApiResponseStatus.SERVER_PING_DIE.getMessage()));
		}
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No1-4: 자원 페이징
	 * @Info: 해당 페이징 처리 
	 */
	@GetMapping("/monitoring/{page}")
	public ResponseEntity<ApiResponse<?>> selectResInfoByPage(@PathVariable("page") int page, SearchCondition searchCondition){
		log.info("Page / searchCondition: " + searchCondition);
		
		searchCondition.setPage(page);
		int start = (page-1) * 10 + 1;
		int end = start + 9;
		searchCondition.setStart(start);
		searchCondition.setEnd(end);
		
		List<Monitoring> searchResult = monitoringService.selectResInformationBySearchCondition(searchCondition);
		int totalCount = monitoringService.selectResCountBySearch(searchCondition);
		
		Map<String,Object> paging = monitoringService.monitoringPaging(page, totalCount);
		
		Map<String,Object> data = new HashedMap<String, Object>();
		data.put("resInfo", searchResult);
		data.put("paging", paging);
		
		for(Monitoring resInfo : searchResult) {
			String ip = resInfo.getIp();
			boolean isAlive = monitoringService.serverPingCheck(ip);
			resInfo.setIpStatus(isAlive);
		}
		
		log.info("SearchResult: " + searchResult);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
	}

}
