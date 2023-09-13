package kr.co.kcc.itmgr.domain.resinfo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.resclass.controller.ResClassController;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
import kr.co.kcc.itmgr.domain.resinfo.service.IResInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResInfoController {
	
	private final IResInfoService resInfoService;
	static final Logger logger = LoggerFactory.getLogger(ResClassController.class);

	@RequestMapping(value="/resinfo")
	public String selectAllResInfo(Model model) {
		List<ResInfo> selectAllResInfo = resInfoService.selectAllResInfo();
		model.addAttribute("selectAllResInfo", selectAllResInfo);
		List<ResInfo> searchResInfoByResClass = resInfoService.searchResInfoByResClass();
		model.addAttribute("search", searchResInfoByResClass);
		return "resinfo/resinfo";
	}
	
	@GetMapping("/resinfo/search")
	@ResponseBody
	public List<ResInfo> searchResInfo(ResInfo resInfo) {
		List<ResInfo> searchResInfo = resInfoService.searchResInfo(resInfo);
		logger.info("'"+searchResInfo);
		return searchResInfo;
	}
}
