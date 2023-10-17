package kr.co.kcc.itmgr.domain.ipinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.ipinfo.dao.IIpInfoRepository;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpCode;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpRes;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IpInfoService implements IIpInfoService{

	private final IIpInfoRepository ipRepository;
	
	@Override
	public Map<String, Object> ipInfoPaging(int page, int totalCount) {
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

	// IP 정보 조회[페이징]
	@Override
	public List<IpInfo> selectIpInfoByPage(int start, int end) {
		List<IpInfo> ipInfo =  ipRepository.selectIpInfoByPage(start, end);
		return ipInfo;
	}

	// IP 개수 조회
	@Override
	public int selectIpCount() {
		return ipRepository.selectIpCount();
	}
	
	// IP 상세 정보 조회
	@Override
	public IpInfo selectIpDetail(int ipSn) {
		return ipRepository.selectIpDetail(ipSn);
	}

	// IP 유형 조회
	@Override
	public List<IpCode> selectIpCode() {
		return ipRepository.selectIpCode();
	}

	// IP 자원 조회
	@Override
	public List<IpRes> selectResInfoByIpSn(int start, int end, int ipSn) {
		return ipRepository.selectResInfoByIpSn(start, end, ipSn);
	}

	// IP 검색 개수 조회
	@Override
	public int selectIpCountByKeyword(String keyword) {
		return ipRepository.selectIpCountByKeyword(keyword);
	}

	// IP 검색 결과 조회
	@Override
	public List<IpInfo> searchIp(String keyword, int start, int end) {
		return ipRepository.searchIp(keyword, start, end);
	}

}
