package kr.co.kcc.itmgr.domain.user.service;

import kr.co.kcc.itmgr.domain.user.model.User;

public interface IUserService {
	//로그인시 id확인
	User selectUser(String employeeId);

}
