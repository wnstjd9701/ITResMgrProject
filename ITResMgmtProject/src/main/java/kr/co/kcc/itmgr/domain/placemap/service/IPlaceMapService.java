package kr.co.kcc.itmgr.domain.placemap.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.global.common.DoName;

public interface IPlaceMapService {
	List<InstallPlace> selectInstallPlaceList(); // 모든 설치 장소 조회
	
	InstallPlace getDoName(InstallPlace place); // 도 이름, 좌표 가져오기
	List<String> getDoValues(); // 도 이름 조회
	DoName getDoValuesByDoName(String doName); // 도 이름 조회
}
