package kr.co.kcc.itmgr.domain.home.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Monitoring> selectAllResourceInformation(int start, int end) {
		return monitoringRepository.selectAllResourceInformation(start, end);
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
	
	// 서버 핑 체크
	@Override
	public Boolean serverPingCheck(String ip) {
		InetAddress pingCheck;
		boolean isAlive = false;
		try {
			pingCheck = InetAddress.getByName(ip);
			isAlive = pingCheck.isReachable(1000); // Timeout 조절 가능
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isAlive;
	}

	// 페이징
	@Override
	public Map<String, Object> monitoringPaging(int page, int totalCount) {
		int totalPage = 0;
		if(totalCount > 0) {
			totalPage=(int)Math.ceil(totalCount / 10.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage / 10.0));
		int nowPageBlock = (int)Math.ceil(page / 10.0);
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage=0;
		if(totalPage > nowPageBlock * 10) {
			endPage = nowPageBlock * 10;
		}else {
			endPage = totalPage;
		}

		Map<String,Object> paging = new HashMap<String, Object>();
		paging.put("totalPageCount", totalPage);
		paging.put("nowPage", page);
		paging.put("totalPageBlock", totalPageBlock);
		paging.put("nowPageCount", nowPageBlock);
		paging.put("startPage", startPage);
		paging.put("endPage", endPage);
		return paging;
	}

	@Override
	public int selectResCount() {
		return monitoringRepository.selectResCount();
	}

	@Override
	public int selectResCountBySearch(SearchCondition searchCondition) {
		return monitoringRepository.selectResCountBySearch(searchCondition);
	}

}
