package comon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import comon.dto.ChartDto;
import comon.dto.DenyDto;
import comon.dto.ImageDto;
import comon.dto.UserDto;

@Mapper
public interface AdminMapper {

	// 1. 전체 앱 목록 조회
	public List<ImageDto> openAppList() throws Exception;

	// 1-1. 삭제 신청 상태의 앱 조회
	public List<ImageDto> openRegistDeleteAppList() throws Exception;

	// 1-2. 서비스 중인 앱 조회
	public List<ImageDto> openAppListOnService() throws Exception;

	// 1-3. 등록 상태의 앱 조회(심사 전)
	public List<ImageDto> openRegistAppList() throws Exception;

	// 2. 삭제된 앱 목록 조회
	public List<ImageDto> openDeletedAppList() throws Exception;

	// 3. 개발자별 앱 목록 조회
	public List<ImageDto> openAppListByUser(int userIdx) throws Exception;

	// 4-0. 앱 등록 전 devIdx 세팅
	public int selectIdxByUserId(String userId) throws Exception;

	// 4. 앱 등록
	public int registApp(ImageDto imageDto) throws Exception;

	// 5. 앱 삭제 신청
	public int registDeleteApp(@Param("imageIdx") int imageIdx) throws Exception;

	// 6. 앱 삭제 등록
	public int deleteApp(@Param("imageIdx") int imageIdx) throws Exception;

	// 7. 카테고리 코드로 카테고리 이름 조회
	public String selectCategoryNameByIdx(int categoryIdx) throws Exception;

	// 8. 상태 코드로 심사 상태 이름 조회
	public String selectStatusNameByIdx(int statusIdx) throws Exception;

	// 10. 이미지 번호로 개발자 정보 조회
	public String selectUserByImageIdx(int imageIdx) throws Exception;

	// 11. 유저 인덱스로 유저 이름 조회
	public String selectNameByUserIdx(int userIdx) throws Exception;

	// 12. 심사를 위한 이미지 상세 조회
	public ImageDto openImageDetail(@Param("imageIdx") int imageIdx) throws Exception;

	// 심사 후 출시 승인
	public int accessRegist(@Param("imageIdx") int imageIdx) throws Exception;

	// 심사 후 출시 거절
	public int denyRegist(@Param("imageIdx") int imageIdx, @Param("denyIdx") int denyIdx) throws Exception;

	// 심사 거절 시 사유 목록 조회
	public List<DenyDto> openDenyReasonList() throws Exception;

	// 개발자 setting - 본인 정보 조회 및 수정
	public UserDto openUserPage(@Param("userId") String userId) throws Exception;

	// 관리자 setting - 전체 사용자 정보 조회
	public List<UserDto> selectAllUser() throws Exception;

	// 관리자 setting - 전체 개발자 정보 조회
	public List<UserDto> selectAllDev() throws Exception;

	// 개발자 회원 정보 수정
	public int editUserInfo(UserDto data) throws Exception;

	// 관리자 차트 - 월별 앱 출시 건수 차트
	public List<ChartDto> monthlyOpenAppByCount() throws Exception;

	// 관리자 차트 - 다운로드 카운트 랭킹순 앱 리스트
	public List<ImageDto> openAppRankByCount() throws Exception;

	// 관리자 차트 - 사용자 통계(일반 사용자)
	public int countAllUser() throws Exception;

	// 관리자 차트 - 사용자 통계(개발자)
	public int countAllDev() throws Exception;

	// 관리자 차트 - 전체 앱 다운로드 수 (월별) 차트
	public List<ChartDto> totalAppDownload() throws Exception;

	// 관리자 차트 - 앱별 다운로드 수 (월별) 차트
	public List<ChartDto> appDownload(@Param("imageIdx") int imageIdx) throws Exception;

	// 관리자 차트 - 앱 전체 카운트
	public List<ChartDto> totalCount() throws Exception;
}
