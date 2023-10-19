package kr.co.kcc.itmgr.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import kr.co.kcc.itmgr.domain.user.model.User;
import kr.co.kcc.itmgr.domain.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final IUserService userService;

	@GetMapping("/signin")
	public String userLogin() {
		return "signin";
	}

	@PostMapping("/signin")
	public String loginCheck(@RequestParam("employeeId") String employeeId, @RequestParam("employeePwd") String employeePwd, HttpSession session, Model model) {

		User user = userService.selectUser(employeeId);
		log.info("user: " + user);

		if(user != null) {
			//아이디가 있는 경우
			String userPwd = user.getEmployeePwd();
			String userUseYN = user.getUseYN();
			System.out.println("-----------userUseYN" +userUseYN);
			String userStatusCode = user.getEmployeeStatusCode();
			System.out.println("-----------userStatusCode" +userStatusCode);
			if("N".equals(userUseYN)) {
				model.addAttribute("message", "삭제된 사원은 로그인 할 수 없습니다.");
			} else if("EMS003".equals(userStatusCode)) {
				model.addAttribute("message", "퇴직한 사원은 로그인 할 수 없습니다.");
			} else if("EMS002".equals(userStatusCode)) {
				model.addAttribute("message", "휴직중인 사원은 로그인 할 수 없습니다.");
			} else {
				//삭제,퇴직,휴직중인 사원이 아닐 경우

				if(userPwd.equals(employeePwd)) {	//비밀번호 일치 시
					session.setAttribute("employeeId", employeeId);
					session.setAttribute("employeeName", user.getEmployeeName());
					session.setAttribute("employeeTypeCode", user.getEmployeeTypeCode());
					return "/index";
				} else {
					model.addAttribute("message", " 비밀번호를 잘못 입력했습니다.\r\n" + "다시 입력해 주세요.");
				}
			}
		}else {
			//아이디가 없는 경우
			model.addAttribute("message", "등록되지 않은 사원입니다.");
		}
		
		session.invalidate();
		return "signin";
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "/signin";
	}


}
