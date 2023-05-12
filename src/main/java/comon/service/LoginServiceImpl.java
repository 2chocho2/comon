package comon.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import comon.dto.LoginDto;
import comon.dto.UserDto;
import comon.mapper.LoginMapper;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDto login(LoginDto loginDto) throws Exception {
		return loginMapper.login(loginDto);
	}
	
	@Override
	public int registUser(UserDto userDto, HttpSession session) throws Exception {
		userDto.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
		return loginMapper.registUser(userDto);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto userDto = loginMapper.selectUserByUserId(username);
		if (userDto == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(userDto.getUserId(), userDto.getUserPassword(), 
				true, true, true, true, new ArrayList<>());		
	}

	@Override
	public UserDto selectUserByUserId(String userId) throws Exception {
		return loginMapper.selectUserByUserId(userId);
	}
	
	
}
