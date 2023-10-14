package kr.co.kcc.itmgr.domain.installplace.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.installplace.dao.IInstallPlaceRepository;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstallPlaceService implements IInstallPlaceService {
	
	private final IInstallPlaceRepository installPlaceRepository;

	// 모든 설치 장소 조회
	@Override
	public List<InstallPlace> selectAllPlace(int page) {
		int start = page;
		int end = start + 4;
		return installPlaceRepository.selectAllPlace(start, end);
	}
	
	// 설치 장소 개수 조회
	@Override
	public int selectInstallPlaceCount() {
		return installPlaceRepository.selectInstallPlaceCount();
	}
	
	// 모든 자원 정보 조회
	@Override
	public List<InstallRes> selectAllResInfo(int start, int end) {
		return installPlaceRepository.selectAllResInfo(start, end);
	}

	// 설치 장소 개수 조회
	@Override
	public int selectInstallPlaceSearchCount(String keyword) {
		return installPlaceRepository.selectInstallPlaceSearchCount(keyword);
	}
	
	// 설치 장소 검색
	@Override
	public List<InstallPlace> searchInstallPlaceByName(String keyword, int start, int end) {
		log.info("start: " + start + " end: " + end);
		return installPlaceRepository.selectInstallPlaceByName(keyword, start, end);
	}
	
	// 자원 정보 조회
	@Override
	public List<InstallRes> selectResInformationByInstallPlaceName(String placeName, int start, int end) {
		return installPlaceRepository.selectResInformationByInstallPlaceName(placeName, start, end);
	}

	// 설치 장소 상세 정보 조회
	@Override
	public InstallPlace selectInstallPlaceDetail(String placeName) {
		return installPlaceRepository.selectInstallPlaceDetail(placeName);
	}

	// 설치 장소명 중복 체크
	@Override
	public int checkPlaceNameBySn(String placeName, int placesn) {
		return installPlaceRepository.checkPlaceNameBySn(placeName, placesn);
	}
	
	// 설치 장소 신규 등록 시 장소명 중복 체크
	@Override
	public int checkPlaceNameByName(String placeName) {
		return installPlaceRepository.checkPlaceNameByName(placeName);
	}
	
	// 기존 설치 장소 이름 체크
	@Override
	public InstallPlace selectInstallPlaceBySn(int placesn) {
		return installPlaceRepository.selectInstallPlaceBySn(placesn);
	}
	
	// 설치 장소 등록
	@Override
	public int insertInstallPlace(InstallPlace installPlace) {
		return installPlaceRepository.insertInstallPlace(installPlace);
	}

	// 설치 장소 수정
	@Override
	public int updateInstallPlace(InstallPlace installPlace) {
		return installPlaceRepository.updateInstallPlace(installPlace);
	}

	// 설치 장소 삭제 
	@Override
	public boolean deleteInstallPlace(int placesn) {
		return installPlaceRepository.deleteInstallPlace(placesn);
	}

	// 해당 지역 설치 장소 조회
	@Override
	public List<InstallPlace> selectPlaceListByPlaceName(List<String> placeNames, int start, int end) {
		return installPlaceRepository.selectPlaceListByPlaceName(placeNames, start, end);
	}
	
	// 해당 지역 자원 정보 조회
	@Override
	public List<InstallRes> selectResInformationByCity(List<String> placeNames, int start, int end) {
		return installPlaceRepository.selectResInformationByCity(placeNames, start, end);
	}
	
	// 지역별 설치 장소 조회
	@Override
	public List<InstallPlace> selectPlaceByCity(String firstDoName, String secondDoName, int start, int end) {
		return installPlaceRepository.selectPlaceByCity(firstDoName, secondDoName, start, start + 4);
	}

	// 지역별 자원 정보 조회
	@Override
	public List<InstallRes> selectResInfoByCity(String firstDoName, String secondDoName, int start, int end) {
		return installPlaceRepository.selectResInfoByCity(firstDoName, secondDoName, start, end);
	}
	
	// 모든 해당 지역 장소 개수 조회 
	@Override
	public int selectPlaceCountByCity(String firstDoName, String secondDoName) {
		return installPlaceRepository.selectPlaceCountByCity(firstDoName, secondDoName);
	}
	
	// 이름으로 해당 지역 장소 개수 조회
	@Override
	public int selectPlaceCountByPlaceName(List<String> placeNames) {
		return installPlaceRepository.selectPlaceCountByPlaceName(placeNames);
	}

	// 페이징 로직
	@Override
	public Map<String, Object> placePaging(int page, int totalCount) {
		int totalPage = 0;
		if(totalCount > 0) {
			totalPage=(int)Math.ceil(totalCount / 5.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage / 5.0));
		int nowPageBlock = (int)Math.ceil(page / 5.0);
		int startPage = (nowPageBlock - 1) * 5 + 1;
		int endPage=0;
		if(totalPage > nowPageBlock * 5) {
			endPage = nowPageBlock * 5;
		}else {
			endPage = totalPage;
		}
		
		Map<String,Object> paging = new HashMap<String, Object>();
		paging.put("totalPageCount", totalPage);
		paging.put("nowPage", page);
		paging.put("totalPageBlock", totalPageBlock);
		paging.put("nowPageCount", nowPageBlock);
		paging.put("startPage", startPage);
		paging.put("endPage", endPage);
		return paging;
	}
	
	// 이름으로 특정 장소 자원 개수 조회
	@Override
	public int selectResInfoCountByPlaceName(String place) {
		return installPlaceRepository.selectResInfoCountByPlaceName(place);
	}
	
	// 해당 지역 자원 개수
	@Override
	public int selectResInfoCountByCity(String firstDoName, String secondDoName) {
		return installPlaceRepository.selectResInfoCountByCity(firstDoName, secondDoName);
	}

	// 이름으로 해당 지역 자원 개수 조회
	public int selectResCountByCity(List<String> placeNames) {
		return installPlaceRepository.selectResCountByCity(placeNames);
	}

	@Override
	public int selectResCount() {
		return installPlaceRepository.selectResCount();
	}

}
