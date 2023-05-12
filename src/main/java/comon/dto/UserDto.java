package comon.dto;

import lombok.Data;

@Data
public class UserDto {
	
	private int userIdx;
	private String userId;
	private String userName;
	private String userPassword;
	private String userPhoneNumber;
	private String userEmail;
	private int authIdx;
	private String signDate;
	
}
