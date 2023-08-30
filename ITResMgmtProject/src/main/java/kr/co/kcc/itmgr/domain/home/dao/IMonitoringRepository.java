package kr.co.kcc.itmgr.domain.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;

@Repository
@Mapper
public interface IMonitoringRepository {
	List<Monitoring> getAllResourceInformation(); 
}
