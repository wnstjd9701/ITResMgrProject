package kr.co.kcc.itmgr.domain.commoncode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;

@Repository
@Mapper
public interface ICommonCodeRepository {
	List<CommonCode> selectAllCommonCode();
}
