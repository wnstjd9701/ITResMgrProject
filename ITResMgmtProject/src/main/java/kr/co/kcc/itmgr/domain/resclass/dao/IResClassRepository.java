package kr.co.kcc.itmgr.domain.resclass.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.resclass.model.ResClass;


@Repository
@Mapper
public interface IResClassRepository {
	List<ResClass> selectAllResClass(); 
	List<Map<Object, Object>> numberOfResByResClass(); //자원분류별 자원의 개수
	
}
