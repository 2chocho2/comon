package comon.dto;

import java.util.List;

import lombok.Data;

@Data
public class ImageDto {

	private int imageIdx;
	private String imageName;
	private String thumbnailImage;
	private String imageDescription;
	private String imageDetail;
	private int categoryIdx;
	private int deleteIdx;
	private String iconImage;
	private String yamlFile;
	private int userIdx;
	private int statusIdx;
	private int denyIdx;
	private int downloadCount;
	private String screenshotImage1;
	private String screenshotImage2;
	private String screenshotImage3;
	private String screenshotImage4;
	private String screenshotImage5;
	private String screenshotImage6;
	private String registDt;
	private String deleteDt;
	private String endpointServiceName;
	private long randomNum;
	
	private String userName;
	private String denyName;
	private int springbootPort;
	private int reactPort;
	private String monthlyCount;
	
	public int setScreenshotImage(List<String> images) {
		int i = 0;
		for (String image : images) {
			i ++;
			switch(i) {
			case 1:
				setScreenshotImage1(image);
				break;
			case 2:
				setScreenshotImage2(image);
				break;
			case 3:
				setScreenshotImage3(image);
				break;
			case 4:
				setScreenshotImage4(image);
				break;
			case 5:
				setScreenshotImage5(image);
				break;
			case 6:
				setScreenshotImage6(image);
				break;
			}
		}
		return i;
	}
}
