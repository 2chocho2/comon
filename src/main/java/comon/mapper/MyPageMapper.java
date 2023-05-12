package comon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import comon.dto.ImageDto;
import comon.dto.ImageReviewDto;
import comon.dto.ImageUserDto;
import comon.dto.QnaDto;
import comon.dto.UserDto;

@Mapper
public interface MyPageMapper {

	// 마이페이지 접속 시 사용자 이름 출력
	public String selectUserName(@Param("userId") String userId) throws Exception;

	// 리뷰 작성
	public int writeReview(ImageReviewDto imageReviesDto) throws Exception;
	
	// 리뷰 삭제
	public int deleteReview(int reviewIdx)throws Exception;
	
	// 리뷰 목록 조회
	public List<ImageDto> selectReview(int userIdx) throws Exception;
	
	// 이미지에 유저가 리뷰를 작성한적 있는지 확인
	public int checkReview(ImageReviewDto imageReviewDto) throws Exception;

	// 유저가 사용하는 앱 목록 출력
	public List<ImageDto> openAppListByUser(int userIdx) throws Exception;

	// 앱 삭제
	public int deleteAppUser(ImageUserDto imageUserDto) throws Exception;

	// 회원 정보 수정 화면 요청
	public UserDto selectUserInfo(String userId) throws Exception;

	// 회원 정보 수정
	public int editUserInfo(UserDto userDto) throws Exception;

	// 문의 내역 확인
	public List<QnaDto> selectMyQnaList(int userIdx) throws Exception;

	// userid를 기준으로 idx 조회
	public int selectUserIdx(String userId) throws Exception;

	// imageUserDto 조회
	public ImageUserDto selectImageUserDto(@Param("userIdx") int userIdx, @Param("imageIdx") int imageIdx) throws Exception;
}
