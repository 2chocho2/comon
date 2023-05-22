package comon.dto;

import lombok.Data;

@Data
public class ImageReviewDto {

	private int reviewIdx;
	private int imageIdx;
	private String reviewContent;
	private String registDt;
	private int userIdx;
	private int scoreCount;
	private int selectCount;
	private String thumbnailImage;
	private String iconImage;
	private String imageName;
	private String userId;
	private String userName;
	
}
