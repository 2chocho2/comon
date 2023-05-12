package comon.dto;

import lombok.Data;

@Data
public class ImageUserDto {

	private int imageIdx;
	private int userIdx;
	private String deleteYn;
	private int springbootPort;
	private int reactPort;
	private String downloadDate;
	
	private String userId;
	
}
