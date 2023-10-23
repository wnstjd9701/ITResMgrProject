package kr.co.kcc.itmgr.domain.ipinfo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpApiResponse;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpCode;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpRes;
import kr.co.kcc.itmgr.domain.ipinfo.service.IIpInfoService;
import kr.co.kcc.itmgr.global.common.ApiResponse;
import kr.co.kcc.itmgr.global.common.ApiResponseStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "IP 관리", description = "IP 관리 API Document")
@Controller
@AllArgsConstructor
@Slf4j
public class IpInfoController {

	private final IIpInfoService ipService;
	private static ApiResponseStatus status;
	/*
	 * @Author: [윤준성]
	 * @API No.5-1. IP 정보 페이지
	 * @Info: IP 정보 조회 
	 */
	@GetMapping("/ip")
	public String selectIpInfo(Model model, HttpSession session) {
		int start = 1;
		int end = start + 9;
		List<IpInfo> ipInfo = ipService.selectIpInfoByPage(start, end);
		int totalCount = ipService.selectIpCount();
		Map<String, Object> ipPaging = ipService.ipInfoPaging(start, totalCount);

		List<IpCode> ipCode = ipService.selectIpCode();

		log.info("IpInfo: " + ipInfo);
		log.info("IPCODE: " + ipCode);
		model.addAttribute("ipInfo", ipInfo);
		model.addAttribute("paging",ipPaging);
		model.addAttribute("ipCode", ipCode);

		return "/ip/ipinfo";
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-2. IP 정보 페이지 클릭
	 * @Info: IP 정보 조회 페이징  
	 */
	@GetMapping("/ip/{page}")
	@ResponseBody
	public ResponseEntity<IpApiResponse> selecIpInfoByPage(@PathVariable("page") int page,
			@RequestParam("searchType") String searchType){
		log.info("searchType: " + searchType);

		int start = ((page - 1) * 10 ) + 1;
		int end = start + 9;

		List<IpInfo> ipInfo;
		int totalCount;
		Map<String,Object> ipPaging;

		if(searchType.equals("ALL")) {
			ipInfo = ipService.selectIpInfoByPage(start, end);
			totalCount = ipService.selectIpCount();
			ipPaging = ipService.ipInfoPaging(start, totalCount);
		}else {
			ipInfo = ipService.searchIp(searchType, start, end);
			totalCount = ipService.selectIpCountByKeyword(searchType);
			ipPaging = ipService.ipInfoPaging(start, totalCount);
		}

		IpApiResponse response = new IpApiResponse();
		if(ipInfo.size() < 1 || totalCount < 1) {
			response.setCode(400);
			response.setStatus("error");
			response.setMessage("페이징 오류");
			return ResponseEntity.ok().body(response);
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ipInfo", ipInfo);
		data.put("ipPaging", ipPaging);

		response.setCode(200);
		response.setStatus("success");
		response.setMessage("IP 리스트 조회 성공");
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-7. 자원 정보 페이징
	 * @Info: 자원 정보 페이징
	 */
	@GetMapping("/ip/resinfo/{page}")
	@ResponseBody
	public ResponseEntity<IpApiResponse> selectResInfoByPage(@PathVariable("page") int page,
			@RequestParam("ipSn") int ipSn){
		int start = (page - 1) * 5 + 1;
		int end = start + 4;

		List<IpRes> resInfo = ipService.selectResInfoByIpSn(start, end, ipSn);
		int totalCount = ipService.selectResInfoCountByIpSn(ipSn);
		Map<String,Object> resPaging = ipService.resInfoPaging(page, totalCount);

		Map<String,Object> data = new HashMap<String, Object>();
		data.put("resInfo", resInfo);
		data.put("resPaging", resPaging);

		IpApiResponse response = new IpApiResponse();

		response.setCode(200);
		response.setStatus("success");
		response.setMessage("자원 조회 페이징 성공");
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-3. IP 상세 및 해당 자원 조회
	 * @Info: IP 상세 및 해당 자원 조회
	 */
	@GetMapping("/ip/detail/{ipsn}")
	@ResponseBody
	public ResponseEntity<IpApiResponse> selectIpAndResInfo(@PathVariable("ipsn") int ipSn){
		// 자원 조회 
		// 상세 Ip 정보 조회
		int start = 1;
		int end = start + 4;

		IpInfo ipInfo = ipService.selectIpDetail(ipSn);
		int totalCount = ipService.selectResInfoCountByIpSn(ipSn);
		List<IpRes> resInfo = ipService.selectResInfoByIpSn(start, end, ipSn);
		Map<String,Object> resPaging = ipService.ipInfoPaging(start, totalCount);

		IpApiResponse response = new IpApiResponse();

		log.info("ipInfo: " + ipInfo);
		log.info("resInfo: " + resInfo);

		if(ipInfo == null) {
			response.setCode(400);
			response.setStatus("error");
			response.setMessage("IP 상세 오류");
			return ResponseEntity.ok().body(response);
		}

		Map<String,Object> data = new HashMap<String, Object>();
		if(resInfo.size() == 0) {
			data.put("ipInfo", ipInfo);

			response.setCode(5001);
			response.setStatus("RES_INFO_NO_MAPPING");
			response.setMessage("매핑된 자원이 없습니다.");
			response.setData(data);
			return ResponseEntity.ok().body(response);
		}
		data.put("ipInfo", ipInfo);
		data.put("resInfo", resInfo);
		data.put("resPaging", resPaging);

		response.setCode(200);
		response.setStatus("success");
		response.setMessage("IP상세 조회 성공");
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-4. IP 검색
	 * @Info: IP 주소 검색
	 */
	@GetMapping("/ip/search")
	@ResponseBody
	public ResponseEntity<IpApiResponse> searchIp(@RequestParam("keyword") String keyword){
		int start = 1;
		int end = start + 9;

		int totalCount = ipService.selectIpCountByKeyword(keyword);
		List<IpInfo> ipInfo = ipService.searchIp(keyword, start, end);
		Map<String,Object> ipPaging = ipService.ipInfoPaging(start, totalCount);

		IpApiResponse response = new IpApiResponse();
		Map<String,Object> data = new HashMap<String, Object>();

		if(ipInfo.size() < 1) {
			data.put("ipPaging", ipPaging);

			response.setCode(4001);
			response.setStatus("RESULT_NOT_FOUND");
			response.setMessage("검색 결과가 없습니다.");
			response.setData(data);

			return ResponseEntity.ok().body(response);
		}

		log.info("IpInfo: " + ipInfo);
		log.info("totalCount: " + totalCount);

		data.put("ipInfo", ipInfo);
		data.put("ipPaging", ipPaging);

		response.setCode(200);
		response.setStatus("success");
		response.setMessage("검색 성공");
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-5. IP 삭제
	 * @Info: IP 선택 삭제
	 */
	@Operation(summary = "IP 삭제", description = "IP 삭제")
	@DeleteMapping("/ip/{ipSn}")
	@ResponseBody
	public ResponseEntity<IpApiResponse> deleteIp(@Parameter @PathVariable("ipSn") int ipSn){
		log.info("deleteIpSn: " + ipSn);
		boolean result = ipService.deleteIp(ipSn);

		IpApiResponse response = new IpApiResponse();
		Map<String,Object> data = new HashMap<String, Object>();

		int start = 1;
		int end = start + 9;

		int totalCount = ipService.selectIpCount();
		List<IpInfo> ipInfo = ipService.selectIpInfoByPage(start, end);
		Map<String, Object> ipPaging = ipService.ipInfoPaging(start, totalCount);

		data.put("ipInfo", ipInfo);
		data.put("ipPaging", ipPaging);

		response.setCode(200);
		response.setStatus("success");
		response.setMessage("삭제 성공");
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-6. IP 저장
	 * @Info: IP 한 개 저장 
	 */
	@Operation(summary = "IP 수정 / 신규", description = "IP 신규 등록 / 수정")
	@PostMapping("/ip")
	@ResponseBody
	public ResponseEntity<IpApiResponse> saveIp(@RequestBody @Valid IpInfo ipInfo, BindingResult bindingResult){
		log.info("Ip:" + ipInfo);
		IpApiResponse response = new IpApiResponse();
		Map<String,Object> data = new HashMap<String, Object>();

		if(bindingResult.hasErrors() || bindingResult.getErrorCount() != 0) {
			List<FieldError> list = bindingResult.getFieldErrors();
			for(FieldError error : list) {
				log.info("ERROR:" +error.getDefaultMessage());
				response.setMessage(error.getDefaultMessage());
				break;
			}
			response.setCode(5006);
			response.setStatus("NOT_VALID_VALUE");
			return ResponseEntity.ok().body(response);
		}

		int count = 0;
		int ipCheck;
		// 신규 저장
		if(ipInfo.getIpSn() == 0) {
			ipCheck = ipService.ipIsExists(ipInfo.getIp());
			if(ipCheck > 0) {
				response.setCode(5007);
				response.setStatus("ALREADY_IP_EXISTS");
				response.setMessage("이미 존재하는 IP 입니다.");
				return ResponseEntity.ok().body(response);
			}
			log.info("New Ip: " + ipInfo);
			count = ipService.insertIp(ipInfo);
			int newIpSn = ipInfo.getIpSn();

			response.setCode(200);
			response.setStatus("success");
			response.setMessage("IP 저장 성공");
			data.put("ipSn", newIpSn);
		}else { // 수정
			int updateIpCheck = ipService.updateIpIsExists(ipInfo.getIpSn(), ipInfo.getIp());
			if(updateIpCheck > 0) {
				// IP가 기존의 값과 동일, 다른값만 변경이므로 Check할 필요 x
				ipService.updateIp(ipInfo);
				response.setCode(200);
				response.setStatus("success");
				response.setMessage("IP 수정 성공");
			}else { // 기존의 IP가 수정된 경우
				ipCheck = ipService.ipIsExists(ipInfo.getIp());
				if(ipCheck > 0) {
					// IP 중복
					response.setCode(5007);
					response.setStatus("ALREADY_IP_EXISTS");
					response.setMessage("이미 존재하는 IP 입니다.");
					return ResponseEntity.ok().body(response);
				}else {
					// UPDATE
					ipService.updateIp(ipInfo);
					response.setCode(200);
					response.setStatus("success");
					response.setMessage("IP 수정 성공");
				}
			}
		}

		int start = 1;
		int end = start + 9;
		List<IpInfo> ipInfoResult = ipService.selectIpInfoByPage(start, end);
		int totalCount = ipService.selectIpCount();
		Map<String, Object> ipPaging = ipService.ipInfoPaging(start, totalCount);

		data.put("ipInfo", ipInfoResult);
		data.put("ipPaging", ipPaging);
		response.setData(data);

		return ResponseEntity.ok().body(response);
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-8. 엑셀 다운로드
	 * @Info: 엑셀 다운로드 
	 */
	@GetMapping("/ip/excel/download")
	public void excelDownload(HttpServletResponse response) throws IOException {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet1");
		Row row = null;
		Cell cell = null;
		int rowNum = 0;

		// Freeze the top row (0행을 고정)
		sheet.createFreezePane(0, 1);
		// Header
		row = sheet.createRow(rowNum++);

		// 스타일 생성
		XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
		XSSFFont font = (XSSFFont) wb.createFont();
		font.setBold(true);
		style.setFont(font);
		style.setFillForegroundColor(new XSSFColor(new java.awt.Color(189, 215, 238), new DefaultIndexedColorMap()));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell = row.createCell(0);
		cell.setCellValue("IP 주소");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("IP 유형");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("IP 설명");
		cell.setCellStyle(style);

		List<IpCode> ipTypeList = ipService.selectIpCode();
		String[] ipTypes = new String[ipTypeList.size()];

		for (int i = 0; i < ipTypeList.size(); i++) {
			ipTypes[i] = ipTypeList.get(i).getDetailCodeName();
		}

		// 데이터 유효성 검사를 위한 셀 범위 생성
		CellRangeAddressList addressList = new CellRangeAddressList(1, 10000, 1, 1); // 1행부터 마지막 행까지, 2열에 적용

		// 드롭다운 목록을 위한 제약 조건 생성
		DataValidationHelper dvHelper = sheet.getDataValidationHelper();
		DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(ipTypes);

		// 데이터 유효성 검사 생성 및 셀 범위에 적용
		DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
		sheet.addValidationData(validation);

		// 컨텐츠 타입과 파일명 지정
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;filename=IP.xlsx");

		// Excel 파일 출력
		wb.write(response.getOutputStream());
		wb.close();
	}

	/*
	 * @Author: [윤준성]
	 * @API No.5-9. 엑셀 업로드
	 * @Info: 엑셀 업로드 
	 */
	@Operation(summary = "엑셀 업로드", description = "엑셀 파일로 Ip 등록 Api")
	@PostMapping("/ip/excel/upload")
	@ResponseBody
	public ResponseEntity<ApiResponse<?>> ipExcelUpload(@RequestParam("file") MultipartFile file) throws Exception{
		log.info("MultiFile: " + file);
		// 업로드된 엑셀 파일을 읽기 위해 MultipartFile.getInputStream()을 사용
		InputStream inputStream = file.getInputStream();

		// 엑셀 파일을 읽기 위해 Workbook을 생성
		Workbook workbook = new XSSFWorkbook(inputStream);

		// 엑셀 파일의 첫 번째 시트를 가져오기
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		log.info("rowCount: " + rowCount);
		log.info("sheet: " + sheet);
		// 엑셀 파일에서 데이터 읽기
		int rowIndex = 0; // 행 인덱스 초기화
		List<IpInfo> ipInfoList = new ArrayList<IpInfo>();
		List<String> ipList = ipService.selectIpSet();

		Set<String> ipDuplication = new HashSet<>(ipList); 
		log.info("IPDUPL "+ipDuplication);
		if(rowCount > 1) {

			// 엑셀 파일에서 데이터를 읽어오기
			for (Row row : sheet) {
				if (rowIndex == 0) {
					rowIndex++;
					continue;
				}
				// IP 주소, 유형, 설명
				Cell ipCell = row.getCell(0);
				Cell ipTypeCodeCell = row.getCell(1);
				Cell ipDescCell = row.getCell(2);

				String ip = null;
				String ipTypeCodeName = null;
				String ipDesc = null;
				List<IpCode> ipTypeList = ipService.selectIpCode();

				if(ipCell != null) {
					if (ipCell.getCellType() == CellType.STRING) {
						ip = ipCell.getStringCellValue();
						if (ipDuplication.contains(ip)) {
							log.info("중복된 IP");
							return ResponseEntity.status(status.SUCCESS.getStatus()).body(ApiResponse.fail(ApiResponseStatus.EXCEL_DUPLICATE_DATA.getCode(),ApiResponseStatus.EXCEL_DUPLICATE_DATA.getMessage()));
						} 
					} else {
						log.info("IP 문자열만 가능");
						return ResponseEntity.status(status.SUCCESS.getStatus()).body(ApiResponse.fail(ApiResponseStatus.EXCEL_INCORRECT_IP.getCode(),ApiResponseStatus.EXCEL_INCORRECT_IP.getMessage()));
					}
				}
				if(ipTypeCodeCell != null){
					if (ipTypeCodeCell.getCellType() == CellType.STRING) {
						boolean validIpTypeCode = false;
						ipTypeCodeName = ipTypeCodeCell.getStringCellValue();


						for (IpCode ipCode : ipTypeList) {
							log.info("ipCode: " + ipCode);
							log.info("ipTypeCodeName: " + ipTypeCodeName);
							if (ipCode.getDetailCodeName().equals(ipTypeCodeName)) {
								validIpTypeCode = true;
								break;
							}
						}
						if(!validIpTypeCode) {
							log.info("잘못된 IP유형");
							return ResponseEntity.status(status.SUCCESS.getStatus()).body(ApiResponse.fail(ApiResponseStatus.EXCEL_INCORRECT_IPCODE.getCode(),ApiResponseStatus.EXCEL_INCORRECT_IPCODE.getMessage()));
						}
					} else {
						log.info("IP유형 문자열만 가능");
						return ResponseEntity.status(status.SUCCESS.getStatus()).body(ApiResponse.fail(ApiResponseStatus.EXCEL_INCORRECT_IPCODE.getCode(),ApiResponseStatus.EXCEL_INCORRECT_IPCODE.getMessage()));
					}
				}
				if(ipDescCell != null) {
					if (ipDescCell.getCellType() == CellType.STRING) {
						ipDesc = ipDescCell.getStringCellValue();
					} else {
						ipDesc = "";
					}
				}
				log.info("IP: " + ip + "/" + "ipTypeCode"+ ipTypeCodeName + "ipDesc" + ipDesc);

				IpInfo ipInfo = new IpInfo();
				ipInfo.setIp(ip);
				for(IpCode ipType: ipTypeList) {
					String ipCodeName = ipType.getDetailCodeName();
					String ipCode = ipType.getDetailCode();
					if(ipCodeName.equals(ipTypeCodeName)) {
						ipInfo.setDetailCode(ipCode);
						break;
					}
				}
				ipInfo.setIpDesc(ipDesc);

				ipInfoList.add(ipInfo);
				rowIndex++;
			}
			workbook.close();
			inputStream.close();
			
			// DB 저장 로직
			log.info("IpInfoList: " + ipInfoList);
			int count = ipService.insertIpList(ipInfoList);
			int start = 1;
			int end = start + 9;
			List<IpInfo> ipInfo = ipService.selectIpInfoByPage(start, end);
			int totalCount = ipService.selectIpCount();
			Map<String, Object> ipPaging = ipService.ipInfoPaging(start, totalCount);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("ipInfo", ipInfo);
			data.put("ipPaging", ipPaging);
			
			return ResponseEntity.status(ApiResponseStatus.SUCCESS.getStatus()).body(ApiResponse.success(data));
		}
		return ResponseEntity.status(ApiResponseStatus.SUCCESS.getStatus()).body(ApiResponse.fail(ApiResponseStatus.EXCEL_EMPTY_DATA.getCode(),ApiResponseStatus.EXCEL_EMPTY_DATA.getMessage()));
	}

}
