package kr.co.kcc.itmgr.domain.installplace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.installplace.dao.IInstallPlaceRepository;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.installplace.model.InstallRes;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstallPlaceService implements IInstallPlaceService {
	
	private final IInstallPlaceRepository installPlaceRepository;

	// 모든 설치 장소 조회
	@Override
	public List<InstallPlace> selectAllPlace() {
		return installPlaceRepository.selectAllPlace();
	}
	
	// 설치 장소 검색
	@Override
	public List<InstallPlace> selectInstallPlaceByName(String placeName) {
		return installPlaceRepository.selectInstallPlaceByName(placeName);
	}
	
	// 자원 정보 조회
	@Override
	public List<InstallRes> selectResInformationByInstallPlaceName(String placeName) {
		return installPlaceRepository.selectResInformationByInstallPlaceName(placeName);
	}

	// 설치 장소 상세 정보 조회
	@Override
	public InstallPlace selectInstallPlaceDetail(String placeName) {
		return installPlaceRepository.selectInstallPlaceDetail(placeName);
	}

	// 설치 장소명 중복 체크
	@Override
	public int checkPlaceNameBySn(String placeName) {
		return installPlaceRepository.checkPlaceNameBySn(placeName);
	}
	
	// 설치 장소 등록
	@Override
	public int insertInstallPlace(InstallPlace installPlace) {
		return installPlaceRepository.insertInstallPlace(installPlace);
	}

	// 설치 장소 수정
	@Override
	public int updateInstallPlace(InstallPlace installPlace) {
		return installPlaceRepository.updateInstallPlace(installPlace);
	}

	
}
