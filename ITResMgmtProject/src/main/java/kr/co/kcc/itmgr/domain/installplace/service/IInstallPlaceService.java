package kr.co.kcc.itmgr.domain.installplace.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;

public interface IInstallPlaceService {
	List<InstallPlace> selectAllAddress(); // 모든 설치 장소 조회
	
	List<InstallPlace> selectInstallPlaceByName(String placeName); // 설치 장소 검색
	
	List<InstallRes> selectResInformationByInstallPlaceName(String placeName); // 자원 정보 조회
}
