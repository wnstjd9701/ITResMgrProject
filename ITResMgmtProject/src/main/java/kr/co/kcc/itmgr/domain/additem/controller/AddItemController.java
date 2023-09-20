package kr.co.kcc.itmgr.domain.additem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;
import kr.co.kcc.itmgr.domain.additem.service.IAddItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AddItemController {
	static final Logger logger = LoggerFactory.getLogger(AddItemController.class);
	
	private final IAddItemService addItemService;
	
	@RequestMapping(value="/addItem", method=RequestMethod.GET)
	public String selectAddItem(Model model) {
		List<AddItem> addItemList = addItemService.selectAllAddItem();
		model.addAttribute("addItemList", addItemList);
		return "additem/additem";
	}
	
	//검색
	@RequestMapping(value="/search/addItem", method = RequestMethod.GET)
	@ResponseBody
	public List<AddItem> selectSearchAddItem(@RequestParam Map<String, String> searchAddItemData) {
		List<AddItem> addItemList = new ArrayList<>(); 

		try {
			String useYN = searchAddItemData.get("searchAddItemUseYN");
			String searchText = searchAddItemData.get("searchAddItemText");
			
			logger.info("useYN",useYN);
			logger.info("searchText",searchText);

			addItemList = addItemService.selectSearchAddItem(useYN, searchText);

		}catch(Exception e){
			e.printStackTrace();
		}
			return addItemList;
	}  
}
