package kr.co.kcc.itmgr.domain.resclass.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resclass.model.ResClassAddItem;
import kr.co.kcc.itmgr.domain.resclass.service.IResClassService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResClassController {

	static final Logger logger = LoggerFactory.getLogger(ResClassController.class);
	private final IResClassService resClassService;

	/*
	 * Author: [조한나]
	 * API No1-1. 자원분류 메뉴트리
	 * Info : 자원분류 메뉴트리 및 자원분류에 해당하는 자원의 수 
	 */
	@RequestMapping(value="/resclass" , method=RequestMethod.GET)
	public String selectAllResClass(Model model,String upperResClassId) {
		Map<String, Map<String, List<String>>> resClassMap = new LinkedHashMap<>();

		List<ResClass> resClassList = resClassService.selectAllResClass();

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
	
		List<Map<Object, Object>> numberOfRes = resClassService.numberOfResByResClass();
		Map<Object, Object> numOfRes = new HashMap<>();
		Map<Object, Object> numOfRes2 = new HashMap<>();

		  for(int i = 0; i < numberOfRes.size(); i++) {
			    Object key = (String) numberOfRes.get(i).get("resClassName");
			    int value = Integer.parseInt(String.valueOf(numberOfRes.get(i).get("mappingNumberOfRes")));
			    numOfRes.put(key, value);
			  }
		
		  for(int i = 0; i < numberOfRes.size(); i++) {
			    Object key = (String) numberOfRes.get(i).get("upperResClassId");
			    int value = Integer.parseInt(String.valueOf(numberOfRes.get(i).get("mappingNumberOfRes")));
			    numOfRes2.put(key, value);
			  }
			 
		  
		model.addAttribute("numOfRes", numOfRes);
	  	model.addAttribute("numOfRes2", numOfRes2);
	  	
	  	List<ResClass> selectResClassByLevel = resClassService.selectResClassByLevel();
	  	model.addAttribute("selectResClassByLevel", selectResClassByLevel);
		
		return "resclass/resclass"; 
	}
	
	/*
	 * Author: [조한나]
	 * API No1-2. 자원분류 상세조회[비동기]
	 * Info : 자원분류 트리메뉴에서 클릭시 그 자원에 대한 상세정보 및 부가항목
	 */
	@GetMapping("/resclassdetail")
	@ResponseBody
	public List<ResClass> selectResClassByResClassName(@RequestParam("resClassName")String resClassName){
		List<ResClass> selectResClassByResClassName = resClassService.selectResClassByResClassName(resClassName);
		
		logger.info("sssss"+selectResClassByResClassName);
		return selectResClassByResClassName;
	}
	
	/*
	 * Author: [조한나]
	 * API No1-3. 자원분류 신규등록[동기]
	 * Info : 자원분류 INSERT
	 */
	@PostMapping("/resclass/insert")
	public String insertResClassInsert(ResClass resClass, Model model) {
		resClassService.insertResClass(resClass);
		return"redirect:/resclass";
	}
	
	/*
	 * Author: [조한나]
	 * API No1-4. 부가항목 리스트 조회[비동기]
	 * Info : 자원분류에서 자원분류하나에 부가항목리스트 조회하는 모달
	 */
	@GetMapping("/resclass/additem")
	@ResponseBody
	public Map<String, Object> selectAddItemInResClass(){
		JSONArray result = new JSONArray();
		List<AddItem> selectAddItemInResClass = resClassService.selectAddItemInResClass();
		Map<String, Object> test = new HashMap<String, Object>();
		test.put("test",selectAddItemInResClass);
		return test;
	}

	/*
	 * Author: [조한나]
	 * API No1-5. 부가항목 리스트 등록[동기]
	 * Info : 자원분류에서 자원분류하나에 부가항목 등록
	 */
//	@PostMapping("/resclass/additeminsert")
//	@ResponseBody
//	public String insertAddItemToResClass(@RequestParam("resClassId")String resClassId, @RequestParam("addItemSn") List<Integer> addItemSn) {
//
//		ResClassAddItem resClassAddItem = new ResClassAddItem();
//		resClassAddItem.setAddItemSn(addItemSn);
//		resClassAddItem.setResClassId(resClassId);
//
//		resClassService.insertAddItemToResClass(resClass);
//		return "redirect:/resclass";
//	}
	
	@PostMapping("/resclass/additem")
	@ResponseBody
	public Map<String, Object> saveResClass(@RequestBody List<ResClass> resClassList){
		System.out.println("아니여긴타나");
		Stream<ResClass> streamResClass = resClassList.stream();
		Map<String, List<ResClass>> groupedResClass = streamResClass.collect(Collectors.groupingBy(ResClass::getFlag));
		if(groupedResClass.containsKey("C")) {
			List<ResClass> insertList = groupedResClass.get("C");
			logger.info("insertList:"+insertList);

			int addItemResult = resClassService.insertAddItemToResClass(resClassList);
		}
		
		else if(groupedResClass.containsKey("U")) {
			List<ResClass> updateResClassList = groupedResClass.get("U");
			int updateRow = updateResClassList.stream()
							.mapToInt(resClassService::updateResClass)
							.sum();
			logger.info("updateRow:" + updateRow);	
		}
		
		else if (groupedResClass.containsKey("D")) {
			System.out.println("여긴타나");
		    List<ResClass> deleteList = groupedResClass.get("D");
		    logger.info("deleteList:"+deleteList);
		    int deleteRow = deleteList.stream()
                    .mapToInt(resClassService::deleteAddItemInResClass)
                    .sum();
		}
		Map<String,Object> resClassAddItemMap = new HashMap<String, Object>();
		return resClassAddItemMap;
	}
}
