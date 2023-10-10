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
	public List<InstallRes> selectAllResInfo() {
		return installPlaceRepository.selectAllResInfo();
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
	public List<InstallRes> selectResInformationByInstallPlaceName(String placeName) {
		return installPlaceRepository.selectResInformationByInstallPlaceName(placeName);
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
	public List<InstallPlace> selectPlaceListByPlaceName(List<String> placeNames) {
		return installPlaceRepository.selectPlaceListByPlaceName(placeNames);
	}
	
	// 해당 지역 자원 정보 조회
	@Override
	public List<InstallRes> selectResInformationByCity(List<String> placeNames) {
		return installPlaceRepository.selectResInformationByCity(placeNames);
	}
	
	// 지역별 설치 장소 조회
	@Override
	public List<InstallPlace> selectPlaceByCity(String firstDoName, String secondDoName) {
		return installPlaceRepository.selectPlaceByCity(firstDoName, secondDoName);
	}

	// 지역별 자원 정보 조회
	@Override
	public List<InstallRes> selectResInfoByCity(String firstDoName, String secondDoName) {
		return installPlaceRepository.selectResInfoByCity(firstDoName, secondDoName);
	}

}
