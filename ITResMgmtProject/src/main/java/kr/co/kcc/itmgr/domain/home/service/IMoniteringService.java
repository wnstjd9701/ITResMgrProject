package kr.co.kcc.itmgr.domain.home.service;

import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.home.model.Monitoring;
import kr.co.kcc.itmgr.domain.home.model.SearchCondition;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;

public interface IMoniteringService {
	List<Monitoring> selectAllResourceInformation(int start, int end); // 모니터링 모든 자원 정보 가져오기
	List<ResClass> selectResClassInformationByLevel(int level); // 하드웨어 레벨 별로 자원분류 가져오기
	
	List<Monitoring> selectResInformationBySearchCondition(SearchCondition searchCondition); // 자원 검색 결과 조회
	int selectResCountBySearch(SearchCondition searchCondition); // 자원 검색 결과 개수 조회
	
	Boolean serverPingCheck(String ip); // 서버 핑 체크
	Map<String, Object> monitoringPaging(int page, int totalCount); // 페이징 처리
	int selectResCount(); // 자원 전체 개수 조회
}
