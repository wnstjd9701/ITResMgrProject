package kr.co.kcc.itmgr.domain.resclass.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;

@Repository
@Mapper
public interface IResClassRepository {
	List<ResClass> selectAllResClass(); 
	List<ResInfo> numberOfResByResClass(); //자원분류별 자원의 개수 
	
}
