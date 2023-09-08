package kr.co.kcc.itmgr.domain.commoncode.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * API No2. 공통 코드 관리
	 * Info : 공통 코드, 상세 코드 조회 API 
	 */
	@GetMapping("/commoncode")
	public String commonCode(Model model) {
		List<CommonCode> commonCodeResult = commonCodeService.selectAllCommonCode();
		List<CommonCodeDetail> commonCodeDetailResult = commonCodeService.selectAllCommonCodeDetail().stream()
				.map(c -> {
					c.setFlag("E");
					return c;
				})
				.collect(Collectors.toList());
		logger.info("result" + commonCodeDetailResult);
		List<CommonCode> commonCode = commonCodeResult.stream()
				.map(c -> {
					c.setFlag("E");
					return c;
				})
				.collect(Collectors.toList());
		List<CommonCodeDetail> commonCodeDetail = commonCodeDetailResult.stream()
				.map(c -> {
					c.setFlag("E");
					return c;
				})
				.collect(Collectors.toList());
		
		model.addAttribute("commonCode", commonCode);
		model.addAttribute("commonCodeDetail", commonCodeDetail);
		return "code/commoncode";
	}
	
	/*
	 * API No3. 공통 코드 그룹 체크
	 * Info : 공통 코드 그룹 체크 API [비동기 처리]
	 */
	@GetMapping("/checkcodegroup")
	@ResponseBody
	public int checkCodeGroupId(@RequestParam("codeGroupId") String codeGroupId) {
		int result = commonCodeService.checkIfCodeGroupIdExists(codeGroupId);
		return result;
	}
}
