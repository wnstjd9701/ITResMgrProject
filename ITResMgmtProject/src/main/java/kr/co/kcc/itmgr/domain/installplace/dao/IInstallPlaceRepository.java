package kr.co.kcc.itmgr.domain.installplace.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;

@Repository
@Mapper
public interface IInstallPlaceRepository {
	List<InstallPlace> selectAllAddress(); // 모든 설치 장소 조회
	
	List<InstallPlace> selectInstallPlaceByName(String placeName); // 설치 장소 검색
}
