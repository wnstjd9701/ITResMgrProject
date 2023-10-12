package kr.co.kcc.itmgr.domain.resinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;

@Repository
@Mapper
public interface IResInfoRepository {
	List<ResInfo> selectAllResInfo(); //자원정보 모두 찾아오기
	List<ResInfo> searchResInfoByResClass(); //자원분류 조회
	List<ResInfo> searchResInfo(ResInfo resInfo); //검색결과조회
	
	List<ResClass> selectAllResClass(String resClassName); //자원분류리스트 조회
	
	void insertResInfo(ResInfo resInfo); //자원입력
	void updateResInfo(ResInfo resInfo); //자원수정
	
	List<CommonCodeDetail> selectResStatusCode(String codeGroupId); //자원상태코드 리스트 불러오기
	List<InstallPlace> selectResInstallPlace(); //자원설치장소 리스트 불러오기
	
	ResInfo selectResInfoDetail(String resName);
	
}
