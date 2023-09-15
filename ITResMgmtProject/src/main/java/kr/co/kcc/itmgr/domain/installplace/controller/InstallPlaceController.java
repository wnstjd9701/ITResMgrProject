package kr.co.kcc.itmgr.domain.installplace.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.service.IInstallPlaceService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InstallPlaceController {
	
	static final Logger logger = LoggerFactory.getLogger(InstallPlaceController.class);
	
	private final IInstallPlaceService installPlaceService;
	
	/*
	 * API No.3-1. 모든 설치 장소 조회
	 * Info: 설치 장소 모두 조회
	 */
	@GetMapping("/installplace")
	public String selectInstallPlace(Model model) {
		List<InstallPlace> installPlace = installPlaceService.selectAllAddress();
		logger.info("installPlace: " + installPlace);
		model.addAttribute("installPlace", installPlace);
		
		return "place/installplace";
	}
}
