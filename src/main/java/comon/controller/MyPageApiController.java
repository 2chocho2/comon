package comon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comon.dto.ImageDto;
import comon.dto.ImageReviewDto;
import comon.dto.ImageUserDto;
import comon.dto.QnaDto;
import comon.dto.UserDto;
import comon.service.ComonMainService;
import comon.service.MyPageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MyPageApiController {

	private final String UPLOAD_USER_YAMLFILE_PATH = "c:\\comon\\app\\useryamlfile\\";
	
	@Autowired
	MyPageService myPageService;
	
	@Autowired
	ComonMainService comonMainService;

	// 마이페이지 접속 시 사용자 이름 출력
	@GetMapping("/api/user/mypage/{userId}")
	public ResponseEntity<String> selectUserName(@PathVariable("userId") String userId) throws Exception {
		String userName = myPageService.selectUserName(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(userName);
	}
	
	// 유저가 사용하는 앱 목록 출력
	@GetMapping("/api/myservice/{userId}")
	public ResponseEntity<List<ImageDto>> openAppListByUser(@PathVariable("userId") String userId) throws Exception {
		int userIdx = myPageService.selectUserIdx(userId);
		List<ImageDto> list = myPageService.openAppListByUser(userIdx);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 마이페이지에서 작성한 리뷰 조회
	@GetMapping("/api/myservice/selectreview/{userId}")
	public ResponseEntity<List<ImageDto>> selectReview(@PathVariable("userId") String userId) throws Exception {
		int userIdx = myPageService.selectUserIdx(userId);
		List<ImageDto> list = myPageService.selectReview(userIdx);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 앱에 유저가 리뷰를 작성한적 있는지 확인
	@GetMapping("/api/myservice/{imageIdx}/{userId}") 
	public ResponseEntity<Integer> checkReview(@PathVariable ("imageIdx") int imageIdx, 
											   @PathVariable ("userId") String userId) throws Exception {
		int userIdx = myPageService.selectUserIdx(userId);
		ImageReviewDto imageReviewDto = new ImageReviewDto();
		imageReviewDto.setImageIdx(imageIdx);
		imageReviewDto.setUserIdx(userIdx);
		
		int selectCount = myPageService.checkReview(imageReviewDto);
		return ResponseEntity.status(HttpStatus.OK).body(selectCount);
	}

	// 리뷰 작성
	@PostMapping("/api/user/writereview/{imageIdx}")
	public ResponseEntity<Integer> writeReview(@PathVariable ("imageIdx") int imageIdx, @RequestBody ImageReviewDto imageReviewDto) throws Exception {
		int userIdx = myPageService.selectUserIdx(imageReviewDto.getUserId());
		imageReviewDto.setUserIdx(userIdx);
		
		int writeCount = myPageService.writeReview(imageReviewDto);

		if (writeCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(writeCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(writeCount);
		}
	}
	
	// 리뷰 삭제
	@DeleteMapping("/api/user/deletereview/{reviewIdx}")
	public ResponseEntity<Integer> deleteReview(@PathVariable("reviewIdx") int reviewIdx) throws Exception {
		int deleteCount = myPageService.deleteReview(reviewIdx);
		
		if (deleteCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deleteCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deleteCount);
		}
	}	

	// 앱 삭제
	@DeleteMapping("/api/mypage/{imageIdx}/{userId}")
	public ResponseEntity<Integer> deleteAppUser(@PathVariable("imageIdx") int imageIdx,
												@PathVariable("userId") String userId,
												HttpSession session) throws Exception {
		
		// 해당 사용자가 사용하는 디렉터리 정보 삭제
		long randomNum = comonMainService.selectRandomNum(imageIdx);
		final String deleteCommand = String.format("cmd /C RD /S /Q /comon\\%s\\%s", userId, randomNum);
		
		Process process = null;
		int exitCode = 0;
		
		try {
			process = Runtime.getRuntime().exec(deleteCommand);
			exitCode = process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			exitCode = -1;
		}
				
		// t_image_user에서 해당 앱-사용자에 대한 delete_yn을 y로 변경
		int userIdx = myPageService.selectUserIdx(userId);
		ImageUserDto imageUserDto = new ImageUserDto();
		
		imageUserDto.setUserIdx(userIdx);
		imageUserDto.setImageIdx(imageIdx);
		
		int deleteCount = myPageService.deleteAppUser(imageUserDto);
		
		if (deleteCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exitCode);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(exitCode);
		}
	}

	// 회원 정보 수정 화면 요청
	@GetMapping("/api/mypage/edit/{userId}")
	public ResponseEntity<UserDto> selectUserInfo(@PathVariable("userId") String userId) throws Exception {
		UserDto userDto = myPageService.selectUserInfo(userId);
		
		if (userDto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userDto);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	// 회원 정보 수정
	@PostMapping("/api/mypage/edit")
	public ResponseEntity<Integer> editUserInfo(@RequestBody UserDto userDto) throws Exception {
		int editCount = myPageService.editUserInfo(userDto);
		
		if (editCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(editCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(editCount);
		}
	}
	
	// 앱 실행
	@GetMapping("/api/runapp/{userId}/{imageIdx}")
	ResponseEntity<Map<String, Integer>> runApp(@PathVariable("userId") String userId, 
									@PathVariable("imageIdx") int imageIdx,
									 HttpSession session) throws Exception {
		
		Map<String, Integer> result = new HashMap<>();
		
		Process process = null;
		int exitCode = 0;
		
		int userIdx = myPageService.selectUserIdx(userId);
		ImageUserDto imageUserDto = myPageService.selectImageUserDto(userIdx, imageIdx);
		
		// 이미지 idx를 기준으로 imageDto 조회
		ImageDto imageDto = comonMainService.openAppDetail(imageIdx);
		long randomNum = comonMainService.selectRandomNum(imageIdx);
		// 유저의 고유 yamlfile 조회
		// 파일명: 고유숫자-yamlfile-devidx-docker-compose-userid.yaml
		String userYamlFileName = String.format("%s-yamlfile-%s-docker-compose-%s.yaml", randomNum, imageDto.getUserIdx(), userId);
		
		// c:\\comon\\userid\\imagename 디렉터리에서 해당 유저의 yaml 파일 compose up
		final String runCommand = String.format("docker-compose -f %s%s\\%s up -d ", UPLOAD_USER_YAMLFILE_PATH, userId, userYamlFileName);
		
		try {
			process = Runtime.getRuntime().exec(runCommand);
			log.debug(">>>>>>>>>>>>>>>>" + runCommand);
		} catch (IOException e) {
			e.printStackTrace();
			exitCode = -1;
		} catch(Exception e) {
			log.debug("############" + e.getMessage());
			e.printStackTrace();
		}
		
		// imageUserDto에서 리액트 포트 조회
		int endpointPort = imageUserDto.getReactPort();
		// 포트, exitCode 리턴
		result.put("endpointPort", endpointPort);
		result.put("exitCode", exitCode);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
}
