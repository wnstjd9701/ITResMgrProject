package kr.co.kcc.itmgr.domain.ipinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpApiResponse;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;
import kr.co.kcc.itmgr.domain.ipinfo.service.IIpInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class IpInfoController {

	private final IIpInfoService ipInfoService;
	
	/*
	 * @Author: [윤준성]
	 * @API No.5-1. IP 정보 페이지
	 * @Info: IP 정보 조회 
	 */
	@GetMapping("/ip")
	public String selectIpInfo(Model model, HttpSession session) {
		int start = 1;
		int end = start + 9;
		List<IpInfo> ipInfo = ipInfoService.selectIpInfoByPage(start, end);
		int totalCount = ipInfoService.selectIpCount();
		
		Map<String, Object> ipPaging = ipInfoService.ipInfoPaging(start, totalCount);
				
		log.info("IpInfo: " + ipInfo);
		model.addAttribute("ipInfo", ipInfo);
		model.addAttribute("paging",ipPaging);
		
		return "/ip/ipinfo";
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.5-2. IP 정보 페이지 클릭
	 * @Info: IP 정보 조회 페이징  
	 */
	@GetMapping("/ip/{page}")
	public ResponseEntity<IpApiResponse> selecIpInfoByPage(@PathVariable("page") int page){
		int start = ((page - 1) * 10 ) + 1;
		int end = start + 9;
		List<IpInfo> ipInfo = ipInfoService.selectIpInfoByPage(start, end);
		int totalCount = ipInfoService.selectIpCount();
		
		Map<String, Object> ipPaging = ipInfoService.ipInfoPaging(start, totalCount);
		
		IpApiResponse response = new IpApiResponse();
		if(ipInfo.size() < 1 || totalCount < 1) {
			response.setCode(400);
			response.setStatus("error");
			response.setMessage("페이징 오류");
			return ResponseEntity.ok().body(response);
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ipInfo", ipInfo);
		data.put("ipPaging", ipPaging);
		
		response.setCode(200);
		response.setStatus("success");
		response.setMessage("조회 성공");
		response.setData(data);
		
		return ResponseEntity.ok().body(response);
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.5-3. IP 상세 및 해당 자원 조회
	 * @Info: IP 상세 및 해당 자원 조회
	 */
	@GetMapping("/ip/detail/{ipsn}")
	public ResponseEntity<IpApiResponse> selectIpAndResInfo(@PathVariable("ipsn") int ipSn, 
			@RequestParam("searchType") String searchType){
		// 자원 조회 
		// 상세 Ip 정보 조회
		return null;
	}
	
}
