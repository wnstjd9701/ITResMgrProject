package kr.co.kcc.itmgr.domain.user.service;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.user.dao.IUserRepository;
import kr.co.kcc.itmgr.domain.user.model.User;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
	private final IUserRepository userRepository;

	@Override
	public User selectUser(String employeeId) {
		return userRepository.selectUser(employeeId);
		
	}
}
