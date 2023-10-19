package kr.co.kcc.itmgr.domain.resinfo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.resclass.controller.ResClassController;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfoDetailDTO;
import kr.co.kcc.itmgr.domain.resinfo.service.IResInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResInfoController {

	private final IResInfoService resInfoService;
	static final Logger logger = LoggerFactory.getLogger(ResClassController.class);

	@GetMapping("/resinfo")
	public String selectAllResInfo(Model model) {
		int page = 1;

		List<ResInfo> selectAllResInfo = resInfoService.selectAllResInfo(page);
		Map<String, Object> resInfoMap = new HashMap<String, Object>();
		
		int countOfResList = resInfoService.countOfResInfo();
		int totalPage = 0;
		if(countOfResList > 0) {
			totalPage= (int)Math.ceil(countOfResList/10.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage/10.0));
		int nowPageBlock = (int) Math.ceil(page/10.0);
		int startPage = (nowPageBlock-1)*10 + 1;
		int endPage = 0;
		if(totalPage > nowPageBlock*10) {
			endPage = nowPageBlock*10;
		}else {
			endPage = totalPage;
		}
		
		Map<String,Object> page2 = new HashMap<String, Object>();
		
		page2.put("totalPageCount", totalPage);
		page2.put("nowPage", page);
		page2.put("totalPageBlock", totalPageBlock);
		page2.put("nowPageCount", nowPageBlock);
		page2.put("startPage", startPage);
		page2.put("endPage", endPage);
		
		resInfoMap.put("selectAllResInfo",selectAllResInfo);
		resInfoMap.put("page",page2);
		model.addAttribute("selectAllResInfo", selectAllResInfo);
		model.addAttribute("page2", page2);

		List<ResInfo> searchResInfoByResClass = resInfoService.searchResInfoByResClass();
		model.addAttribute("search", searchResInfoByResClass);

		List<CommonCodeDetail> selectResStatusCode = resInfoService.selectResStatusCode("RES000");
		model.addAttribute("selectResStatusCode", selectResStatusCode);

		List<InstallPlace> selectResInstallPlace = resInfoService.selectResInstallPlace();
		model.addAttribute("selectResInstallPlace", selectResInstallPlace);
		List<ResClass> resClassList = resInfoService.selectAllResClass();
		Map<String, Map<String, Map<String, String>>> resClassMap = new HashMap<String, Map<String, Map<String, String>>>();

		// 데이터를 반복하면서 맵에 추가
		
		for (ResClass r : resClassList) {
			if (r.getUpperResClassName() == null) {
				resClassMap.put(r.getResClassName(), null);
			}
		}
		for (String key: resClassMap.keySet()) {
			Map<String, Map<String, String>> map2 = new HashMap<String, Map<String,String>>();
		    for (ResClass r : resClassList) {
		        if (key.equals(r.getResClassName())) {
		        		map2.put(r.getResClassName2(), null);
		            }
		    }
		    if(!map2.isEmpty()) {
		    for(String key2 : map2.keySet()) {
		    	Map<String, String> map3 = new HashMap<>();
		    	for(ResClass r2 : resClassList) {
			    	if(key2.equals(r2.getResClassName()) && key.equals(r2.getUpperResClassName())) {
			    			map3.put(r2.getResClassName2(), r2.getResClassId());
			    	}
			    	map2.put(key2, map3);
			    }
		    }
		}
		    resClassMap.put(key, map2);
		}
		model.addAttribute("resClassMap", resClassMap);
		return "resinfo/resinfo";
	}
	
	@GetMapping("/resinfopagination")
	@ResponseBody
	public Map<String,Object> selectResinfoPagination(int page){

		List<ResInfo> selectAllResInfo = resInfoService.selectAllResInfo(page);
		Map<String, Object> resInfoMap = new HashMap<String, Object>();
		
		int countOfResList = resInfoService.countOfResInfo();
		int totalPage = 0;
		if(countOfResList > 0) {
			totalPage= (int)Math.ceil(countOfResList/10.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage/10.0));
		int nowPageBlock = (int) Math.ceil(page/10.0);
		int startPage = (nowPageBlock-1)*10 + 1;
		int endPage = 0;
		if(totalPage > nowPageBlock*10) {
			endPage = nowPageBlock*10;
		}else {
			endPage = totalPage;
		}
		
		Map<String,Object> page2 = new HashMap<String, Object>();
		
		page2.put("totalPageCount", totalPage);
		page2.put("currentPage", page);
		page2.put("totalPageBlock", totalPageBlock);
		page2.put("nowPageCount", nowPageBlock);
		page2.put("startPage", startPage);
		page2.put("endPage", endPage);
		
		resInfoMap.put("selectAllResInfo",selectAllResInfo);
		resInfoMap.put("page",page2);
		return resInfoMap;
	}
	
	@PostMapping("/resinfo/update")
	@ResponseBody
	public void updateResInfo(@RequestBody ResInfo resInfo) {
		System.out.println(resInfo.toString());
		try {
			resInfoService.updateResInfo(resInfo);
			String resSerialId = resInfo.getResSerialId();
			int count = resInfoService.CountOfAddItemValueInResInfo(resSerialId);
			try {
				if(count >1) {
					logger.info("=====================" + resInfo.getAddItemSnList());
					logger.info("=====================" + resInfo.getResDetailValueList());
					logger.info("=====================" + resSerialId);
					resInfoService.deleteAddItemValueInResInfo(resSerialId);
					resInfoService.insertAddItemValueInResInfo(resSerialId, resInfo.getAddItemSnList(), resInfo.getResDetailValueList());
				}else{
					logger.info("=====================" + resInfo.getAddItemSnList());
					logger.info("=====================" + resInfo.getResDetailValueList());
					logger.info("=====================" + resSerialId);
					resInfoService.insertAddItemValueInResInfo(resSerialId, resInfo.getAddItemSnList(), resInfo.getResDetailValueList());
					System.out.println("=============================================================================================");
				}
			}catch(Exception e){
				e.getMessage();
				System.out.println("예외가 발생했습니다: " + e.toString());
			}
		}catch(Exception e) {
			e.getMessage();
		}
	}
	

	@GetMapping("/resinfo/additem")
	@ResponseBody
	public List<ResInfo> selectMappingAddItem(@RequestParam("resClassId") String resClassId){
		List<ResInfo> selectMappingAddItem = resInfoService.selectMappingAddItem(resClassId);
		return selectMappingAddItem;
	}
	
	@PostMapping("/resinfo/additemvalue")
	@ResponseBody
	public void insertAddItemValueInResInfo(@RequestBody List<ResInfoDetailDTO> resInfoList){
//		String resSerialId = resInfoList.get(0).getResSerialId();
//		int count = resInfoService.CountOfAddItemValueInResInfo(resSerialId);
//		System.out.println("아아아악!!!"+count);
//		if(count >1) {
//			resInfoService.deleteAddItemValueInResInfo(resSerialId);
//			resInfoService.insertAddItemValueInResInfo(resInfoList);
//		}else{
//			resInfoService.insertAddItemValueInResInfo(resInfoList);
//		}
	}
	
	@GetMapping("/resinfo/additemvalue")
	@ResponseBody
	public List<ResInfo> selectAddItemValueInResInfo(String resSerialId){
		List<ResInfo> selectAddItemValueInResInfo = resInfoService.selectAddItemValueInResInfo(resSerialId);
		return selectAddItemValueInResInfo;
	}

	@GetMapping("/resinfo/search")
	@ResponseBody
	public List<ResInfo> searchResInfo(ResInfo resInfo) {
		List<ResInfo> searchResInfo = resInfoService.searchResInfo(resInfo);
		return searchResInfo;
	}

	@PostMapping("/resinfo/insert")
	@ResponseBody
	public void insertResInfo(@RequestBody ResInfo resInfo) {
		resInfoService.insertResInfo(resInfo);
	}

	@GetMapping("/resinfo/detail")
	@ResponseBody
	public ResInfo selectResInfoDetail(@RequestParam("resName")String resName) {
		ResInfo selectResInfoDetail  = resInfoService.selectResInfoDetail(resName);
		return selectResInfoDetail;
	}
}
