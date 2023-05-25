package comon.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import comon.dto.UserDto;
import comon.service.LoginService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestLoginApiController {

	@Autowired
	private LoginService loginService;

	// 회원 가입 API
	@PostMapping("/api/regist")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto, HttpSession session) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>" + userDto);
		try {
			loginService.registUser(userDto, session);
			return ResponseEntity.ok("회원 가입이 완료되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 오류가 발생하였습니다.");
		}
	}

	// 아이디 중복체크
	@PostMapping("/api/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {

		int cnt = loginService.idCheck(id);
		return cnt;

	}

	// 이름 중복체크
	@PostMapping("/api/nameCheck")
	@ResponseBody
	public int nameCheck(@RequestParam("name") String name) {

		int cntN = loginService.nameCheck(name);
		return cntN;

	}

}