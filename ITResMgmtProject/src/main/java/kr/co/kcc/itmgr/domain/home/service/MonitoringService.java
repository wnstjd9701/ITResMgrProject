package kr.co.kcc.itmgr.domain.home.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.home.dao.IMonitoringRepository;
import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import kr.co.kcc.itmgr.domain.home.model.SearchCondition;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonitoringService implements IMoniteringService{
	
	private final IMonitoringRepository monitoringRepository;
	
	// 모니터링 모든 자원 정보 가져오기
	@Override
	public List<Monitoring> selectAllResourceInformation() {
		return monitoringRepository.selectAllResourceInformation();
	}

	// 하드웨어 레벨 별로 자원분류 가져오기
	@Override
	public List<ResClass> selectResClassInformationByLevel(int level) {
		return monitoringRepository.selectResClassInformationByLevel(level);
	}

	// 자원 검색 결과 가져오기
	@Override
	public List<Monitoring> selectResInformationBySearchCondition(SearchCondition searchCondition) {
		return monitoringRepository.selectResourceInformationBySearchCondition(searchCondition);
	}

}
