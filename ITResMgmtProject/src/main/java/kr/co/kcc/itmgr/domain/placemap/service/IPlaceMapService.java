package kr.co.kcc.itmgr.domain.placemap.service;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;

public interface IPlaceMapService {
	InstallPlace getDoName(InstallPlace place); // 도 이름, 좌표 가져오기
}
