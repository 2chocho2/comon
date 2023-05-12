package comon.dto;

import lombok.Data;

@Data
public class NoticeDto {
	private int noticeIdx;
	private String noticeTitle;
	private String noticeContent;
	private String registDt;
	private int noticeCategoryIdx;
	private String deleteYn;
	private String noticeCategoryName;
}
