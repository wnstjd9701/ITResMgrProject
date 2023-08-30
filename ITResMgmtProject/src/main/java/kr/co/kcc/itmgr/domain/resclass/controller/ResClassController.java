package kr.co.kcc.itmgr.domain.resclass.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resclass.service.IResClassService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResClassController {
	
	private final IResClassService IResClassService;
	
	@RequestMapping(value="/resclass", method=RequestMethod.GET)
	public String selectAllResClass(Model model) {
		List<ResClass> resClassList = IResClassService.selectAllResClass();
		model.addAttribute("resClassList", resClassList);
		return "resclass/resclass";
	}
}
