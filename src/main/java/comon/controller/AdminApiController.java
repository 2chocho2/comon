package comon.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import comon.dto.ChartDto;
import comon.dto.DenyDto;
import comon.dto.ImageDto;
import comon.dto.UserDto;
import comon.service.AdminService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AdminApiController {

	private final String CONST_AWS_DIR = "c:\\comon\\";
	private final String UPLOAD_ICON_PATH = "c:\\comon\\app\\icon\\";
	private final String UPLOAD_THUMBNAIL_PATH = "c:\\comon\\app\\thumbnail\\";
	private final String UPLOAD_SCREENSHOT_PATH = "c:\\comon\\app\\screenshot\\";
	private final String UPLOAD_YAMLFILE_PATH = "c:\\comon\\app\\yamlfile\\";

	private ImageDto imageDto;

	@Autowired
	private AdminService adminService;

	// 전체 앱 목록 조회
	@GetMapping("/api/admin/applist")
	public ResponseEntity<List<ImageDto>> openAppList() throws Exception {
		List<ImageDto> list = adminService.openAppList();

		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	// 삭제 신청 상태의 앱 목록 조회
	@GetMapping("/api/admin/applist/registdelete")
	public ResponseEntity<List<ImageDto>> openRegistDeleteAppList() throws Exception {
		List<ImageDto> list = adminService.openRegistDeleteAppList();

		if (list != null & list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	// 서비스 중인 앱 목록 조회
	@GetMapping("/api/admin/applist/onservice")
	public ResponseEntity<List<ImageDto>> openAppListOnService() throws Exception {
		List<ImageDto> list = adminService.openAppListOnService();

		if (list != null & list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	// 등록 상태의 앱 목록 조회(심사 전)
	@GetMapping("/api/admin/applist/regist")
	public ResponseEntity<List<ImageDto>> openRegistAppList() throws Exception {
		List<ImageDto> list = adminService.openRegistAppList();

		if (list != null & list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	// 삭제된 앱 목록 조회
	@GetMapping("/api/admin/applist/delete")
	public ResponseEntity<List<ImageDto>> openDeletedAppList() throws Exception {
		List<ImageDto> list = adminService.openDeletedAppList();

		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	// 심사를 위한 이미지 상세 조회
	@GetMapping("/api/admin/applist/{imageIdx}")
	public ResponseEntity<ImageDto> openImageDetail(@PathVariable("imageIdx") int imageIdx) throws Exception {

		ImageDto imageDto = adminService.openImageDetail(imageIdx);

		if (imageDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(imageDto);
		}
	}

	// 앱 삭제 등록
	@DeleteMapping("/api/admin/registdelete/{imageIdx}")
	public ResponseEntity<Integer> deleteApp(@PathVariable("imageIdx") int imageIdx) throws Exception {

		int deleteCount = adminService.deleteApp(imageIdx);

		if (deleteCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deleteCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deleteCount);
		}
	}

	// 심사 후 출시 승인
	@PutMapping("/api/admin/access/{imageIdx}")
	public ResponseEntity<Integer> accessRegist(@PathVariable("imageIdx") int imageIdx) throws Exception {
		int accessCount = adminService.accessRegist(imageIdx);

		if (accessCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(accessCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(accessCount);
		}
	}

	// 심사 후 출시 거절
	@PutMapping("/api/admin/deny/{imageIdx}/{denyIdx}")
	public ResponseEntity<Integer> denyRegist(@PathVariable("imageIdx") int imageIdx,
			@PathVariable("denyIdx") int denyIdx) throws Exception {
		int denyCount = adminService.denyRegist(imageIdx, denyIdx);

		if (denyCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(denyCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(denyCount);
		}
	}

	// 심사 거절 시 사유 목록 조회
	@GetMapping("/api/admin/denylist")
	public ResponseEntity<List<DenyDto>> openDenyReasonList() throws Exception {
		List<DenyDto> denyDto = adminService.openDenyReasonList();

		if (denyDto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(denyDto);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// 관리자 setting - 전체 사용자, 개발자 정보 조회
	@GetMapping("/api/admin/setting")
	public ResponseEntity<Map<String, List<?>>> selectAllUserAndDev() throws Exception {
		List<UserDto> userList = adminService.selectAllUser();
		List<UserDto> devList = adminService.selectAllDev();

		Map<String, List<?>> resultMap = new HashMap<>();
		resultMap.put("userList", userList);
		resultMap.put("devList", devList);

		return ResponseEntity.ok().body(resultMap);
	}

	// 개발자별 앱 목록 조회
	@GetMapping("/api/dev/applist/{userId}")
	public ResponseEntity<Map<String, Object>> openAppListByUser(@PathVariable("userId") String userId)
			throws Exception {
		Map<String, Object> result = new HashMap<>();

		int userIdx = adminService.selectIdxByUserId(userId);
		List<ImageDto> list1 = adminService.openAppListByUser(userIdx);
		List<DenyDto> list2 = adminService.openDenyReasonList();

		result.put("list1", list1);
		result.put("list2", list2);

		if (list1 != null && list2 != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	// 앱 등록 전 devIdx 세팅
	@GetMapping("/api/dev/getidx/{userId}")
	public ResponseEntity<Integer> getUserIdx(@PathVariable("userId") String userId) throws Exception {
		int userIdx = adminService.selectIdxByUserId(userId);

		return ResponseEntity.ok(userIdx);
	}

	// 앱 등록
	@PostMapping("/api/dev/registapp")
	public ResponseEntity<String> registApp(
			@RequestPart(value = "iconimage", required = false) MultipartFile[] iconimage,
			@RequestPart(value = "thumbnailimage", required = false) MultipartFile[] thumbnailimage,
			@RequestPart(value = "screenshotimage", required = false) MultipartFile[] screenshotimage,
			@RequestPart(value = "yamlFile", required = false) MultipartFile[] yamlFile,
			@RequestPart(value = "data", required = false) ImageDto data, HttpServletRequest request) throws Exception {

		String uploadedDatas = "";
		uploadedDatas += "imageName: " + data.getImageName() + "\n";
		uploadedDatas += "imageDescription: " + data.getImageDescription() + "\n";
		uploadedDatas += "imageDetail: " + data.getImageDetail() + "\n";
		log.debug(">>>>>>>>>>>>>", data);
		log.debug(">>>>>>>>>>>>", uploadedDatas);
		long randomNum = System.currentTimeMillis();
		data.setRandomNum(randomNum);

		List<Map<String, String>> resultList = saveFiles(iconimage, UPLOAD_ICON_PATH, "icon", randomNum, data.getUserIdx());
		for (Map<String, String> result : resultList) {
			String iconImage = result.get("savedFileName");
			data.setIconImage(iconImage);
		}

		resultList = saveFiles(thumbnailimage, UPLOAD_THUMBNAIL_PATH, "thumbnail", randomNum, data.getUserIdx());
		for (Map<String, String> result : resultList) {
			String thumbnailImage = result.get("savedFileName");
			data.setThumbnailImage(thumbnailImage);
		}

		resultList = saveFiles(screenshotimage, UPLOAD_SCREENSHOT_PATH, "screenshot", randomNum, data.getUserIdx());
		int i = 1;
		for (Map<String, String> result : resultList) {
			switch (i) {
			case 1:
				data.setScreenshotImage1(result.get("savedFileName"));
				break;
			case 2:
				data.setScreenshotImage2(result.get("savedFileName"));
				break;
			case 3:
				data.setScreenshotImage3(result.get("savedFileName"));
				break;
			case 4:
				data.setScreenshotImage4(result.get("savedFileName"));
				break;
			case 5:
				data.setScreenshotImage5(result.get("savedFileName"));
				break;
			case 6:
				data.setScreenshotImage6(result.get("savedFileName"));
				break;
			default:
				break;
			}
			i++;
		}

		resultList = saveFiles(yamlFile, UPLOAD_YAMLFILE_PATH, "yamlfile", randomNum, data.getUserIdx());
		for (Map<String, String> result : resultList) {
			String yaml = result.get("savedFileName");
			data.setYamlFile(yaml);

		}
		log.debug(">>>>>>>>>>>>>", data);
		log.debug(">>>>>>>>>>>>", uploadedDatas);
		int insertCount = adminService.registApp(data);
		if (insertCount > 0) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "정상적으로 등록되었습니다.");
			result.put("count", insertCount);
			return ResponseEntity.ok(uploadedDatas);
		} else {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertCount);
			return ResponseEntity.ok(uploadedDatas);
		}
	}

	// 앱 삭제 신청
	@PutMapping("/api/dev/registdelete/{imageIdx}")
	public ResponseEntity<Integer> registDeleteApp(@PathVariable("imageIdx") int imageIdx) throws Exception {

		int registCount = adminService.registDeleteApp(imageIdx);

		if (registCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(registCount);
		}
	}

	// 이미지, yaml 파일 저장 로직 
	private List<Map<String, String>> saveFiles(MultipartFile[] files, String path, String field, long randomNum,
			int userIdx) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				String savedFileName = randomNum + "-" + field + "-" + userIdx + "-" + originFileName;

				try {
					File f = new File(path + savedFileName);
					mf.transferTo(f);

					Map<String, String> result = new HashMap<>();
					result.put("originalFileName", originFileName);
					result.put("savedFileName", savedFileName);
					resultList.add(result);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}

	// 이미지 다운로드 로직
	@GetMapping("/api/getimage/{field}/{fileName}")
	private void getImage(@PathVariable("field") String field, @PathVariable("fileName") String fileName,
			HttpServletResponse response) throws Exception {

		String path = "";
		if (field.equals("icon")) {
			path = UPLOAD_ICON_PATH;
		} else if (field.equals("thumbnail")) {
			path = UPLOAD_THUMBNAIL_PATH;
		} else if (field.equals("screenshot")) {
			path = UPLOAD_SCREENSHOT_PATH;
		}

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			response.setHeader("Content-Disposition", "inline");

			byte[] buf = new byte[1024];
			fis = new FileInputStream(path + fileName);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(buf, 0, 1024)) != -1) {
				bos.write(buf, 0, read);
			}
		} finally {
			bos.close();
			bis.close();
			fis.close();
		}
	}

	// yaml 파일 다운로드 로직
	@GetMapping("/api/download/{yamlFile}")
	public void download(HttpServletResponse response, @PathVariable("yamlFile") String yamlFile) throws Exception {
		try {
			File file = new File(UPLOAD_YAMLFILE_PATH + yamlFile);
			response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
			response.setHeader("Content-Type", "application/octet-stream");

			FileInputStream fileInputStream = new FileInputStream(UPLOAD_YAMLFILE_PATH + yamlFile);
			OutputStream out = response.getOutputStream();

			int read;
			byte[] buffer = new byte[1024];
			while ((read = fileInputStream.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
		} catch (Exception e) {
			throw new Exception("download error");
		}
	}

	// 개발자 setting - 본인 정보 조회 및 수정
	@GetMapping("/api/dev/mypage/{userId}")
	public ResponseEntity<UserDto> openUserPage(@PathVariable("userId") String userId) throws Exception {
		UserDto userDto = adminService.openUserPage(userId);

		if (userDto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userDto);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// 개발자 회원 정보 수정
	@PutMapping("/api/dev/mypage/edit")
	public ResponseEntity<Integer> editUserInfo(@RequestBody UserDto data) throws Exception {

		int editCount = adminService.editUserInfo(data);

		if (editCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(editCount);
		}
	}

	// 관리자 차트 - 월별 앱 출시 건수 차트
	@GetMapping("/api/admin/chart/monthlycount")
	public ResponseEntity<Map<String, Object>> monthlyOpenAppByCount() throws Exception {
		Map<String, Object> result = new HashMap<>();

		List<ChartDto> list = adminService.monthlyOpenAppByCount();
		result.put("list", list);

		if (list != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}

	// 관리자 차트 - 다운로드 카운트 랭킹순 앱 리스트
	@GetMapping("/api/admin/chart/rankcount")
	public ResponseEntity<List<ImageDto>> openAppRankByCount() throws Exception {
		List<ImageDto> list = adminService.openAppRankByCount();

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 관리자 차트 - 사용자 수 통계
	@GetMapping("/api/admin/chart/countalluseranddev")
	public ResponseEntity<ChartDto> countAllUserAndDev() {
		try {
			int userCount = adminService.countAllUser();
			int devCount = adminService.countAllDev();

			ChartDto dto = new ChartDto();
			dto.setCountAllUser(userCount);
			dto.setCountAllDev(devCount);

			return ResponseEntity.ok().body(dto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 전체 앱 다운로드 (월별) 차트
	@GetMapping("/api/admin/chart/totalappcount")
	public ResponseEntity<Map<String, Object>> totalAppDownload() throws Exception {
		Map<String, Object> result = new HashMap<>();

		List<ChartDto> list = adminService.totalAppDownload();
		result.put("totalCount", list);

		if (list != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}

	}

	// 앱별 다운로드 (월별) 차트
	@GetMapping("/api/admin/chart/count/{imageIdx}")
	public ResponseEntity<Map<String, Object>> appDownload(@PathVariable("imageIdx") int imageIdx) throws Exception {
		Map<String, Object> result = new HashMap<>();

		List<ChartDto> list = adminService.appDownload(imageIdx);
		result.put("count", list);

		if (list != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}

	// 관리자 차트 - 전체 누적 카운트
	@GetMapping("/api/admin/chart/totalcount")
	public ResponseEntity<List<ChartDto>> totalCount() throws Exception {
		List<ChartDto> list = adminService.totalCount();

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

}
