package comon.dto;

import lombok.Data;

@Data
public class QnaDto {

	private int qnaIdx;
	private String qnaTitle;
	private String qnaContent;
	private String qnaComment;
	private int userIdx;
	private String createdDt;
	
}
