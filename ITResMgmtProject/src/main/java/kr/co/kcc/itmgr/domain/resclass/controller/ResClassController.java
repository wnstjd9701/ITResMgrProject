package kr.co.kcc.itmgr.domain.resclass.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	static final Logger logger = LoggerFactory.getLogger(ResClassController.class);
	private final IResClassService IResClassService;


	@RequestMapping(value="/resclass" , method=RequestMethod.GET)
	public String selectAllResClass(Model model) {

		Map<String, Map<String, List<String>>> resClassMap = new LinkedHashMap<>();

		List<ResClass> resClassList = IResClassService.selectAllResClass();
		for(ResClass r : resClassList) {
			if(r.getUpperResClassId()==null) {
				String[] resClassName2s = r.getResClassName2().split(",");
				Map<String, List<String>> tempMap = new LinkedHashMap<String, List<String>>();
				for(String resClassName2 : resClassName2s) {
					tempMap.put(resClassName2, new ArrayList<>());
				}
				resClassMap.put(r.getResClassName(), tempMap);
			}
		}

		for(ResClass r : resClassList) {

			if( r.getUpperResClassId()!=null){
				if(r.getUpperResClassId().equals("HW_000000")) {
					Map<String, List<String>> tempMap2 = resClassMap.get("하드웨어");
					List<String> temp2 = tempMap2.get(r.getResClassName());
					temp2 = Arrays.asList(r.getResClassName2().split(","));
					tempMap2.put(r.getResClassName(), temp2);
				}else if(r.getUpperResClassId().equals("SW_000000")) {
					Map<String, List<String>> tempMap3 = resClassMap.get("소프트웨어");
					List<String> temp3 = tempMap3.get(r.getResClassName());
					temp3 = Arrays.asList(r.getResClassName2().split(","));
					tempMap3.put(r.getResClassName(), temp3);												
				}
			}
		}

		model.addAttribute("resClassMap", resClassMap);

	
		List<Map<Object, Object>> numberOfRes = IResClassService.numberOfResByResClass();
		Map<Object, Object> numOfRes = new HashMap<>();

			  for(int i = 0; i < numberOfRes.size(); i++) {
				    Object key = (String) numberOfRes.get(i).get("resClassName");
				    Object value = numberOfRes.get(i).get("mappingNumberOfRes");
				    numOfRes.put(key, value);
				  }


		model.addAttribute("numOfRes", numOfRes);
		return "itres/resclass"; 
	}
	

}
