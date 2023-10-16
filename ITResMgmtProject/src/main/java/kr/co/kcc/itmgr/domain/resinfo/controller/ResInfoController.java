package kr.co.kcc.itmgr.domain.resinfo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.resclass.controller.ResClassController;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
import kr.co.kcc.itmgr.domain.resinfo.service.IResInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResInfoController {

	private final IResInfoService resInfoService;
	static final Logger logger = LoggerFactory.getLogger(ResClassController.class);

	@RequestMapping(value="/resinfo/{page}")
	public String selectAllResInfo(Model model,@PathVariable int page,HttpSession session) {
		session.setAttribute("page", page);

		List<ResInfo> selectAllResInfo = resInfoService.selectAllResInfo(page);
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

		model.addAttribute("selectAllResInfo", selectAllResInfo);
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageCount", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

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
		logger.info("dsdss"+resClassMap);
		return "resinfo/resinfo";
	}

	@GetMapping("/resinfo/additem")
	@ResponseBody
	public List<ResInfo> selectMappingAddItem(@RequestParam("resClassId") String resClassId){
		List<ResInfo> selectMappingAddItem = resInfoService.selectMappingAddItem(resClassId);
		return selectMappingAddItem;
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
		resInfo.setResClassId(resInfo.getResClassId());
		resInfo.setMgmtId(resInfo.getMgmtId());
		resInfo.setMgmtDeptName(resInfo.getMgmtDeptName());
		resInfo.setResName(resInfo.getResName());
		resInfo.setResStatusCode(resInfo.getResStatusCode());
		resInfo.setManagerName(resInfo.getManagerName());
		resInfo.setResSerialId(resInfo.getResSerialId());
		resInfo.setManufactureCompanyName(resInfo.getManufactureCompanyName());
		resInfo.setModelName(resInfo.getModelName());
		resInfo.setInstallPlaceSn(resInfo.getInstallPlaceSn());
		resInfo.setRackInfo(resInfo.getRackInfo());
		resInfo.setResSn(resInfo.getResSn());
		resInfo.setIntroductionDate(resInfo.getIntroductionDate());
		resInfo.setExpirationDate(resInfo.getExpirationDate());
		resInfo.setIntroductionPrice(resInfo.getIntroductionPrice());
		resInfo.setUseYn(resInfo.getUseYn());
		resInfo.setMonitoringYn(resInfo.getMonitoringYn());
		resInfo.setPurchaseCompanyName(resInfo.getPurchaseCompanyName());
		resInfo.setAddInfo(resInfo.getAddInfo());
		resInfoService.insertResInfo(resInfo);
	}

	@GetMapping("/resinfo/detail")
	@ResponseBody
	public ResInfo selectResInfoDetail(@RequestParam("resName")String resName) {
		ResInfo selectResInfoDetail  = resInfoService.selectResInfoDetail(resName);
		return selectResInfoDetail;
	}
}
