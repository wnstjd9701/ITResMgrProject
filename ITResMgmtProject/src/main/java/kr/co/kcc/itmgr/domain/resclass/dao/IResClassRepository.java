package kr.co.kcc.itmgr.domain.resclass.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IResClassRepository {
	List<Map<String, String>> selectAllResClass();
	
}
