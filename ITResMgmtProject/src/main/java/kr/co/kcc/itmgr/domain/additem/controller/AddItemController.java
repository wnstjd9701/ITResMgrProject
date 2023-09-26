package kr.co.kcc.itmgr.domain.additem.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.kcc.itmgr.domain.additem.model.AddItem;
import kr.co.kcc.itmgr.domain.additem.service.IAddItemService;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;






@Controller
@RequiredArgsConstructor
public class AddItemController {
	static final Logger logger = LoggerFactory.getLogger(AddItemController.class);

	private final IAddItemService addItemService;

	@RequestMapping(value="/additem", method=RequestMethod.GET)
	public String selectAddItem(Model model) {
		List<AddItem> addItemList = addItemService.selectAllAddItem();
		model.addAttribute("addItemList", addItemList);
		return "additem/additem";
	}

	//검색
	@RequestMapping(value="/search/additem", method = RequestMethod.GET)
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

	//Insert, Delete, Update 저장
	@RequestMapping(value="/save/additem", method = RequestMethod.POST)
	@ResponseBody
	public List<AddItem> saveAll(@RequestBody(required = false) AddItem requestData){
		List<AddItem> addItemList = new ArrayList<>(); 

		try {
			List<AddItem> insertAddItems = requestData.getInsertAddItems();
			List<Integer> deletedAddItems = requestData.getDeletedAddItems();
			List<AddItem> updateAddItems = requestData.getUpdateAddItems();
			
			//Insert
			if(insertAddItems != null && !insertAddItems.isEmpty()) {
				for (AddItem addItem : insertAddItems) {
					addItemService.insertAddItem(addItem);
				}
			}
			
			//Delete
			if(deletedAddItems != null) {
				for (int addItemSn : deletedAddItems) {
					addItemService.deleteAddItemByUseYN(addItemSn);
				}
			}
			
			//Update
			if(updateAddItems != null && !updateAddItems.isEmpty()) {
				addItemService.updateAddItemDesc(updateAddItems);
			}

			addItemList = addItemService.selectAllAddItem();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return addItemList;
	}
	
	//엑셀다운로드
	@GetMapping("/excel/download")
    public void excelDownload(HttpServletResponse response) throws IOException {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet1");
		Row row = null;
		Cell cell = null;
		int rowNum = 0;

		// Header
		row = sheet.createRow(rowNum++);

		// 스타일 생성
		XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle(); // XSSFCellStyle 사용
		XSSFFont font = (XSSFFont) wb.createFont(); // XSSFFont 사용
		font.setBold(true); // 글씨 굵게
		style.setFont(font);

		// RGB 색상 정의 (예: 파스텔 톤의 하늘색, RGB 값: 173, 216, 230)
		style.setFillForegroundColor(new XSSFColor(new java.awt.Color(189, 215, 238), new DefaultIndexedColorMap())); // DefaultIndexedColorMap 사용
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// 부가항목명
		cell = row.createCell(0);
		cell.setCellValue("부가항목명");
		cell.setCellStyle(style); // 스타일 적용

		// 부가항목설명
		cell = row.createCell(1);
		cell.setCellValue("부가항목설명");
		cell.setCellStyle(style); // 스타일 적용

		// 컨텐츠 타입과 파일명 지정
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;filename=addItemList.xlsx"); // 파일명 설정

		// Excel File Output
		wb.write(response.getOutputStream());
		wb.close();
	
	}
}

