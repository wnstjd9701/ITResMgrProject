package kr.co.kcc.itmgr.domain.placemap.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;
import kr.co.kcc.itmgr.domain.installplace.service.IInstallPlaceService;
import kr.co.kcc.itmgr.domain.placemap.service.IPlaceMapService;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
import kr.co.kcc.itmgr.global.common.DoName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PlaceMapController {
	
	private final IInstallPlaceService installPlaceService;
	private final IPlaceMapService placeMapService;
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-1. 지역별 설치 장소 현황
	 * @Info: 상세 주소 수정
	 */
	@GetMapping("/place/map")
    public ModelAndView selectPlaceMap() {
        ModelAndView modelAndView = new ModelAndView("place/placemap"); // 뷰 이름 설정
        List<InstallPlace> placeList = placeMapService.selectInstallPlaceList();
        
        List<InstallPlace> installPlace = installPlaceService.selectAllPlace(1);
        // stream을 사용하여 각 InstallPlace 객체에 getDoName 메서드 적용
        List<InstallPlace> place = placeList.stream()
                .map(placeMapService::getDoName) // 각 객체에 getDoName 메서드 적용
                .collect(Collectors.toList());
        
        Map<String, Integer> doNames = placeMapService.getDoCountValues();
        
        int totalCount = installPlaceService.selectInstallPlaceCount();
        Map<String, Object> paging = placeMapService.placeMapPaging(1, totalCount);
        int totalResCount = installPlaceService.selectResCount();
        
        log.info("place" + place);
        log.info("DoName: " + doNames);
        
        modelAndView.addObject("placeList", placeList);
        modelAndView.addObject("placePaging", installPlace);
        modelAndView.addObject("paging", paging);
        modelAndView.addObject("doNames", doNames);
        modelAndView.addObject("totalResCount", totalResCount);
        return modelAndView;
    }
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-7. 페이징 처리
	 * @Info: 설치 장소 페이징 처리
	 */
	@GetMapping("/place/map/paging/{page}")
	public ResponseEntity<Map<String,Object>> selectPlaceByPage(@PathVariable("page") int page, @RequestParam("searchType") String searchType){
		int startNumber = (page - 1) * 5 + 1;
		int totalCount = 0;
		log.info("searchType: " + searchType);
		
		if(searchType.equals("ALL")) {
			totalCount = installPlaceService.selectInstallPlaceCount();
			List<InstallPlace> place = installPlaceService.selectAllPlace(startNumber);
			
			log.info("result: " + place);
			
			int count = 0;
			for(InstallPlace p : place) {
				p.setRn(startNumber + count);
				count++;
			}
			
			Map<String, Object> paging = placeMapService.placeMapPaging(page, totalCount);

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("place", place);
			result.put("paging", paging);
			
			return ResponseEntity.ok().body(result);
		}else {
			DoName selectedDoName = placeMapService.getDoValuesByDoName(searchType);
			log.info("doName: " + selectedDoName);
	        if (selectedDoName != null) {
	        	String firstDoName = selectedDoName.getDoName().split(",")[0];
	        	String secondDoName = selectedDoName.getDoName().split(",")[1];
	        	
	        	totalCount = installPlaceService.selectPlaceCountByCity(firstDoName, secondDoName);
	        	List<InstallPlace> place = installPlaceService.selectPlaceByCity(firstDoName, secondDoName, startNumber, startNumber + 4);
	        	
	        	Map<String, Object> paging = placeMapService.placeMapPaging(page, totalCount);

	        	int count = 0;
				for(InstallPlace p : place) {
					p.setRn(startNumber + count);
					count++;
				}
				
	        	Map<String, Object> result = new HashMap<String, Object>();
	        	result.put("place", place);
	        	result.put("paging", paging);
	        	
	        	return ResponseEntity.ok().body(result);
	        }
			return ResponseEntity.ok().body(null);
		}
		
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-8. 자원 페이지 클릭
	 * @Info: 자원 페이징 처리
	 */
	@GetMapping("/place/map/resinfo/paging/{page}")
	public ResponseEntity<Map<String,Object>> selectResInfoByPage(@PathVariable("page") int page, 
			@RequestParam("resSearchType") String resSearchType){
		int startNumber = (page - 1) * 5 + 1;
		int totalCount = 0;
		List<InstallRes> resInfo = null;
		Map<String,Object> resInfoPaging = null;
		// 전체일 경우
		if(resSearchType.equals("ALL")) {
			totalCount = installPlaceService.selectResCount();
			resInfo = installPlaceService.selectAllResInfo(startNumber, startNumber + 4);
			resInfoPaging = placeMapService.placeMapPaging(page, totalCount);
		}else {
			DoName doName = placeMapService.getDoValuesByDoName(resSearchType);
			log.info("doName: " + doName);
			
			if (doName != null) { // 도시 별 페이징
				log.info("도시 이름: " + doName.getDoName());
				String firstDoName = doName.getDoName().split(",")[0];
	        	String secondDoName = doName.getDoName().split(",")[1];
	        	
	        	resInfo = installPlaceService.selectResInfoByCity(firstDoName, secondDoName, startNumber, startNumber + 4);
	            totalCount = installPlaceService.selectResInfoCountByCity(firstDoName, secondDoName);
				resInfoPaging = placeMapService.placeMapPaging(page, totalCount);
			} else { // 특정 장소에 해당하는 자원 페이징 
				String placeName = resSearchType;
				totalCount = installPlaceService.selectResInfoCountByPlaceName(resSearchType);
				log.info("totalCount: " + resSearchType);
				resInfo = installPlaceService.selectResInformationByInstallPlaceName(placeName, startNumber, startNumber + 4);
				resInfoPaging = placeMapService.placeMapPaging(page, totalCount);
				log.info("resInfoPaging: "+resInfoPaging);
			}
		}
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("resInfo", resInfo);
		result.put("resInfoPaging", resInfoPaging);
		return ResponseEntity.ok().body(result);
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-2. 특정 설치 장소 [비동기]
	 * @Info: 특정 설치 장소
	 */
	@PostMapping("/place/map/detail")
	public ResponseEntity<InstallPlace> selectInstallPlaceByName(@RequestBody Map<String, String> requestBody) {
	    String placeName = requestBody.get("placeName");
	    List<InstallPlace> places = installPlaceService.searchInstallPlaceByName(placeName, 1, 2);

	    if (places != null && !places.isEmpty()) {
	        InstallPlace place = places.get(0);
	        log.info("body: " + placeName);
	        log.info("place: " + place);
	        return ResponseEntity.ok().body(place);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-4. 지역별 자원 조회 
	 * @Info: 지역에 따른 설치 장소에 있는 자원 조회 / 클러스터 클릭
	 */
	/*
	 * @PostMapping("/place/city/resinfo") public ResponseEntity<Map<String,
	 * Object>> selectResInfoByCity(@RequestBody String doName){ int page = 1; int
	 * start = 1; int end = start + 4;
	 * 
	 * int totalCount =
	 * installPlaceService.selectPlaceCountByPlaceName(placeNameList);
	 * List<InstallPlace> place =
	 * installPlaceService.selectPlaceListByPlaceName(placeNameList, start, end);
	 * Map<String, Object> paging = placeMapService.placeMapPaging(page,
	 * totalCount);
	 * 
	 * int resInfoCount = installPlaceService.selectResCountByCity(placeNameList);
	 * List<InstallRes> resInfo =
	 * installPlaceService.selectResInformationByCity(placeNameList, start, end);
	 * Map<String, Object> resInfoPaging = placeMapService.placeMapPaging(page,
	 * resInfoCount); log.info("resPaging: " + resInfoPaging);
	 * 
	 * int count = 0; for(InstallPlace p : place) { p.setRn(start + count); count++;
	 * } Map<String, Object> placeMap = new HashMap<String, Object>();
	 * 
	 * log.info("place: " + place); log.info("resinfo: " + resInfo);
	 * 
	 * placeMap.put("resInfo", resInfo); placeMap.put("place", place);
	 * placeMap.put("paging", paging); placeMap.put("resInfoPaging", resInfoPaging);
	 * 
	 * return ResponseEntity.ok().body(placeMap); }
	 */
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-5. 특정 장소 조회
	 * @Info: 특정 장소에 대한 자원 정보 조회
	 */
	@PostMapping("/place/map/resinfo")
	public ResponseEntity<Map<String, Object>> selectResInfoAndPlace(@RequestBody String placeName){
		log.info("placeName: " + placeName);
		placeName = placeName.replace("\"", "");
		
		Map<String, Object> placeMap = new HashMap<String, Object>();
		
		int start = 1;
		int end = 5;
		InstallPlace place = installPlaceService.selectInstallPlaceDetail(placeName);
		Map<String,Object> paging = placeMapService.placeMapPaging(start, start);
		int resInfoCount = place.getResCount();
		
		log.info("resCount; " + resInfoCount);
		List<InstallRes> resInfo = installPlaceService.selectResInformationByInstallPlaceName(placeName, start, end);
		Map<String,Object> resInfoPaging = placeMapService.placeMapPaging(start, resInfoCount);
		
		
		log.info("resInfo: " + resInfo);
		placeMap.put("place", place);
		placeMap.put("paging", paging);
		placeMap.put("resInfo", resInfo);
		placeMap.put("resInfoPaging", resInfoPaging);
		
		return ResponseEntity.ok().body(placeMap);
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-6. 도시 별 설치 장소 조회
	 * @Info: 도시 별 설치 장소 조회 / Selectbox 클릭 / 클러스터 클릭
	 */
	@GetMapping("/place/map/city")
	public ResponseEntity<Map<String, Object>> selectPlaceByCity(@RequestParam("doName") String doName){
		int totalCount = 0;
		int start = 1;
		int end = start + 4;
		if(doName.equals("ALL")) {
			
			totalCount = installPlaceService.selectInstallPlaceCount();
			Map<String, Object> paging = placeMapService.placeMapPaging(start, totalCount);
			List<InstallPlace> place = installPlaceService.selectAllPlace(start);
			
			List<InstallRes> resInfo = installPlaceService.selectAllResInfo(start, end);
			int resInfoCount = installPlaceService.selectResCount();
			Map<String, Object> resInfoPaging = placeMapService.placeMapPaging(start, resInfoCount);
			
			int count = 0;
			for(InstallPlace p : place) {
				p.setRn(1 + count);
				count++;
			}
			
			Map<String, Object> placeMap = new HashMap<String, Object>();
			placeMap.put("resInfo", resInfo);
			placeMap.put("place", place);
			placeMap.put("paging", paging);
			placeMap.put("resInfoPaging", resInfoPaging);
			
			return ResponseEntity.ok().body(placeMap);
		}
		
		DoName selectedDoName = placeMapService.getDoValuesByDoName(doName);
        if (selectedDoName != null) {
        	log.info("제주도?: " + selectedDoName);
        	String firstDoName = selectedDoName.getDoName().split(",")[0];
        	String secondDoName = selectedDoName.getDoName().split(",")[1];
        	totalCount = installPlaceService.selectPlaceCountByCity(firstDoName, secondDoName);
        	Map<String, Object> paging = placeMapService.placeMapPaging(1, totalCount);
        	
        	List<InstallPlace> place = installPlaceService.selectPlaceByCity(firstDoName, secondDoName, start, end);
        	
            List<InstallRes> resInfo = installPlaceService.selectResInfoByCity(firstDoName, secondDoName, start, end);
            int resInfoCount = installPlaceService.selectResInfoCountByCity(firstDoName, secondDoName);
            
			Map<String, Object> resInfoPaging = placeMapService.placeMapPaging(1, resInfoCount);
            
            int count = 0;
			for(InstallPlace p : place) {
				p.setRn(1 + count);
				count++;
			}
			
            Map<String, Object> placeMap = new HashMap<String, Object>();
            placeMap.put("resInfo", resInfo);
            placeMap.put("paging", paging);
    		placeMap.put("place", place);
    		placeMap.put("resInfoPaging", resInfoPaging);
    		
            return ResponseEntity.ok().body(placeMap);
        } else {
            return ResponseEntity.badRequest().build();
        }
	}
}
