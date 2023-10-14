package kr.co.kcc.itmgr.domain.ipinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.kcc.itmgr.domain.ipinfo.service.IIpInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class IpInfoController {

	private final IIpInfoService ipInfoService;
	
	@GetMapping("/ip")
	public String selectIpInfo(Model model, HttpSession session) {
		return "/ip/ipinfo";
	}
}
