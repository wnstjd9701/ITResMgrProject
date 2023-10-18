package kr.co.kcc.itmgr.domain.ipinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpApiResponse;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpCode;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpRes;
import kr.co.kcc.itmgr.domain.ipinfo.service.IIpInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class IpInfoController {

	private final IIpInfoService ipService;

	/*
	 * @Author: [윤준성]
	 * @API No.5-1. IP 정보 페이지
	 * @Info: IP 정보 조회 
	 */
	@GetMapping("/ip")
	public String selectIpInfo(Model model, HttpSession session) {
		int start = 1;
		int end = start + 9;
		List<IpInfo> ipInfo = ipService.selectIpInfoByPage(start, end);
		int totalCount = ipService.selectIpCount();
		Map<String, Object> ipPaging = ipService.ipInfoPaging(start, totalCount);

		List<IpCode> ipCode = ipService.selectIpCode();

		log.info("IpInfo: " + ipInfo);
		log.info("IPCODE: " + ipCode);
		model.addAttribute("ipInfo", ipInfo);
		model.addAttribute("paging",ipPaging);
		model.addAttribute("ipCode", ipCode);

		return "/ip/ipinfo";
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-2. IP 정보 페이지 클릭
	 * @Info: IP 정보 조회 페이징  
	 */
	@GetMapping("/ip/{page}")
	public ResponseEntity<IpApiResponse> selecIpInfoByPage(@PathVariable("page") int page,
			@RequestParam("searchType") String searchType){
		log.info("searchType: " + searchType);

		int start = ((page - 1) * 10 ) + 1;
		int end = start + 9;

		List<IpInfo> ipInfo;
		int totalCount;
		Map<String,Object> ipPaging;

		if(searchType.equals("ALL")) {
			ipInfo = ipService.selectIpInfoByPage(start, end);
			totalCount = ipService.selectIpCount();
			ipPaging = ipService.ipInfoPaging(start, totalCount);
		}else {
			ipInfo = ipService.searchIp(searchType, start, end);
			totalCount = ipService.selectIpCountByKeyword(searchType);
			ipPaging = ipService.ipInfoPaging(start, totalCount);
		}

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
		response.setMessage("IP 리스트 조회 성공");
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.5-7. 자원 정보 페이징
	 * @Info: 자원 정보 페이징
	 */
	@GetMapping("/ip/resinfo/{page}")
	public ResponseEntity<IpApiResponse> selectResInfoByPage(@PathVariable("page") int page,
			@RequestParam("ipSn") int ipSn){
		int start = (page - 1) * 5 + 1;
		int end = start + 4;
		
		List<IpRes> resInfo = ipService.selectResInfoByIpSn(start, end, ipSn);
		int totalCount = ipService.selectResInfoCountByIpSn(ipSn);
		Map<String,Object> resPaging = ipService.resInfoPaging(page, totalCount);
		
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("resInfo", resInfo);
		data.put("resPaging", resPaging);
		
		IpApiResponse response = new IpApiResponse();
		
		response.setCode(200);
		response.setStatus("success");
		response.setMessage("자원 조회 페이징 성공");
		response.setData(data);
		
		return ResponseEntity.ok().body(response);
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.5-3. IP 상세 및 해당 자원 조회
	 * @Info: IP 상세 및 해당 자원 조회
	 */
	@GetMapping("/ip/detail/{ipsn}")
	public ResponseEntity<IpApiResponse> selectIpAndResInfo(@PathVariable("ipsn") int ipSn){
		// 자원 조회 
		// 상세 Ip 정보 조회
		int start = 1;
		int end = start + 4;
		
		IpInfo ipInfo = ipService.selectIpDetail(ipSn);
		int totalCount = ipService.selectResInfoCountByIpSn(ipSn);
		List<IpRes> resInfo = ipService.selectResInfoByIpSn(start, end, ipSn);
		Map<String,Object> resPaging = ipService.ipInfoPaging(start, totalCount);
		
		IpApiResponse response = new IpApiResponse();

		log.info("ipInfo: " + ipInfo);
		log.info("resInfo: " + resInfo);

		if(ipInfo == null) {
			response.setCode(400);
			response.setStatus("error");
			response.setMessage("IP 상세 오류");
			return ResponseEntity.ok().body(response);
		}
		
		Map<String,Object> data = new HashMap<String, Object>();
		if(resInfo.size() == 0) {
			data.put("ipInfo", ipInfo);

			response.setCode(5001);
			response.setStatus("RES_INFO_NO_MAPPING");
			response.setMessage("매핑된 자원이 없습니다.");
			response.setData(data);
			return ResponseEntity.ok().body(response);
		}
		data.put("ipInfo", ipInfo);
		data.put("resInfo", resInfo);
		data.put("resPaging", resPaging);

		response.setCode(200);
		response.setStatus("success");
		response.setMessage("IP상세 조회 성공");
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-4. IP 검색
	 * @Info: IP 주소 검색
	 */
	@GetMapping("/search/ip")
	public ResponseEntity<IpApiResponse> searchIp(@RequestParam("keyword") String keyword){
		int start = 1;
		int end = start + 9;

		int totalCount = ipService.selectIpCountByKeyword(keyword);
		List<IpInfo> ipInfo = ipService.searchIp(keyword, start, end);
		Map<String,Object> ipPaging = ipService.ipInfoPaging(start, totalCount);

		IpApiResponse response = new IpApiResponse();
		Map<String,Object> data = new HashMap<String, Object>();

		if(ipInfo.size() < 1) {
			data.put("ipPaging", ipPaging);

			response.setCode(4001);
			response.setStatus("RESULT_NOT_FOUND");
			response.setMessage("검색 결과가 없습니다.");
			response.setData(data);

			return ResponseEntity.ok().body(response);
		}

		log.info("IpInfo: " + ipInfo);
		log.info("totalCount: " + totalCount);

		data.put("ipInfo", ipInfo);
		data.put("ipPaging", ipPaging);

		response.setCode(200);
		response.setStatus("success");
		response.setMessage("검색 성공");
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-5. IP 삭제
	 * @Info: IP 선택 삭제
	 */
	@PostMapping("/delete/ip")
	public ResponseEntity<IpApiResponse> deleteIp(@RequestBody int deleteIpSn){
		log.info("deleteIpSn: " + deleteIpSn);
		boolean result = ipService.deleteIp(deleteIpSn);
		
		IpApiResponse response = new IpApiResponse();
		Map<String,Object> data = new HashMap<String, Object>();

		int start = 1;
		int end = start + 9;

		int totalCount = ipService.selectIpCount();
		List<IpInfo> ipInfo = ipService.selectIpInfoByPage(start, end);
		Map<String, Object> ipPaging = ipService.ipInfoPaging(start, totalCount);
		
		data.put("ipInfo", ipInfo);
		data.put("ipPaging", ipPaging);
		
		response.setCode(200);
		response.setStatus("success");
		response.setMessage("삭제 성공");
		response.setData(data);
		
		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-6. IP 저장
	 * @Info: IP 한 개 저장 
	 */
	@PostMapping("/save/ip")
	public ResponseEntity<IpApiResponse> saveIp(@Valid IpInfo ipInfo, BindingResult bindingResult){
		log.info("Ip:" + ipInfo);
		IpApiResponse response = new IpApiResponse();
		Map<String,Object> data = new HashMap<String, Object>();
		
		if(bindingResult.hasErrors() || bindingResult.getErrorCount() != 0) {
			List<FieldError> list = bindingResult.getFieldErrors();
			for(FieldError error : list) {
				log.info("ERROR:" +error.getDefaultMessage());
				response.setMessage(error.getDefaultMessage());
				break;
			}
			response.setCode(5006);
			response.setStatus("NOT_VALID_VALUE");
			return ResponseEntity.ok().body(response);
		}
		
		
		int count = 0;
		int ipCheck;
		// 신규 저장
		if(ipInfo.getIpSn() == 0) {
			ipCheck = ipService.ipIsExists(ipInfo.getIp());
			if(ipCheck > 0) {
				response.setCode(5007);
				response.setStatus("ALREADY_IP_EXISTS");
				response.setMessage("이미 존재하는 IP 입니다.");
				return ResponseEntity.ok().body(response);
			}
			log.info("New Ip: " + ipInfo);
			count = ipService.insertIp(ipInfo);
			int newIpSn = ipInfo.getIpSn();
			
			response.setCode(200);
			response.setStatus("success");
			response.setMessage("IP 저장 성공");
			data.put("ipSn", newIpSn);
		}else { // 수정
			int updateIpCheck = ipService.updateIpIsExists(ipInfo.getIpSn(), ipInfo.getIp());
			if(updateIpCheck > 0) {
				// IP가 기존의 값과 동일, 다른값만 변경이므로 Check할 필요 x
				ipService.updateIp(ipInfo);
				response.setCode(200);
				response.setStatus("success");
				response.setMessage("IP 수정 성공");
			}else { // 기존의 IP가 수정된 경우
				ipCheck = ipService.ipIsExists(ipInfo.getIp());
				if(ipCheck > 0) {
					// IP 중복
					response.setCode(5007);
					response.setStatus("ALREADY_IP_EXISTS");
					response.setMessage("이미 존재하는 IP 입니다.");
					return ResponseEntity.ok().body(response);
				}else {
					// UPDATE
					ipService.updateIp(ipInfo);
					response.setCode(200);
					response.setStatus("success");
					response.setMessage("IP 수정 성공");
				}
			}
		}
		
		int start = 1;
		int end = start + 9;
		List<IpInfo> ipInfoResult = ipService.selectIpInfoByPage(start, end);
		int totalCount = ipService.selectIpCount();
		Map<String, Object> ipPaging = ipService.ipInfoPaging(start, totalCount);
		
		data.put("ipInfo", ipInfoResult);
		data.put("ipPaging", ipPaging);
		response.setData(data);
		
		return ResponseEntity.ok().body(response);
	}










}
