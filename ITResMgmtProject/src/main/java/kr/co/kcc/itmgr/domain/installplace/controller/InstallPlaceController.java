package kr.co.kcc.itmgr.domain.installplace.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;
import kr.co.kcc.itmgr.domain.installplace.service.IInstallPlaceService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InstallPlaceController {
	
	static final Logger logger = LoggerFactory.getLogger(InstallPlaceController.class);
	
	private final IInstallPlaceService installPlaceService;
	
	/*
	 * Author: [윤준성]
	 * API No.3-1. 설치 장소 페이지
	 * Info: 설치 장소 모두 조회
	 */
	@GetMapping("/installplace")
	public String selectInstallPlace(Model model) {
		List<InstallPlace> installPlace = installPlaceService.selectAllAddress();
		logger.info("installPlace: " + installPlace);
		model.addAttribute("installPlace", installPlace);
		
		return "place/installplace";
	}
	
	/*
	 * Author: [윤준성]
	 * API No.3-2. 설치 장소 검색 [비동기]
	 * Info: 설치장소명으로 설치 장소 검색 
	 */
	@GetMapping("/installplace/search")
	@ResponseBody
	public List<InstallPlace> selectInstallPlaceByName(String placeName) {
		List<InstallPlace> installPlace = installPlaceService.selectInstallPlaceByName(placeName);
		logger.info("InstallPlaceList: " + installPlace);
		return installPlace;
	}
	
	/*
	 * Author: [윤준성]
	 * API No.3-3. 자원 정보 및 상세 주소 조회 [비동기]
	 * Info: 설치 장소 이름으로 자원 정보 조회
	 */
	@GetMapping("/installplace/resinfo")
	@ResponseBody
	public Map<String, Object> selectResInformationByInstallPlaceName(String placeName){
		List<InstallRes> resInfo = installPlaceService.selectResInformationByInstallPlaceName(placeName);
		InstallPlace installPlace = installPlaceService.selectInstallPlaceDetail(placeName);
		
		Map<String, Object> placeMap = new HashMap<String, Object>();
		placeMap.put("resInfo", resInfo);
		placeMap.put("installPlace", installPlace);
		return placeMap;
	}
}
