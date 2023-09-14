package kr.co.kcc.itmgr.domain.commoncode.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.commoncode.service.ICommonCodeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommonCodeController {

	static final Logger logger = LoggerFactory.getLogger(CommonCodeController.class);

	private final ICommonCodeService commonCodeService;

	/*
	 * API No2-1. 공통 코드 관리
	 * Info : 공통 코드, 상세 코드 조회 API 
	 */
	@GetMapping("/commoncode")
	public String commonCode(Model model) {
		List<CommonCode> commonCode = commonCodeService.selectAllCommonCode();
		List<CommonCodeDetail> commonCodeDetail = commonCodeService.selectAllCommonCodeDetail();
		
		model.addAttribute("commonCode", commonCode);
		model.addAttribute("commonCodeDetail", commonCodeDetail);
		return "code/commoncode";
	}
	
	/*
	 * API No2-2. 공통 코드 그룹 존재 여부 체크
	 * Info : 공통 코드 그룹 체크 API [비동기 처리]
	 */
	@GetMapping("/checkcodegroup")
	@ResponseBody
	public int checkCodeGroupId(@RequestParam("codeGroupId") String codeGroupId) {
		int result = commonCodeService.checkIfCodeGroupIdExists(codeGroupId);
		return result;
	}
	
	/*
	 * API No2-3. 공통 코드 검색
	 * Info : 공통 코드 검색 [사용 여부, 코드 그룹명 및 코드 그룹 ID] / [비동기 처리]
	 */
	@GetMapping("/search/commoncode")
	@ResponseBody
	public List<CommonCode> searchCommonCode(@RequestParam("useYn") String useYn, @RequestParam("keyword") String keyword) {
		List<CommonCode> commonCode = commonCodeService.selectCommonCodeBySearch(useYn, keyword);
		logger.info("commonCode: " + commonCode);
		return commonCode;
	}
	
	/*
	 * API No2-4. 공통 코드 저장 버튼 클릭 시
	 * Info : 저장 버튼 클릭 시 C/U/D [비동기 처리]
	 */
	@PostMapping("/commoncode")
	@ResponseBody
	public Map<String, Object> commonCodeSave(@RequestBody List<CommonCode> commonCodeList){
		/*
		 *  Flag에 따른 저장 로직
		 *  C - Insert / U - Update / D - Delete (사용 여부 N으로 바꿔주기)
		 */
		Stream<CommonCode> streamCommonCode = commonCodeList.stream();
		Map<String, List<CommonCode>> groupedCommonCode = streamCommonCode.collect(Collectors.groupingBy(CommonCode::getFlag)); // Flag가 Key값이 됨
		
		if (groupedCommonCode.containsKey("C")) {
			List<CommonCode> insertList = groupedCommonCode.get("C");
	    	logger.info("insertList: " + insertList);
	    	
	    	//int insertResult = insertList.forEach(commonCode -> commonCodeService.insertCommonCode(commonCode));
	    	int insertResult = commonCodeService.insertCommonCode(insertList);
	    	logger.info("insertResult: " + insertResult);
		}
		
		else if (groupedCommonCode.containsKey("U") || groupedCommonCode.containsKey("D")) {
			List<CommonCode> updateList = groupedCommonCode.get("U");
	    	logger.info("updateList: " + updateList);

	    	int updateRow = updateList.stream()
	    	        .mapToInt(commonCodeService::updateCommonCode)
	    	        .sum();
	    	logger.info("updateRow: " + updateRow);
		}
		
		// 저장 후 다시 값들을 받아와서 return 
		Map<String, Object> commonCodeMap = new HashMap<String, Object>();
		
		List<CommonCode> commonCodeResult = commonCodeService.selectAllCommonCode();
		List<CommonCodeDetail> commonCodeDetailResult = commonCodeService.selectAllCommonCodeDetail();
		
		commonCodeMap.put("commonCode", commonCodeResult);
		commonCodeMap.put("commonCodeDetail", commonCodeDetailResult);
		return commonCodeMap;
	}
	
	/*
	 * API No2-5. 공통 코드에 대한 상세 코드 가져오기
	 * Info : 공통 코드 클릭 시 상세 코드 정보 가져오기 [비동기 처리]
	 */
	@GetMapping("/commoncodedetail")
	@ResponseBody 
    public List<CommonCodeDetail> selectCommonCodeDetail(@RequestParam("codeGroupId") String codeGroupId){
		List<CommonCodeDetail> commonCodeDetail = commonCodeService.selectCommonCodeDetailByCodeGroupId(codeGroupId);
		logger.info("commonCodeDetail: " + commonCodeDetail);
		return commonCodeDetail;
	}
	
	/*
	 * API No2-6. 상세 코드 검색
	 * Info : 상세 코드 검색 [사용 여부, 상세 코드 및 상세 코드명] / [비동기 처리]
	 */
	@GetMapping("/search/commoncodedetail")
	@ResponseBody
	public List<CommonCodeDetail> searchCommonCodeDetail(@RequestParam("useYn") String useYn, @RequestParam("keyword") String keyword){
		List<CommonCodeDetail> commonCodeDetail = commonCodeService.selectCommonCodeDetailBySearch(useYn, keyword);
		logger.info("commonCodeDetail: " + commonCodeDetail);
		return commonCodeDetail;
	}
	
	/*
	 * API No2-7. 상세 코드 저장
	 * Info : 상세 코드 저장 [비동기 처리]
	 */
	@PostMapping("/commoncodedetail")
	@ResponseBody
	public Map<String, Object> saveCommonCodeDetail(@RequestBody List<CommonCodeDetail> commonCodeDetailList){
		Map<String, List<CommonCodeDetail>> groupedCommonCode = commonCodeDetailList.stream()
				.collect(Collectors.groupingBy(CommonCodeDetail::getFlag)); // Flag가 Key값이 됨

		if (groupedCommonCode.containsKey("C")) {
			List<CommonCodeDetail> insertList = groupedCommonCode.get("C");
			commonCodeService.insertCommonCodeDetail(insertList);
		}
		else if(groupedCommonCode.containsKey("U") || groupedCommonCode.containsKey("D")) {
			List<CommonCodeDetail> updateList = groupedCommonCode.get("U");
			int updateRow = updateList.stream()
	    	        .mapToInt(commonCodeService::updateCommonCodeDetail)
	    	        .sum();
		}
		
		Map<String, Object> commonCodeMap = new HashMap<String, Object>();
		
		List<CommonCode> commonCodeResult = commonCodeService.selectAllCommonCode();
		List<CommonCodeDetail> commonCodeDetailResult = commonCodeService.selectAllCommonCodeDetail();
		
		commonCodeMap.put("commonCode", commonCodeResult);
		commonCodeMap.put("commonCodeDetail", commonCodeDetailResult);
		return commonCodeMap;
	}
}
