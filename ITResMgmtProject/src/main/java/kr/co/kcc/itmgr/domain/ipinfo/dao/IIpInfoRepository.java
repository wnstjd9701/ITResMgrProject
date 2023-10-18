package kr.co.kcc.itmgr.domain.ipinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.ipinfo.model.IpCode;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpRes;

@Mapper
@Repository
public interface IIpInfoRepository {
	List<IpInfo> selectIpInfoByPage(int start, int end); // IP 정보 조회 
	List<IpCode> selectIpCode(); // IP 유형 조회
	IpInfo selectIpDetail(int ipSn); // IP 상세 정보 조회
	List<IpRes> selectResInfoByIpSn(int start, int end, int ipSn); // IP 매핑된 자원 조회
	
	int selectIpCount(); // IP 개수 조회
	
	int selectIpCountByKeyword(String keyword); // IP 검색 결과 개수 조회
	List<IpInfo> searchIp(String keyword, int start, int end); // IP 검색 조회
}
