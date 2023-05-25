package comon.mapper;

import org.apache.ibatis.annotations.Mapper;

import comon.dto.LoginDto;
import comon.dto.UserDto;

@Mapper
public interface LoginMapper {

	public UserDto login(LoginDto loginDto) throws Exception;

	public int registUser(UserDto userDto) throws Exception;

	public UserDto selectUserByUserId(String userName);

	// 아이디 중복체크
	public int idCheck(String id);

	// 이름 중복체크
	public int nameCheck(String name);

}
