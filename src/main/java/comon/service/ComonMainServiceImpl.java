package comon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comon.dto.ImageDto;
import comon.dto.ImageReviewDto;
import comon.dto.ImageUserDto;
import comon.dto.PortDto;
import comon.mapper.ComonMainMapper;

@Service
public class ComonMainServiceImpl implements ComonMainService {

	@Autowired
	ComonMainMapper comonMainMapper;

	// 메인에 랜덤으로 3개 앱 목록 출력
	public List<ImageDto> openRecommendApp() throws Exception {
		return comonMainMapper.openRecommendApp();
	}

	// 다운로드 카운트가 가장 높은 3개 출력
	public List<ImageDto> openAppRanking() throws Exception {
		return comonMainMapper.openAppRanking();
	}

	// 전체 앱 리스트 출력
	public List<ImageDto> openAllAppList() throws Exception {
		return comonMainMapper.openAllAppList();
	}

	// 카테고리별 앱 리스트 출력
	public List<ImageDto> openAppListByCategory(int categoryIdx) throws Exception {
		return comonMainMapper.openAppListByCategory(categoryIdx);
	}

	// 다운로드 카운트 높은 순으로 앱 리스트 출력
	public List<ImageDto> openAppListByCount() throws Exception {
		return comonMainMapper.openAppListByCount();
	}

	// 출시일 순으로 앱 리스트 출력
	public List<ImageDto> openAppListByRegistDt() throws Exception {
		return comonMainMapper.openAppListByRegistDt();
	}

	// 앱 이름 가나다 순으로 목록 출력
	public List<ImageDto> openAppListByName() throws Exception {
		return comonMainMapper.openAppListByName();
	}

	// 앱 상세 페이지 조회
	public ImageDto openAppDetail(int imageIdx) throws Exception {
		return comonMainMapper.openAppDetail(imageIdx);
	}

	// download 전 download 내역 확인
	public String checkDownload(ImageUserDto imageUserDto) throws Exception {
		return comonMainMapper.checkDownload(imageUserDto);
	}

	// 재다운로드 로직
	public int toggleDeleteYn(ImageUserDto imageUserDto) throws Exception {
		return comonMainMapper.toggleDeleteYn(imageUserDto);
	}

	// 앱 다운로드
	public int downloadApp(ImageUserDto imageUserDto) throws Exception {
		comonMainMapper.addDownloadCount(imageUserDto.getImageIdx());

		int userIdx = comonMainMapper.selectUserIdx(imageUserDto.getUserId());
		imageUserDto.setUserIdx(userIdx);

		return comonMainMapper.downloadApp(imageUserDto);
	}

	// 포트 테이블에 데이터 추가 후 사용할 포트 번호 반환받기
	public int insertPort(PortDto portDto) throws Exception {
		return comonMainMapper.insertPort(portDto);
	}

	// 이미지 idx로 고유 번호 조회
	public Long selectRandomNum(int imageIdx) throws Exception {
		return comonMainMapper.selectRandomNum(imageIdx);
	}

	// 리뷰 점수 평균
	public Double openReviewAverage(int imageIdx) throws Exception {
		Double avg = comonMainMapper.openReviewAverage(imageIdx);
		if (avg == null) {
			avg = 5.0d;
		}
		return avg;
	}
	
	// 리뷰 별점 점수별 비율 출력
	public Integer selectScoreRatio(int scoreCount, int imageIdx) throws Exception {
		Integer ratio = comonMainMapper.selectScoreRatio(scoreCount, imageIdx);
		return ratio != null ? ratio : 0;
	}

	// 리뷰 목록 출력
	public List<ImageReviewDto> openReviewList(int imageIdx) throws Exception {
		return comonMainMapper.openReviewList(imageIdx);
	}

	// 이미지 인덱스로 yamlFile 선택
	public String selectYamlFile(int imageIdx) throws Exception {
		return comonMainMapper.selectYamlFile(imageIdx);
	}

	// 유저 아이디로 idx 조회
	public int selectUserIdx(String userId) throws Exception {
		return comonMainMapper.selectUserIdx(userId);
	}
}
