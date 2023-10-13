package kr.co.kcc.itmgr.domain.placemap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.installplace.dao.IInstallPlaceRepository;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.global.common.DoName;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class PlaceMapService implements IPlaceMapService {
	
	private final IInstallPlaceRepository installPlaceRepository;
	
	public InstallPlace getDoName(InstallPlace place) {
		String address = place.getInstallPlaceAddress();

		DoName mappedDoName = getMappedDoName(address);
		if (mappedDoName != null) {
			place.setDoName(mappedDoName.getDoName().split(",")[0]);
			place.setDoLatitude(mappedDoName.getDoLatitude());
			place.setDoLongitude(mappedDoName.getDoLongitude());
		}

		return place;
	}

	private DoName getMappedDoName(String address) {
		for (DoName doName : DoName.values()) {
			// 주소가 "서울" 또는 "서울특별시"로 시작하면 매핑
			String firstDoName = doName.getDoName().split(",")[0];
			String secondDoName = doName.getDoName().split(",")[1];

			if (address.startsWith(firstDoName) || address.startsWith(secondDoName)) {
				return doName;
			}
		}
		return null;
	}

	public Map<String, Integer> getDoCountValues() {
		Map<String, Integer> doNamesCount= new HashMap<String, Integer>();

		for (DoName doName : DoName.values()) {
			String[] parts = doName.getDoName().split(",");
			if (parts.length > 0) {
				String firstDoName = parts[0];
				String secondDoName = parts[1];
				int resCount = installPlaceRepository.selectResInfoCountByCity(firstDoName, secondDoName);
				doNamesCount.put(parts[0], resCount);
			}
		}
		return doNamesCount;
	}

	public DoName getDoValuesByDoName(String doName) {
		DoName selectedDoName = null;
		for (DoName enumValue : DoName.values()) {
			String firstDoName = enumValue.getDoName().split(",")[0];
			String secondDoName = enumValue.getDoName().split(",")[1];
			
			if(doName.startsWith(firstDoName) || doName.startsWith(secondDoName)) {
				return enumValue;
			}
		}
		return selectedDoName;
	}
	
	// 모든 설치 장소 조회
	@Override
	public List<InstallPlace> selectInstallPlaceList() {
		return installPlaceRepository.selectInstallPlaceList();
	}

	// 설치 장소 페이징 처리
	@Override
	public Map<String, Object> placeMapPaging(int page, int totalCount) {
		int totalPage=0;
		if(totalCount > 0) {
			totalPage=(int)Math.ceil(totalCount / 5.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage / 5.0));
		int nowPageBlock = (int)Math.ceil(page / 5.0);
		int startPage = (nowPageBlock - 1) * 5 + 1;
		int endPage=0;
		if(totalPage > nowPageBlock * 5) {
			endPage = nowPageBlock * 5;
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
}
