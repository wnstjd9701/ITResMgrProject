package kr.co.kcc.itmgr.domain.installplace.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;

public interface IInstallPlaceService {
	List<InstallPlace> selectAllPlace(); // 모든 설치 장소 조회
	List<InstallRes> selectAllResInfo(); // 모든 자원 정보 조회
	
	List<InstallPlace> searchInstallPlaceByName(String keyword); // 설치 장소 검색
	InstallPlace selectInstallPlaceDetail(String placeName); // 설치 장소 상세 정보 조회
	
	List<InstallRes> selectResInformationByInstallPlaceName(String placeName); // 자원 정보 조회
	
	int checkPlaceNameBySn(String placeName); // 설치 장소명 중복 체크
	int insertInstallPlace(InstallPlace installPlace); // 설치 장소 등록 
	int updateInstallPlace(InstallPlace installPlace); // 설치 장소 수정
	boolean deleteInstallPlace(int placesn); // 설치 장소 삭제
	
	// placeMap
	List<InstallPlace> selectPlaceListByPlaceName(List<String> placeNames); // 해당 지역 설치 장소 조회
	List<InstallRes> selectResInformationByCity(List<String> placeNames); // 해당 지역 자원 정보 조회
	
	List<InstallPlace> selectPlaceByCity(String firstDoName, String secondDoName); // 해당 지역 설치 장소 조회
	List<InstallRes> selectResInfoByCity(String firstDoName, String secondDoName); // 해당 도시 자원 정보 조회
}
