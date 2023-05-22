package comon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comon.dto.ChartDto;
import comon.dto.DenyDto;
import comon.dto.ImageDto;
import comon.dto.UserDto;
import comon.mapper.AdminMapper;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	// 1. 전체 앱 목록 조회
	@Override
	public List<ImageDto> openAppList() throws Exception {
		return adminMapper.openAppList();
	}

	// 1-1. 삭제 신청 상태의 앱 조회
	@Override
	public List<ImageDto> openRegistDeleteAppList() throws Exception {
		return adminMapper.openRegistDeleteAppList();
	}

	// 1-2. 서비스 중인 앱 조회
	@Override
	public List<ImageDto> openAppListOnService() throws Exception {
		return adminMapper.openAppListOnService();
	}

	// 1-3. 등록 상태의 앱 조회(심사 전)
	@Override
	public List<ImageDto> openRegistAppList() throws Exception {
		return adminMapper.openRegistAppList();
	}

	// 2. 삭제된 앱 목록 조회
	@Override
	public List<ImageDto> openDeletedAppList() throws Exception {
		return adminMapper.openDeletedAppList();
	}

	// 3. 개발자별 앱 목록 조회
	@Override
	public List<ImageDto> openAppListByUser(int userIdx) throws Exception {
		return adminMapper.openAppListByUser(userIdx);
	}

	// 4-0. 앱 등록 전 devIdx 세팅
	public int selectIdxByUserId(String userId) throws Exception {
		return adminMapper.selectIdxByUserId(userId);
	}

	// 4. 앱 등록
	@Override
	public int registApp(ImageDto imageDto) throws Exception {
		return adminMapper.registApp(imageDto);
	}

	// 5. 앱 삭제 신청
	@Override
	public int registDeleteApp(int imageIdx) throws Exception {
		return adminMapper.registDeleteApp(imageIdx);
	}

	// 6. 앱 삭제 등록
	@Override
	public int deleteApp(int imageIdx) throws Exception {
		return adminMapper.deleteApp(imageIdx);
	}

	// 7. 카테고리 코드로 카테고리 이름 조회
	@Override
	public String selectCategoryNameByIdx(int categoryIdx) throws Exception {
		return adminMapper.selectCategoryNameByIdx(categoryIdx);
	}

	// 8. 상태 코드로 심사 상태 이름 조회
	@Override
	public String selectStatusNameByIdx(int statusIdx) throws Exception {
		return adminMapper.selectStatusNameByIdx(statusIdx);
	}

	// 10. 이미지 번호로 개발자 정보 조회
	@Override
	public String selectUserByImageIdx(int imageIdx) throws Exception {
		return adminMapper.selectUserByImageIdx(imageIdx);
	}

	// 11. 유저 인덱스로 유저 이름 조회
	public String selectNameByUserIdx(int userIdx) throws Exception {
		return adminMapper.selectNameByUserIdx(userIdx);
	}

	// 12. 심사를 위한 이미지 상세 조회
	public ImageDto openImageDetail(int imageIdx) throws Exception {
		return adminMapper.openImageDetail(imageIdx);
	}

	// 심사 후 출시 승인
	public int accessRegist(int imageIdx) throws Exception {
		return adminMapper.accessRegist(imageIdx);
	}

	// 심사 후 출시 거절
	public int denyRegist(int imageIdx, int denyIdx) throws Exception {
		return adminMapper.denyRegist(imageIdx, denyIdx);
	}

	// 심사 거절 시 사유 목록 조회
	public List<DenyDto> openDenyReasonList() throws Exception {
		return adminMapper.openDenyReasonList();
	}

	// 개발자 setting - 본인 정보 조회 및 수정
	public UserDto openUserPage(String userId) throws Exception {
		return adminMapper.openUserPage(userId);
	}

	// 관리자 setting - 전체 사용자 정보 조회
	public List<UserDto> selectAllUser() throws Exception {
		return adminMapper.selectAllUser();
	}

	// 관리자 setting - 전체 개발자 정보 조회
	public List<UserDto> selectAllDev() throws Exception {
		return adminMapper.selectAllDev();
	}

	// 개발자 회원 정보 수정
	public int editUserInfo(UserDto data) throws Exception {
		return adminMapper.editUserInfo(data);
	}

	// 관리자 차트 - 월별 앱 출시 건수 차트
	public List<ChartDto> monthlyOpenAppByCount() throws Exception {
		return adminMapper.monthlyOpenAppByCount();
	}

	// 관리자 차트 - 다운로드 카운트 랭킹순 앱 리스트
	public List<ImageDto> openAppRankByCount() throws Exception {
		return adminMapper.openAppRankByCount();
	}

	// 관리자 차트 - 사용자 통계(일반 사용자)
	public int countAllUser() throws Exception {
		return adminMapper.countAllUser();
	}

	// 관리자 차트 - 사용자 통계(개발자)
	public int countAllDev() throws Exception {
		return adminMapper.countAllDev();
	}

	// 전체 앱 다운로드 수 (월별) 차트
	public List<ChartDto> totalAppDownload() throws Exception {
		return adminMapper.totalAppDownload();
	}

	// 앱별 다운로드 수 (월별) 차트
	public List<ChartDto> appDownload(int imageIdx) throws Exception {
		return adminMapper.appDownload(imageIdx);
	}

	// 관리자 차트 - 전체 누적 카운트
	public List<ChartDto> totalCount() throws Exception {
		return adminMapper.totalCount();
	}
}
