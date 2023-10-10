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
		List<InstallPlace> installPlace = installPlaceService.selectAllPlace(1);
		
		int totalPage=0;
		if(installPlaceCount > 0) {
			totalPage=(int)Math.ceil(installPlaceCount / 5.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage / 5.0));
		int nowPageBlock = (int)Math.ceil(page / 5.0);
		int startPage = (nowPageBlock - 1) * 5 + 1;
		int endPage=0;
		if(totalPage > nowPageBlock * 5) {
			endPage = nowPageBlock * 5;
		}else {
			endPage = totalPage;
		}
		
		Map<String,Object> paging = new HashMap<String, Object>();
		paging.put("totalPageCount", totalPage);
		paging.put("nowPage", page);
		paging.put("totalPageBlock", totalPageBlock);
		paging.put("nowPageCount", nowPageBlock);
		paging.put("startPage", startPage);
		paging.put("endPage", endPage);
		
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
			if(page > 1) {
				int count = 0;
				for(InstallPlace place : installPlace) {
					place.setRn(startPage + count);
					count++;
				}
			}
			logger.info("installPlace: " + installPlace);
			return ResponseEntity.ok().body(installPlace);
		}else {
			List<InstallPlace> installPlace = installPlaceService.searchInstallPlaceByName(searchType, startPage, startPage + 4);
			return ResponseEntity.ok().body(installPlace);
		}
	}
	
	@GetMapping("/installPlace/next/{startNum}")
	public ResponseEntity<Map<String, Object>> installPlaceNextPaging(@PathVariable("startNum") int startNum,
	        @RequestParam("searchType") String searchType) {
	    logger.info("page: " + startNum + " searchType: " + searchType);

	    int pageSize = 5; // 한 페이지에 표시할 항목 수
	    int totalCount = installPlaceService.selectInstallPlaceCount();

	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    int currentPageBlock = (int) Math.ceil((double) startNum / pageSize);

	    int startPage = (currentPageBlock - 1) * pageSize + 1;
	    int endPage = Math.min(currentPageBlock * pageSize, totalPages);

	    Map<String, Object> paging = new HashMap<>();
	    paging.put("totalPageCount", totalPages);
	    paging.put("nowPage", startNum);
	    paging.put("totalPageBlock", currentPageBlock);
	    paging.put("nowPageCount", currentPageBlock);
	    paging.put("startPage", startPage);
	    paging.put("endPage", endPage);

	    List<InstallPlace> installPlace;

	    if (searchType.equals("ALL")) {
	        installPlace = installPlaceService.selectAllPlace(startNum);
	    } else {
	        installPlace = installPlaceService.searchInstallPlaceByName(searchType, startNum, startNum + 4);
	    }

	    Map<String, Object> result = new HashMap<>();
	    result.put("installPlace", installPlace);
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
		
		int totalPage=0;
		if(searchCount > 0) {
			totalPage=(int)Math.ceil(searchCount / 5.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage / 5.0));
		int nowPageBlock = (int)Math.ceil(page / 5.0);
		int startPage = (nowPageBlock - 1) * 5 + 1;
		int endPage=0;
		if(totalPage > nowPageBlock * 5) {
			endPage = nowPageBlock * 5;
		}else {
			endPage = totalPage;
		}
		
		Map<String,Object> paging = new HashMap<String, Object>();
		paging.put("totalPageCount", totalPage);
		paging.put("nowPage", 1);
		paging.put("totalPageBlock", totalPageBlock);
		paging.put("nowPageCount", nowPageBlock);
		paging.put("startPage", startPage);
		paging.put("endPage", endPage);
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
		List<InstallRes> resInfo = installPlaceService.selectResInformationByInstallPlaceName(placeName);
		InstallPlace installPlace = installPlaceService.selectInstallPlaceDetail(placeName);

		Map<String, Object> placeMap = new HashMap<String, Object>();
		placeMap.put("resInfo", resInfo);
		placeMap.put("installPlace", installPlace);
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
		
		int totalPage=0;
		if(installPlaceCount > 0) {
			totalPage=(int)Math.ceil(installPlaceCount / 5.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage / 5.0));
		int nowPageBlock = (int)Math.ceil(page / 5.0);
		int startPage = (nowPageBlock - 1) * 5 + 1;
		int endPage=0;
		if(totalPage > nowPageBlock * 5) {
			endPage = nowPageBlock * 5;
		}else {
			endPage = totalPage;
		}
		
		Map<String,Object> paging = new HashMap<String, Object>();
		paging.put("totalPageCount", totalPage);
		paging.put("nowPage", 1);
		paging.put("totalPageBlock", totalPageBlock);
		paging.put("nowPageCount", nowPageBlock);
		paging.put("startPage", startPage);
		paging.put("endPage", endPage);
		
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
		
		int totalPage=0;
		if(installPlaceCount > 0) {
			totalPage=(int)Math.ceil(installPlaceCount / 5.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage / 5.0));
		int nowPageBlock = (int)Math.ceil(page / 5.0);
		int startPage = (nowPageBlock - 1) * 5 + 1;
		int endPage=0;
		if(totalPage > nowPageBlock * 5) {
			endPage = nowPageBlock * 5;
		}else {
			endPage = totalPage;
		}
		
		Map<String,Object> paging = new HashMap<String, Object>();
		paging.put("totalPageCount", totalPage);
		paging.put("nowPage", 1);
		paging.put("totalPageBlock", totalPageBlock);
		paging.put("nowPageCount", nowPageBlock);
		paging.put("startPage", startPage);
		paging.put("endPage", endPage);
		
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
	public ResponseEntity<Object> deleteInstallPlace(@RequestParam("placesn") int placesn) {
		logger.info("placesn: " + placesn);
		boolean result = installPlaceService.deleteInstallPlace(placesn);

		if (result) {
			List<InstallPlace> newInstallPlace = installPlaceService.selectAllPlace(1);
			return ResponseEntity.ok(newInstallPlace); // 성공 시 200 OK와 데이터 반환
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail"); // 실패 시 500 Internal Server Error와 실패 메시지 반환
		}
	}
}
