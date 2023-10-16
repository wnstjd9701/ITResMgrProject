package kr.co.kcc.itmgr.domain.ipinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;

@Mapper
@Repository
public interface IIpInfoRepository {
	List<IpInfo> selectIpInfoByPage(int start, int end); // IP 정보 조회 
	int selectIpCount(); // IP 개수 조회
}
