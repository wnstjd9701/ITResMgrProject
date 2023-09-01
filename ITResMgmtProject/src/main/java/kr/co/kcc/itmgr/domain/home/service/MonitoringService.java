package kr.co.kcc.itmgr.domain.home.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.home.dao.IMonitoringRepository;
import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonitoringService implements IMoniteringService{
	
	private final IMonitoringRepository monitoringRepository;
	
	@Override
	public List<Monitoring> getAllResourceInformation() {
		return monitoringRepository.getAllResourceInformation();
	}

}
