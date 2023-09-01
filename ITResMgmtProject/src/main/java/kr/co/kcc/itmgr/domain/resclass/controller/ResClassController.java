package kr.co.kcc.itmgr.domain.resclass.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.kcc.itmgr.domain.resclass.service.IResClassService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResClassController {
	
	private final IResClassService IResClassService;
	
	@RequestMapping(value="/resclass", method=RequestMethod.GET)
	public String selectAllResClass(Model model) {
//		List<Map<String, String>> resClass = IResClassService.selectAllResClass();
//		for(Map<String,String> resClassName : resClass) {
//			String[] levels = resClassName.get("resClassName2").split(",");
//
//		}
//		model.addAttribute("resClass", resClass);
//		System.out.println(resClass);
//		return "itres/resclass";
		
	    List<Map<String, String>> resClassList = IResClassService.selectAllResClass();

	    Map<String, List<String>> resClassMap = new LinkedHashMap<String, List<String>>();
	    List<String> levels = new ArrayList<>();
	    for (Map<String, String> resClass : resClassList) {
	    	String resClassName = resClass.get("resClassName");
	        String resClassName2 = resClass.get("resClassName2");

	        levels.add(resClass.get("lv"));
	        // ','로 분리된 값을 추출하여 저장
	        
	        String[] parts = resClassName2.split(",");
//	        for(int i=0; i< parts.length; i++) {
//	        	System.out.println(parts[i]);
//	        }
	        resClassMap.put(resClassName, Arrays.asList(parts));
	    }

	    model.addAttribute("resClassMap", resClassMap);
	    model.addAttribute("levels", levels);

	    return "itres/resclass";

	}
}
