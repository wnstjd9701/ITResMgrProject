package kr.co.kcc.itmgr.domain.placemap.service;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.global.common.DoName;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlaceMapService implements IPlaceMapService {
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
	    	log.info("name: " + doName.getDoName().split(",")[0]);
	    	log.info("name: " + doName.getDoName().split(",")[1]);
	        if (address.startsWith(doName.getDoName().split(",")[0]) || address.startsWith(doName.getDoName().split(",")[1])) {
	        	log.info("rightDoName: " + doName.getDoName().split(",")[0]);
	            return doName;
	        }
	    }
	    return null;
	}
}
