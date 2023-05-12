package comon.service;

import java.util.List;

import comon.dto.ImageDto;
import comon.dto.ImageReviewDto;
import comon.dto.ImageUserDto;
import comon.dto.PortDto;

public interface ComonMainService {

	// 메인에 랜덤으로 3개 앱 목록 출력
	public List<ImageDto> openRecommendApp() throws Exception;

	// 다운로드 카운트가 가장 높은 3개 출력
	public List<ImageDto> openAppRanking() throws Exception;

	// 전체 앱 리스트 출력
	public List<ImageDto> openAllAppList() throws Exception;

	// 카테고리별 앱 리스트 출력
	public List<ImageDto> openAppListByCategory(int categoryIdx) throws Exception;

	// 다운로드 카운트 높은 순으로 앱 리스트 출력
	public List<ImageDto> openAppListByCount() throws Exception;

	// 출시일 순으로 앱 리스트 출력
	public List<ImageDto> openAppListByRegistDt() throws Exception;

	// 앱 이름 가나다 순으로 목록 출력
	public List<ImageDto> openAppListByName() throws Exception;

	// 앱 상세 페이지 조회
	public ImageDto openAppDetail(int imageIdx) throws Exception;

	// download 전 download 내역 확인
	public String checkDownload(ImageUserDto imageUserDto) throws Exception;

	// 재다운로드 로직
	public int toggleDeleteYn(ImageUserDto imageUserDto) throws Exception;

	// 앱 다운로드
	public int downloadApp(ImageUserDto imageUserDto) throws Exception;

	// 포트 테이블에 데이터 추가 후 사용할 포트 번호 반환받기
	public int insertPort(PortDto portDto) throws Exception;

	// 이미지 idx로 엔드포인트 이름 조회
	public Long selectRandomNum(int imageIdx) throws Exception;

	// 리뷰 점수 평균
	public Double openReviewAverage(int imageIdx) throws Exception;

	// 리뷰 별점 점수별 비율 출력
	public Integer selectScoreRatio(int scoreCount, int imageIdx) throws Exception;
	
	// 리뷰 목록 출력
	public List<ImageReviewDto> openReviewList(int imageIdx) throws Exception;

	// 이미지 인덱스로 yamlFile 선택
	public String selectYamlFile(int imageIdx) throws Exception;

	// 유저 아이디로 idx 조회
	public int selectUserIdx(String userId) throws Exception;
}
