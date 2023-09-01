package kr.co.kcc.itmgr.domain.home.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;

public interface IMoniteringService {
	List<Monitoring> getAllResourceInformation(); // 모니터링 모든 자원 정보 가져오기
}
