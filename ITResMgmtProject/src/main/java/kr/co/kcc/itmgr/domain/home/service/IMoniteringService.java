package kr.co.kcc.itmgr.domain.home.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;

public interface IMoniteringService {
	List<Monitoring> getAllResourceInformation(); // 모니터링 모든 자원 정보 가져오기
	List<ResClass> getResClassInformationByLevel(int level); // 하드웨어 레벨 별로 자원분류 가져오기
	
}
