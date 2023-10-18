package kr.co.kcc.itmgr.domain.home.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import kr.co.kcc.itmgr.domain.home.model.SearchCondition;
import kr.co.kcc.itmgr.domain.home.model.ServerStatusResponse;
import kr.co.kcc.itmgr.domain.home.service.IMoniteringService;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
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
		List<Monitoring> resourceInfo = monitoringService.selectAllResourceInformation();
		List<ResClass> resClassLevel2 = monitoringService.selectResClassInformationByLevel(2);
		List<ResClass> resClassLevel3 = monitoringService.selectResClassInformationByLevel(3);
		for(Monitoring resInfo : resourceInfo) {
			String ip = resInfo.getIp();
			boolean isAlive = monitoringService.serverPingCheck(ip);
			resInfo.setIpStatus(isAlive);
		}
		model.addAttribute("resourceInfo", resourceInfo);

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
	public List<Monitoring> selectResourceInformationByCategory(Model model, SearchCondition searchCondition) {
		log.info("searchCondition: " + searchCondition);
		List<Monitoring> searchResult = monitoringService.selectResInformationBySearchCondition(searchCondition);
		
		for(Monitoring resInfo : searchResult) {
			String ip = resInfo.getIp();
			boolean isAlive = monitoringService.serverPingCheck(ip);
			resInfo.setIpStatus(isAlive);
		}
		log.info("SearchResult: " + searchResult);
		return searchResult;
	}

	/*
	 * @Author: [윤준성]
	 * @API No1-3: 서버 Ping 확인
	 * @Info: 해당 IP 상태 체크
	 */
	@GetMapping("/ip/check")
	public ResponseEntity<ServerStatusResponse> getServerStatus(@RequestParam("ip") String ip) throws IOException {
		ServerStatusResponse response = new ServerStatusResponse();

		boolean isAlive = monitoringService.serverPingCheck(ip);
		if (isAlive) {
			response.setCode(200);
			response.setStatus("성공");
			response.setMessage("서버 살아 있음");
			response.setServerStatus(isAlive);
		} else {
			response.setCode(400);
			response.setStatus("실패");
			response.setMessage("서버 죽음");
			response.setServerStatus(isAlive);
		}
		return ResponseEntity.ok().body(response);
	}

}
