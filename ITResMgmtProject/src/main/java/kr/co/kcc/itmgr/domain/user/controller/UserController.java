package kr.co.kcc.itmgr.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.kcc.itmgr.domain.employee.model.Employee;
import kr.co.kcc.itmgr.domain.user.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@AllArgsConstructor
public class UserController {
	private final IUserService userService;
	
	@GetMapping("/login")
	public String userLogin() {
		// TypeCode를 가져온다.
		return "/login";
	}
	
	@PostMapping("/login/user")
	public String loginCheck(Employee user) {
		log.info("user: " + user);
		return "redirect:/";
	}
}
