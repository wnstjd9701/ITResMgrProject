package kr.co.kcc.itmgr.domain.ipinfo.service;

import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.ipinfo.model.IpCode;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpRes;

public interface IIpInfoService {
	Map<String, Object> ipInfoPaging(int page, int totalCount); // 페이징 처리
	Map<String, Object> resInfoPaging(int page, int totalCount); // 자원 페이징 처리
	
	List<IpInfo> selectIpInfoByPage(int start, int end); // IP 정보 조회
	List<IpCode> selectIpCode(); // IP 유형 조회
	int selectIpCount(); // IP 개수 조회
	
	IpInfo selectIpDetail(int ipSn); // IP 상세 정보 조회
	List<IpRes> selectResInfoByIpSn(int start, int end, int ipSn); // IP 매핑된 자원 조회
	
	int selectIpCountByKeyword(String keyword); // IP 검색 결과 개수 조회
	List<IpInfo> searchIp(String keyword, int start, int end); // IP 검색 조회
	
	int ipIsExists(String ip); // IP 존재여부 확인
	int updateIpIsExists(int ipSn, String ip); // 업데이트 시 IP 수정 여부 확인 
	
	int insertIp(IpInfo ipInfo); // IP 등록
	int updateIp(IpInfo ipInfo); // IP 수정
	boolean deleteIp(int ipSn); // IP 삭제
	
	int selectResInfoCountByIpSn(int ipSn); // 매핑된 자원 수 조회
}
