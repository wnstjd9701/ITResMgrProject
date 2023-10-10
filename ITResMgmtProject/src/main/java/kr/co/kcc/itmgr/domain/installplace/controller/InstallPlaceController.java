package kr.co.kcc.itmgr.domain.installplace.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * @Info: 설치 장소 모두 조회
	 */
	@GetMapping("/installplace")
	public String selectInstallPlace(Model model) {
		List<InstallPlace> installPlace = installPlaceService.selectAllPlace();
		logger.info("installPlace: " + installPlace);
		model.addAttribute("installPlace", installPlace);

		return "place/installplace";
	}

	/*
	 * @Author: [윤준성]
	 * @API No.3-2. 설치 장소 검색 [비동기]
	 * @Info: 설치장소명으로 설치 장소 검색 
	 */
	@GetMapping("/installplace/search")
	@ResponseBody
	public List<InstallPlace> selectInstallPlaceByName(String placeName) {
		List<InstallPlace> installPlace = installPlaceService.searchInstallPlaceByName(placeName);
		logger.info("InstallPlaceList: " + installPlace);
		return installPlace;
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
		int nameCheck = installPlaceService.checkPlaceNameBySn(placeName);
		if(nameCheck >= 1) {
			response.setStatus("error");
            response.setCode(400); 
            response.setMessage("이미 존재하는 장소 이름입니다.");
			return ResponseEntity.ok().body(response);
		}
		int res = installPlaceService.insertInstallPlace(installPlace);
		List<InstallPlace> newInstallPlace = installPlaceService.selectAllPlace();
		
		response.setStatus("success");
        response.setCode(200); 
        response.setMessage(res + "개의 장소가 성공적으로 저장되었습니다.");
        response.setData(newInstallPlace);
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
		
		String placeName = installPlace.getInstallPlaceName();
		int nameCheck = installPlaceService.checkPlaceNameBySn(placeName);
		if(nameCheck >= 1) {
			response.setStatus("error");
            response.setCode(400); 
            response.setMessage("이미 존재하는 장소 이름입니다.");
			return ResponseEntity.ok().body(response);
		}
		int res = installPlaceService.updateInstallPlace(installPlace);
		List<InstallPlace> newInstallPlace = installPlaceService.selectAllPlace();
		
		response.setStatus("success");
        response.setCode(200); 
        response.setMessage("장소가 성공적으로 수정되었습니다.");
        response.setData(newInstallPlace);
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
			List<InstallPlace> newInstallPlace = installPlaceService.selectAllPlace();
			return ResponseEntity.ok(newInstallPlace); // 성공 시 200 OK와 데이터 반환
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail"); // 실패 시 500 Internal Server Error와 실패 메시지 반환
		}
	}
}
