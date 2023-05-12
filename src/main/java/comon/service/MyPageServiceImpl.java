package comon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comon.dto.ImageDto;
import comon.dto.ImageReviewDto;
import comon.dto.ImageUserDto;
import comon.dto.QnaDto;
import comon.dto.UserDto;
import comon.mapper.MyPageMapper;

@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	MyPageMapper myPageMapper;

	// 마이페이지 접속 시 사용자 이름 출력
	public String selectUserName(String userId) throws Exception {
		return myPageMapper.selectUserName(userId);
	}

	// 리뷰 작성
	public int writeReview(ImageReviewDto imageReviewDto) throws Exception {
		return myPageMapper.writeReview(imageReviewDto);
	}

	// 리뷰 삭제
	public int deleteReview(int reviewIdx) throws Exception {
		return myPageMapper.deleteReview(reviewIdx);
	}
	
	// 리뷰 목록 조회
	public List<ImageDto> selectReview(int userIdx) throws Exception {
		return myPageMapper.selectReview(userIdx);
	}
	
	// 이미지에 유저가 리뷰를 작성한적 있는지 확인
	public int checkReview(ImageReviewDto imageReviewDto) throws Exception {
		return myPageMapper.checkReview(imageReviewDto);
	}

	// 유저가 사용하는 앱 목록 출력
	public List<ImageDto> openAppListByUser(int userIdx) throws Exception {
		return myPageMapper.openAppListByUser(userIdx);
	}

	// 앱 삭제
	public int deleteAppUser(ImageUserDto imageUserDto) throws Exception {
		return myPageMapper.deleteAppUser(imageUserDto);
	}

	// 회원 정보 수정 화면 요청
	public UserDto selectUserInfo(String userId) throws Exception {
		return myPageMapper.selectUserInfo(userId);
	}

	// 회원 정보 수정
	public int editUserInfo(UserDto userDto) throws Exception {
		return myPageMapper.editUserInfo(userDto);
	}

	// 문의 내역 확인
	public List<QnaDto> selectMyQnaList(int userIdx) throws Exception {
		return myPageMapper.selectMyQnaList(userIdx);
	}

	// userid를 기준으로 idx 조회
	public int selectUserIdx(String userId) throws Exception {
		return myPageMapper.selectUserIdx(userId);
	}

	// imageUserDto 조회
	public ImageUserDto selectImageUserDto(int userIdx, int imageIdx) throws Exception {
		return myPageMapper.selectImageUserDto(userIdx, imageIdx);
	}
}
