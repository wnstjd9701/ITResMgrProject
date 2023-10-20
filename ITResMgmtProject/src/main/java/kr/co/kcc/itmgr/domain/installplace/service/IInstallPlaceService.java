package kr.co.kcc.itmgr.domain.installplace.service;

import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;

public interface IInstallPlaceService {
	Map<String, Object> placePaging(int page, int totalCount); // 페이징
	
	List<InstallPlace> selectAllPlace(int start); // 모든 설치 장소 조회[페이징 수정]
	int selectInstallPlaceCount(); // 설치 장소 개수 조회
	
	List<InstallRes> selectAllResInfo(int start, int end); // 모든 자원 정보 조회[페이징]
	List<InstallRes> selectResInformationByInstallPlaceName(String placeName, int start, int end); // 해당 설치 장소 자원 정보 조회
	
	int selectInstallPlaceSearchCount(String keyword); // 설치 장소 개수 조회
	List<InstallPlace> searchInstallPlaceByName(String keyword, int start, int end); // 설치 장소 검색
	InstallPlace selectInstallPlaceDetail(String placeName); // 설치 장소 상세 정보 조회
	
	int checkPlaceNameBySn(String placeName, int placesn); // 설치 장소명 중복 체크
	int checkPlaceNameByName(String placeName); // 설치 장소 신규 등록 시 장소명 중복 체크
	InstallPlace selectInstallPlaceBySn(int placesn); // 기존 설치 장소 이름 체크
	
	int insertInstallPlace(InstallPlace installPlace); // 설치 장소 등록 
	int updateInstallPlace(InstallPlace installPlace); // 설치 장소 수정
	boolean deleteInstallPlace(int placesn); // 설치 장소 삭제
	
	// placeMap
	int selectPlaceCountByCity(String firstDoName, String secondDoName); // 모든 해당 지역 장소 개수
	int selectPlaceCountByPlaceName(List<String> placeNames); // 이름으로 지역 장소 개수 조회 
	
	int selectResCount(); // 모든 설치 장소에 대한 자원 개수 조회
	int selectResCountByCity(List<String> placeNames); // 이름으로 지역 자원 개수 조회
	
	int selectResInfoCountByPlaceName(String place); // 이름으로 특정 장소 자원 개수 조회
	int selectResInfoCountByCity(String firstDoName, String secondDoName); // 해당 지역 자원 개수 조회
	
	List<InstallPlace> selectPlaceListByPlaceName(List<String> placeNames, int start, int end); // 해당 지역 설치 장소 조회
	List<InstallPlace> selectPlaceByCity(String firstDoName, String secondDoName, int start, int end); // 해당 지역 설치 장소 조회
	
	List<InstallRes> selectResInformationByCity(List<String> placeNames, int start, int end); // 해당 지역 자원 정보 조회
	List<InstallRes> selectResInfoByCity(String firstDoName, String secondDoName, int start, int end); // 해당 도시 자원 정보 조회
	
}
