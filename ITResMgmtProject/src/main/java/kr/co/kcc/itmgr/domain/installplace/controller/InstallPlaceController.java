package kr.co.kcc.itmgr.domain.installplace.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;
import kr.co.kcc.itmgr.domain.installplace.model.PlaceApiResponse;
import kr.co.kcc.itmgr.domain.installplace.service.IInstallPlaceService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InstallPlaceController {

	static final Logger logger = LoggerFactory.getLogger(InstallPlaceController.class);

	private final IInstallPlaceService installPlaceService;

	/*
	 * @Author: [윤준성]
	 * @API No.3-1. 설치 장소 페이지
	 * @Info: 설치 장소 전체 조회
	 */
	@GetMapping("/installplace")
	public String selectInstallPlace(Model model) {
		int installPlaceCount = installPlaceService.selectInstallPlaceCount();
		int page = 1;
		List<InstallPlace> installPlace = installPlaceService.selectAllPlace(page);
		Map<String,Object> paging = installPlaceService.placePaging(page, installPlaceCount);
		
		logger.info("paging: " + paging);
		logger.info("installPlace: " + installPlace);
		
		model.addAttribute("paging", paging);
		model.addAttribute("installPlace", installPlace);

		return "place/installplace";
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.3-7. 설치 장소 페이징 처리 [비동기]
	 * @Info: 설치 장소 특정 페이지 검색
	 */
	@GetMapping("/installPlace/{page}") 
	public ResponseEntity<List<InstallPlace>> installPlacePaging(@PathVariable("page") int page, @RequestParam("searchType") String searchType){
		logger.info("page: " + page + " searchType: " + searchType);
		
		int startPage = (page - 1) * 5 + 1;
		if(searchType.equals("ALL")) {
			List<InstallPlace> installPlace = installPlaceService.selectAllPlace(startPage);
			
			int count = 0;
			for(InstallPlace place : installPlace) {
				place.setRn(startPage + count);
				count++;
			}
			
			logger.info("installPlace: " + installPlace);
			return ResponseEntity.ok().body(installPlace);
		}else {
			List<InstallPlace> installPlace = installPlaceService.searchInstallPlaceByName(searchType, startPage, startPage + 4);
			
			int count = 0;
			for(InstallPlace place : installPlace) {
				place.setRn(startPage + count);
				count++;
			}
			
			logger.info("installPlace: " + installPlace);
			return ResponseEntity.ok().body(installPlace);
		}
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.3-8. 설치 장소 다음 페이징 처리 [비동기]
	 * @Info: 다음 5개 페이지 검색
	 */
	@GetMapping("/installplace/next/{nextStartPage}")
	public ResponseEntity<Map<String, Object>> installPlaceNextPaging(@PathVariable("nextStartPage") int nextStartPage,
	        @RequestParam("searchType") String searchType) {
	    logger.info("page: " + nextStartPage + " searchType: " + searchType);

	    List<InstallPlace> installPlace;
	    int totalCount = 0;
	    if (searchType.equals("ALL")) {
	    	totalCount = installPlaceService.selectInstallPlaceCount();
	    	installPlace = installPlaceService.selectAllPlace((nextStartPage-1)*5 + 1);
	    	int count = 1;
	    	for(InstallPlace place : installPlace) {
	    		place.setRn(((nextStartPage - 1) * 5) + count);
	    		count++;
	    	}
	    } else {
	    	totalCount = installPlaceService.selectInstallPlaceSearchCount(searchType);
	    	installPlace = installPlaceService.searchInstallPlaceByName(searchType, (nextStartPage-1)*5 + 1, (nextStartPage-1)*5 + 5);
	    }

	    Map<String, Object> paging = installPlaceService.placePaging(nextStartPage, totalCount);
	    logger.info("Paging: " + paging);

	    Map<String, Object> result = new HashMap<>();
	    result.put("installPlace", installPlace);
	    result.put("paging", paging);

	    return ResponseEntity.ok().body(result);
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.3-9. 설치 장소 이전 페이징 처리 [비동기]
	 * @Info: 이전 5개 페이지 검색
	 */
	@GetMapping("/installplace/prev/{prevEndPage}")
	public ResponseEntity<Map<String,Object>> installPlacePrevPaging(@PathVariable("prevEndPage") int prevEndPage,
			@RequestParam("searchType") String searchType){
		int pageSize = 5;
		int startPage = prevEndPage - 4;
		int pageStartNum = (startPage - 1) * pageSize + 1;
		
		List<InstallPlace> installPlace;
		int totalCount = 0;
		if(searchType.equals("ALL")) {
			totalCount = installPlaceService.selectInstallPlaceCount();
			installPlace = installPlaceService.selectAllPlace(pageStartNum);
		}else {
			totalCount = installPlaceService.selectInstallPlaceSearchCount(searchType);
			installPlace = installPlaceService.searchInstallPlaceByName(searchType, pageStartNum, pageStartNum + 4);
		}
		
		Map<String, Object> paging = installPlaceService.placePaging(startPage, totalCount);
	    logger.info("Paging: " + paging);
	    
	    Map<String, Object> result = new HashMap<>();
	    result.put("installPlace", installPlace);
	    result.put("paging", paging);

	    return ResponseEntity.ok().body(result);
	}
	
	/*
	 * @Author: [윤준성]
	 * @API No.3-9. 설치 장소 자원 페이징 처리
	 * @Info: 해당 설치 장소에 해당하는 자원 페이징 처리
	 */
	@GetMapping("/installplace/resinfo/{page}")
	public ResponseEntity<Map<String,Object>> resInfoPaging(@PathVariable("page") int page, 
			@RequestParam("resSearchType") String resSearchType){
		int startNum = (page - 1) * 5 + 1;
		int endNum = startNum + 4;
		
		List<InstallRes> resInfo = installPlaceService.selectResInformationByInstallPlaceName(resSearchType, startNum, endNum);
		
		int totalCount = resInfo.size();
		Map<String,Object> paging = installPlaceService.placePaging(page, totalCount);
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("resInfo", resInfo);
		result.put("paging", paging);
		
		return ResponseEntity.ok().body(result);
	}
	/*
	 * @Author: [윤준성]
	 * @API No.3-2. 설치 장소 검색 [비동기]
	 * @Info: 설치장소명으로 설치 장소 검색 
	 */
	@GetMapping("/installplace/search")
	public ResponseEntity<Map<String,Object>> searchInstallPlaceByName(String keyword) {
		logger.info("keyword: " + keyword);
		int searchCount = installPlaceService.selectInstallPlaceSearchCount(keyword);
		int page = 1;
		
		Map<String,Object> paging = installPlaceService.placePaging(page, searchCount);
		
		int start = 1;
		int end = start + 4;
		
		List<InstallPlace> installPlace = installPlaceService.searchInstallPlaceByName(keyword, start, end);
		logger.info("InstallPlaceList: " + installPlace);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("installPlace", installPlace);
		result.put("paging", paging);
		
		return ResponseEntity.ok().body(result);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.3-3. 자원 정보 및 상세 주소 조회 [비동기]
	 * @Info: 설치 장소 이름으로 자원 정보 조회
	 */
	@GetMapping("/installplace/resinfo")
	@ResponseBody
	public Map<String, Object> selectResInformationByInstallPlaceName(String placeName){
		int start = 1;
		int end = 5;
		List<InstallRes> resInfo = installPlaceService.selectResInformationByInstallPlaceName(placeName, start, end);
		InstallPlace installPlace = installPlaceService.selectInstallPlaceDetail(placeName);
		
		int totalResCount = installPlace.getResCount();
		Map<String,Object> paging = installPlaceService.placePaging(1, totalResCount);
		
		Map<String, Object> placeMap = new HashMap<String, Object>();
		placeMap.put("resInfo", resInfo);
		placeMap.put("installPlace", installPlace);
		placeMap.put("paging", paging);
		return placeMap;
	}

	/*
	 * @Author: [윤준성]
	 * @API No.3-4. 주소 등록 [비동기]
	 * @Info: 상세 주소 등록 
	 */
	@PostMapping("/detailaddress")
	public ResponseEntity<PlaceApiResponse> installPlaceSave(@RequestBody InstallPlace installPlace) {
		PlaceApiResponse response = new PlaceApiResponse();
		
		String placeName = installPlace.getInstallPlaceName();
		int nameCheck = installPlaceService.checkPlaceNameByName(placeName);
		if(nameCheck >= 1) {
			response.setStatus("error");
            response.setCode(400); 
            response.setMessage("이미 존재하는 장소 이름입니다.");
			return ResponseEntity.ok().body(response);
		}
		int res = installPlaceService.insertInstallPlace(installPlace);
		
		int page = 1;
		int installPlaceCount = installPlaceService.selectInstallPlaceCount();
		List<InstallPlace> newInstallPlace = installPlaceService.selectAllPlace(page);
		Map<String,Object> paging = installPlaceService.placePaging(page, installPlaceCount);
		
		response.setStatus("success");
        response.setCode(200); 
        response.setMessage(res + "개의 장소가 성공적으로 저장되었습니다.");
        response.setData(newInstallPlace);
        response.setPaging(paging);
        
		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.3-5. 주소 수정 [비동기]
	 * @Info: 상세 주소 수정
	 */
	@PostMapping("/detailaddress/{placesn}")
	@ResponseBody
	public ResponseEntity<PlaceApiResponse> updateInstallPlace(@PathVariable("placesn") String placesn, @RequestBody InstallPlace installPlace) {
		PlaceApiResponse response = new PlaceApiResponse();
		int placeSn = Integer.parseInt(placesn);
		
		String placeName = installPlace.getInstallPlaceName();
		
		InstallPlace place = installPlaceService.selectInstallPlaceBySn(placeSn);
		String originPlaceName = place.getInstallPlaceName();
		
		if(originPlaceName.equals(placeName)) {
			logger.info("origin: " + originPlaceName);
			int res = installPlaceService.updateInstallPlace(installPlace);
		}else {
			logger.info("not origin : " + originPlaceName);
			int nameCheck = installPlaceService.checkPlaceNameByName(placeName);
			logger.info("nameCheck: " + nameCheck);
			if(nameCheck >= 1) { 
				response.setStatus("error"); response.setCode(400);
				response.setMessage("이미 존재하는 장소 이름입니다."); 
				return ResponseEntity.ok().body(response); 
			}
			
			int res = installPlaceService.updateInstallPlace(installPlace);
		}
		
		int page = 1;
		List<InstallPlace> newInstallPlace = installPlaceService.selectAllPlace(page);
		int installPlaceCount = installPlaceService.selectInstallPlaceCount();
		
		Map<String,Object> paging = installPlaceService.placePaging(page, installPlaceCount);
		
		response.setStatus("success");
        response.setCode(200); 
        response.setMessage("장소가 성공적으로 수정되었습니다.");
        response.setData(newInstallPlace);
        response.setPaging(paging);
		return ResponseEntity.ok().body(response);
	}

	/*
	 *  @Author: [윤준성]
	 *  @API No.3-6. 주소 삭제 [비동기]
	 *  @Info: 기존 주소 삭제 
	 */
	@PostMapping("/delete/place")
	public ResponseEntity<Map<String,Object>> deleteInstallPlace(@RequestParam("placesn") int placesn) {
		logger.info("placesn: " + placesn);
		boolean res = installPlaceService.deleteInstallPlace(placesn);

		Map<String,Object> result = new HashMap<String, Object>();
		if (res) {
			int page = 1;
			List<InstallPlace> newInstallPlace = installPlaceService.selectAllPlace(1);
			int installPlaceCount = installPlaceService.selectInstallPlaceCount();
			Map<String,Object> paging = installPlaceService.placePaging(page, installPlaceCount);
			result.put("installPlace", newInstallPlace);
			result.put("paging", paging);
			return ResponseEntity.ok().body(result); // 성공 시 200 OK와 데이터 반환
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 실패 시 500 Internal Server Error와 실패 메시지 반환
		}
	}
}
