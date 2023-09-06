package kr.co.kcc.itmgr.domain.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;

@Repository
@Mapper
public interface IMonitoringRepository {
	/*
	 * Monitoring
	 */
	List<Monitoring> getAllResourceInformation();  // 모니터링 모든 자원 정보 가져오기
	List<ResClass> getResClassInformationByLevel(int level); // 하드웨어 레벨 별로 자원분류 가져오기
	
}
