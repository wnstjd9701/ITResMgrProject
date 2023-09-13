package kr.co.kcc.itmgr.domain.resinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;

@Repository
@Mapper
public interface IResInfoRepository {
	List<ResInfo> selectAllResInfo(); //자원정보 모두 찾아오기
	List<ResInfo> searchResInfoByResClass(); //계층형쿼리
	List<ResInfo> searchResInfo(ResInfo resInfo); //검색결과조회
	
}
