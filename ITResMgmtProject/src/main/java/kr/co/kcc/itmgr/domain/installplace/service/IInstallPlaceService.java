package kr.co.kcc.itmgr.domain.installplace.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;

public interface IInstallPlaceService {
	List<InstallPlace> selectAllAddress(); // 모든 설치 장소 조회
	
	List<InstallPlace> selectInstallPlaceByName(String placeName); // 설치 장소 검색
}
