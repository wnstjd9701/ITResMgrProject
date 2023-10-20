package kr.co.kcc.itmgr.domain.installplace.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;

@Repository
@Mapper
public interface IInstallPlaceRepository {
	List<InstallPlace> selectInstallPlaceList(); // 모든 설치 장소 조회[지도]
	
	List<InstallPlace> selectAllPlace(int start, int end); // 모든 설치 장소 조회
	int selectInstallPlaceCount(); // 설치 장소 개수 조회
	
	List<InstallRes> selectAllResInfo(int start, int end); // 모든 자원 정보 조회
	
	int selectInstallPlaceSearchCount(String keyword); // 설치 장소 개수 조회
	List<InstallPlace> selectInstallPlaceByName(String keyword, int start, int end); // 설치 장소 검색
	
	InstallPlace selectInstallPlaceDetail(String placeName); // 설치 장소 상세 정보 조회
	List<InstallRes> selectResInformationByInstallPlaceName(String placeName, int start, int end); // 자원 정보 조회
	
	int checkPlaceNameBySn(String placeName, int placesn); // 설치 장소명 중복 체크
	int checkPlaceNameByName(String placeName); // 설치 장소 신규 등록 시 장소명 중복 체크
	InstallPlace selectInstallPlaceBySn(int placesn); // 기존 설치 장소 이름 체크
	
	int insertInstallPlace(InstallPlace installPlace); // 설치 장소 등록 
	int updateInstallPlace(InstallPlace installPlace); // 설치 장소 수정
	boolean deleteInstallPlace(int placesn); // 설치 장소 삭제
	
	//-----------------------------------------------------------------------------------------------------------------------
	int selectPlaceCountByCity(String firstDoName, String secondDoName); // 해당 지역 설치 장소 개수 조회
	int selectPlaceCountByPlaceName(List<String> placeNames); // 이름으로 해당 지역 장소 개수 조회
	
	int selectResCount(); // 설치 장소에 대한 모든 자원 조회
	int selectResCountByCity(List<String> placeNames); // 이름으로 해당 지역 자원 개수 조회
	
	int selectResInfoCountByPlaceName(String placeName); // 이름으로 특정 장소 자원 개수 조회
	int selectResInfoCountByCity(String firstDoName, String secondDoName); // 해당 지역 자원 개수
	
	List<InstallPlace> selectPlaceListByPlaceName(List<String> placeNames, int start, int end); // 해당 지역 설치 장소 조회 
	List<InstallRes> selectResInformationByCity(List<String> placeNames, int start, int end); // 해당 지역 자원 정보 조회
	
	List<InstallPlace> selectPlaceByCity(String firstDoName, String secondDoName, int start, int end); // 해당 도시 설치 장소 조회
	List<InstallRes> selectResInfoByCity(String firstDoName, String secondDoName, int start, int end); // 해당 도시 자원 정보 조회
}
