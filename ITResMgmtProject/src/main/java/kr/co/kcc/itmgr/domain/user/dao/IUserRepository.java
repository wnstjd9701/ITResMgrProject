package kr.co.kcc.itmgr.domain.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.user.model.User;

@Repository
@Mapper
public interface IUserRepository {
	//로그인시 id확인
	User selectUser(String employeeId);

}
