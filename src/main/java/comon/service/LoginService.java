package comon.service;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetailsService;

import comon.dto.LoginDto;
import comon.dto.UserDto;

public interface LoginService extends UserDetailsService {

	public UserDto login(LoginDto loginDto) throws Exception;
	public int registUser(UserDto userDto, HttpSession session) throws Exception;
	public UserDto selectUserByUserId(String userName) throws Exception;
}

