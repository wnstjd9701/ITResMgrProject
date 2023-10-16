package kr.co.kcc.itmgr.domain.ipinfo.service;

import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;

public interface IIpInfoService {
	Map<String,Object> ipInfoPaging(int page, int totalCount); // 페이징 처리
	int selectIpCount(); // IP 개수 조회
	
	List<IpInfo> selectIpInfoByPage(int start, int end); // IP 정보 조회
	
}
