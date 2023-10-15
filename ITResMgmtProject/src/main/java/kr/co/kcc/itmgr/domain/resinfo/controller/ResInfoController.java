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

		Map<String,List<Map<String,List<Map<String,String>>>>> lev1 = new HashMap<String, List<Map<String,List<Map<String,String>>>>>();
		// 데이터를 반복하면서 맵에 추가
        for (ResClass r : resClassList) {
        	List<Map<String,List<Map<String,String>>>> lev2List = new ArrayList<Map<String,List<Map<String,String>>>>();
            if (r.getUpperResClassName() == null) {
            	if(!lev1.containsKey(r.getResClassName())) {
            		Map<String,List<Map<String,String>>> lev2= new HashMap<String, List<Map<String,String>>>();
            		List<Map<String,String>> lev3List=new ArrayList<Map<String,String>>(); 
            		lev2.put(r.getResClassName2(), lev3List);
            		lev2List.add(lev2);
            		
            		lev1.put(r.getResClassName(), lev2List);
            	}else {
            		Map<String,List<Map<String,String>>> lev2= new HashMap<String, List<Map<String,String>>>();
            		List<Map<String,String>> lev3List=new ArrayList<Map<String,String>>(); 
            		
            		lev2.put(r.getResClassName2(), lev3List);
            		lev2List=lev1.get(r.getResClassName());
            		
            		lev2List.add(lev2);
            		lev1.put(r.getResClassName(), lev2List);
            	}
            }else {
            	Map<String,List<Map<String,String>>> lev2=new HashMap<String, List<Map<String,String>>>();
            	List<Map<String,String>> lev3List= new ArrayList<Map<String,String>>();
            	Map<String,String> lev3 = new HashMap<String, String>();

            	lev2List=lev1.get(r.getUpperResClassName());
            	for(int i=0;i<lev2List.size();i++) {
            		if(lev2List.get(i).containsKey(r.getResClassName())) {
            			lev2=lev2List.get(i);
            			lev3List=lev2List.get(i).get(r.getResClassName());
            			lev2List.remove(i);
            			break;
            		}
            	}
            	lev3.put(r.getResClassName2(), r.getResClassId());
            	lev3List.add(lev3);
            	lev2.put(r.getResClassName(), lev3List);
            	
            	lev2List.add(lev2);
            	lev1.put(r.getUpperResClassName(), lev2List);
            }
        }
        logger.info("resClassList: "+lev1.toString());
        model.addAttribute("resClassList", lev1);


        return "resinfo/resinfo";
    }

	
	@GetMapping("/resinfo/additem")
	@ResponseBody
	public List<ResInfo> selectMappingAddItem(@RequestParam("resSerialId") String resSerialId){
		List<ResInfo> selectMappingAddItem = resInfoService.selectMappingAddItem(resSerialId);
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
