package kr.co.kcc.itmgr.domain.additem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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

	@GetMapping("/additem")
	public String selectAddItem(Model model) {
		List<AddItem> addItemList = addItemService.selectUseYAddItem();
		model.addAttribute("addItemList", addItemList);
		return "additem/additem";
	}

	//검색
	@GetMapping("/search/additem")
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
	@PostMapping("/save/additem")
	@ResponseBody
	public List<AddItem> saveAll(@RequestBody(required = false) AddItem requestData){
		List<AddItem> addItemList = new ArrayList<>(); 

		try {
			List<AddItem> insertAddItems = requestData.getInsertAddItems();
			List<Integer> deletedAddItems = requestData.getDeletedAddItems();
			List<AddItem> updateAddItems = requestData.getUpdateAddItems();

			//Insert
			if(insertAddItems != null && !insertAddItems.isEmpty()) {
				addItemService.insertAddItem(insertAddItems);
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
			addItemList = addItemService.selectUseYAddItem();
		}catch(Exception e){
			e.printStackTrace();
		}
		return addItemList;
	}

	//엑셀양식다운로드
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

	//엑셀업로드
	@PostMapping("/excel/upload")
	@ResponseBody
	public List<AddItem> excelUpload(@RequestParam("file") MultipartFile file) {
		List<AddItem> addItemList = new ArrayList<>(); 
		try {
			// 업로드된 엑셀 파일을 읽기 위해 MultipartFile.getInputStream()을 사용
			InputStream inputStream = file.getInputStream();

			// 엑셀 파일을 읽기 위해 Workbook을 생성
			Workbook workbook = new XSSFWorkbook(inputStream);

			// 엑셀 파일의 첫 번째 시트를 가져오기
			Sheet sheet = workbook.getSheetAt(0);

			// 엑셀 파일에서 데이터 읽기
			int rowIndex = 0; // 행 인덱스 초기화

			// 엑셀 파일에서 데이터를 읽어오기
			for (Row row : sheet) {
				if (rowIndex == 0) {
					// 첫 번째 행은 '부가항목명', '부가항목설명'이므로 두번째 행부터 읽어옴
					rowIndex++;
					continue;
				}
				// 각 행에서 셀 값 읽어오기
				String addItemName = row.getCell(0).getStringCellValue();
				String addItemDesc = row.getCell(1).getStringCellValue();
				// 데이터베이스에 저장하는 로직 추가
				addItemService.insertAddItemExcel(addItemName, addItemDesc);

				rowIndex++;
			}
			workbook.close();
			inputStream.close();

			addItemList = addItemService.selectAllAddItem();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addItemList;
	}


	//엑셀업로드 부가항목명 중복체크
	@PostMapping("/checkDuplicateAddItemNamesExcel")
	@ResponseBody
	public List<String> checkDuplicateAddItemNamesExcel(@RequestParam("file") MultipartFile file) {
		List<String> duplicateNamesExcel  = new ArrayList<>(); 
		List<String> addItemNamesExcels = new ArrayList<>(); //엑셀파일의 부가항목명

		try {
			// 업로드된 엑셀 파일을 읽기 위해 MultipartFile.getInputStream()을 사용
			InputStream inputStream = file.getInputStream();

			// 엑셀 파일을 읽기 위해 Workbook을 생성
			Workbook workbook = new XSSFWorkbook(inputStream);

			// 엑셀 파일의 첫 번째 시트를 가져오기
			Sheet sheet = workbook.getSheetAt(0);

			// 엑셀 파일에서 데이터 읽기
			int rowIndex = 0; // 행 인덱스 초기화

			// 엑셀 파일에서 데이터를 읽어오기
			for (Row row : sheet) {
				if (rowIndex == 0) {
					// 첫 번째 행은 '부가항목명', '부가항목설명'이므로 두번째 행부터 읽어옴
					rowIndex++;
					continue;
				}
				// 각 행에서 셀 값 읽어오기
				String addItemName = row.getCell(0).getStringCellValue();
				addItemNamesExcels.add(addItemName); //엑셀파일의 모든 부가정보명

				rowIndex++;
			}
			//기존 DB에 저장되어있는 부가항목명 가져오기
			List<String> existingAddItemNames = new ArrayList<>(); 

			List<AddItem> addItems = addItemService.selectAllAddItem();
			for(AddItem addItem : addItems) {
				existingAddItemNames.add(addItem.getAddItemName());
			}
			//중복된 부가항목명 찾기
			Set<String> duplicateNameSet = new HashSet<>();
			for (String addItemNameExcel : addItemNamesExcels) {
				if((existingAddItemNames.contains(addItemNameExcel) || duplicateNameSet.contains(addItemNameExcel)) 
						&& !duplicateNamesExcel.contains(addItemNameExcel)) {
					duplicateNamesExcel.add(addItemNameExcel);
				}
				duplicateNameSet.add(addItemNameExcel);
			}	
			workbook.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return duplicateNamesExcel;
	}

	//행추가시 부가항목명 중복검사
	@PostMapping("/checkDuplicateAddItemNames")
	@ResponseBody
	public List<String> checkDuplicateAddItemNames(@RequestBody List<String> addItemNames) {
		List<String> duplicateNames = new ArrayList<>();
		try {
			// 기존 DB에 저장된 부가항목명들을 가져옴
			List<String> existingAddItemNames = new ArrayList<>();

			List<AddItem> addItems = addItemService.selectAllAddItem();
			for(AddItem addItem : addItems) {
				existingAddItemNames.add(addItem.getAddItemName());
			}
			//중복된 부가항목명 찾기
			Set<String> duplicateNameSet = new HashSet<>();
			for(String addItemName : addItemNames) {
				if ((existingAddItemNames.contains(addItemName) || duplicateNameSet.contains(addItemName)) && !duplicateNames.contains(addItemName)) {
					duplicateNames.add(addItemName); // 중복된 부가항목명이면 판별되면 목록에 추가
				}
				duplicateNameSet.add(addItemName); // 중복을 방지하기 위해 추가한 중복된 부가항목명을 저장
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return duplicateNames;
	}
}

