package kr.co.kcc.itmgr.domain.placemap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        ModelAndView modelAndView = new ModelAndView("/place/placemap"); // 뷰 이름 설정
        List<InstallPlace> placeList = placeMapService.selectInstallPlaceList();
        
        List<InstallPlace> installPlace = installPlaceService.selectAllPlace(1);
        // stream을 사용하여 각 InstallPlace 객체에 getDoName 메서드 적용
        List<InstallPlace> place = placeList.stream()
                .map(placeMapService::getDoName) // 각 객체에 getDoName 메서드 적용
                .collect(Collectors.toList());
        
        List<String> doNames = placeMapService.getDoValues();
        
        log.info("place" + place);
        log.info("DoName: " + doNames);
        
        modelAndView.addObject("place", placeList);
        modelAndView.addObject("placePaging", installPlace);
        modelAndView.addObject("doNames", doNames);
        
        return modelAndView;
    }
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-2. 특정 설치 장소 [비동기]
	 * @Info: 상세 주소 수정
	 */
	@PostMapping("/place/map/detail")
	public ResponseEntity<InstallPlace> selectInstallPlaceByName(@RequestBody Map<String, String> requestBody) {
	    String placeName = requestBody.get("placeName");
	    List<InstallPlace> places = installPlaceService.searchInstallPlaceByName(placeName, 1, 5);

	    if (places != null && !places.isEmpty()) {
	        InstallPlace place = places.get(0);
	        log.info("body: " + placeName);
	        log.info("place: " + place);
	        return ResponseEntity.ok().body(place);
	    } else {
	        // 만약 검색 결과가 없을 경우에 대한 처리를 추가할 수 있습니다.
	        // 예를 들어, 404 Not Found 상태 코드를 반환하거나 다른 적절한 응답을 보낼 수 있습니다.
	        return ResponseEntity.notFound().build();
	    }
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-4. 지역별 자원 조회 
	 * @Info: 지역에 따른 설치 장소에 있는 자원 조회
	 */
	@PostMapping("/place/city/resinfo")
	public ResponseEntity<Map<String, Object>> selectResInfoByCity(@RequestBody List<String> placeNameList){
		List<InstallPlace> place = installPlaceService.selectPlaceListByPlaceName(placeNameList);
		List<InstallRes> resInfo = installPlaceService.selectResInformationByCity(placeNameList);
		
		Map<String, Object> placeMap = new HashMap<String, Object>();
		
		log.info("place: " + place);
		log.info("resinfo: " + resInfo);
		
		placeMap.put("resInfo", resInfo);
		placeMap.put("place", place);
		
		return ResponseEntity.ok().body(placeMap);
	}
	
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
		
		List<InstallRes> resInfo = installPlaceService.selectResInformationByInstallPlaceName(placeName);
		InstallPlace place = installPlaceService.selectInstallPlaceDetail(placeName);
		log.info("resInfo: " + resInfo);
		placeMap.put("resInfo", resInfo);
		placeMap.put("place", place);
		
		return ResponseEntity.ok().body(placeMap);
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.4-6. 도시 별 설치 장소 조회
	 * @Info: 도시 별 설치 장소 조회
	 */
	@GetMapping("/place/map/city")
	public ResponseEntity<Map<String, Object>> selectPlaceByCity(@RequestParam("doName") String doName){
		if(doName.equals("ALL")) {
			List<InstallPlace> place = installPlaceService.selectAllPlace(1);
			List<InstallRes> resInfo = installPlaceService.selectAllResInfo();
			
			Map<String, Object> placeMap = new HashMap<String, Object>();
			placeMap.put("resInfo", resInfo);
			placeMap.put("place", place);
			
			return ResponseEntity.ok().body(placeMap);
		}
		
		DoName selectedDoName = placeMapService.getDoValuesByDoName(doName);
        if (selectedDoName != null) {
        	String firstDoName = selectedDoName.getDoName().split(",")[0];
        	String secondDoName = selectedDoName.getDoName().split(",")[1];
        	
        	List<InstallPlace> place = installPlaceService.selectPlaceByCity(firstDoName, secondDoName);
            List<InstallRes> resInfo = installPlaceService.selectResInfoByCity(firstDoName, secondDoName);

            Map<String, Object> placeMap = new HashMap<String, Object>();
            placeMap.put("resInfo", resInfo);
    		placeMap.put("place", place);
    		
            return ResponseEntity.ok().body(placeMap);
        } else {
            return ResponseEntity.badRequest().build();
        }
	}
}
