package kr.co.kcc.itmgr.domain.placemap.service;

import java.util.ArrayList;
import java.util.List;

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
			String firstDoName = doName.getDoName().split(",")[0];
			String secondDoName = doName.getDoName().split(",")[1];

			if (address.startsWith(firstDoName) || address.startsWith(secondDoName)) {
				return doName;
			}
		}
		return null;
	}

	public List<String> getDoValues() {
		List<String> doNames = new ArrayList<>();

		for (DoName doName : DoName.values()) {
			String[] parts = doName.getDoName().split(",");
			if (parts.length > 0) {
				doNames.add(parts[0]);
			}
		}
		return doNames;
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
}
