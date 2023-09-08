package kr.co.kcc.itmgr.domain.commoncode.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;
import kr.co.kcc.itmgr.domain.commoncode.service.ICommonCodeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommonCodeController {
	
	private final ICommonCodeService commonCodeService;
	
	@GetMapping("/commoncode")
	public String commonCode(Model model) {
		List<CommonCode> commonCode = commonCodeService.selectAllCommonCode();

		model.addAttribute("commonCode", commonCode);
		return "code/commoncode";
	}
}
