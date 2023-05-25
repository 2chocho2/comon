package comon.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import comon.dto.ImageDto;
import comon.dto.ImageReviewDto;
import comon.dto.ImageUserDto;
import comon.dto.PortDto;
import comon.service.ComonMainService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ComonMainApiController {

	private final String UPLOAD_YAMLFILE_PATH = "c:\\comon\\app\\yamlfile\\";
	private final String UPLOAD_USER_YAMLFILE_PATH = "c:\\comon\\app\\useryamlfile\\";
	
	@Autowired
	ComonMainService comonMainService;

	PortDto portDto;

	// 메인에 랜덤으로 3개 앱 목록 출력
	// 다운로드 카운트가 가장 높은 3개 출력
	@GetMapping("/api/main")
	public ResponseEntity<Map<String, Object>> openRecommendApp() throws Exception {
		Map<String, Object> result = new HashMap<>();

		List<ImageDto> list1 = comonMainService.openRecommendApp();
		List<ImageDto> list2 = comonMainService.openAppRanking();

		result.put("list1", list1);
		result.put("list2", list2);

		if (list1 != null && list2 != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
	}

	// 전체 앱 리스트 출력
	@GetMapping("/api/user/applist")
	public ResponseEntity<List<ImageDto>> openAllAppList() throws Exception {
		List<ImageDto> list = comonMainService.openAllAppList();

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 카테고리별 앱 리스트 출력
	@GetMapping("/api/user/applist/{categoryIdx}")
	public ResponseEntity<List<ImageDto>> openAppLisyByCategory(@PathVariable("categoryIdx") int categoryIdx)
			throws Exception {
		List<ImageDto> list = comonMainService.openAppListByCategory(categoryIdx);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 다운로드 카운트 높은 순으로 앱 리스트 출력
	@GetMapping("/api/user/applist/count")
	public ResponseEntity<List<ImageDto>> openAppListByCount() throws Exception {
		List<ImageDto> list = comonMainService.openAppListByCount();

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 출시일 순으로 앱 리스트 출력
	@GetMapping("/api/user/applist/registdt")
	public ResponseEntity<List<ImageDto>> openAppListByRegistDt() throws Exception {
		List<ImageDto> list = comonMainService.openAppListByRegistDt();

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 앱 이름 가나다 순으로 목록 출력
	@GetMapping("/api/user/applist/order")
	public ResponseEntity<List<ImageDto>> openAppListByName() throws Exception {
		List<ImageDto> list = comonMainService.openAppListByName();

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 앱 상세 페이지 조회
	@GetMapping("/api/user/applist/detail/{imageIdx}")
	public ResponseEntity<Map<String, Object>> openAppDetail(@PathVariable("imageIdx") int imageIdx) throws Exception {
		Map<String, Object> result = new HashMap<>();

		ImageDto imageDto = comonMainService.openAppDetail(imageIdx);
		Double reviewAvg = comonMainService.openReviewAverage(imageIdx);
		List<ImageReviewDto> list = comonMainService.openReviewList(imageIdx);

		result.put("imageDto", imageDto);
		result.put("reviewAvg", reviewAvg);
		result.put("reviewList", list);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	// 앱 다운로드
	@PostMapping("/api/user/downloadapp")
	public ResponseEntity<Map<String, Integer>> downloadApp(@RequestBody ImageUserDto imageUserDto, HttpSession session)
			throws Exception {

		Map<String, Integer> list = new HashMap<>();
		
		String yamlFile = comonMainService.selectYamlFile(imageUserDto.getImageIdx());
		String yamlFilePath = UPLOAD_YAMLFILE_PATH + yamlFile;
		long randomNum = comonMainService.selectRandomNum(imageUserDto.getImageIdx());
		int exitCode = 0;
		Process process = null;
		
		final String command = String.format("docker-compose -f \"%s%s\\%s\" build --no-cache ", 
								UPLOAD_USER_YAMLFILE_PATH, imageUserDto.getUserId(),
								yamlFile.replace(".yaml", "-" + imageUserDto.getUserId() + ".yaml"));
		
//		Files.createDirectories(Paths.get(String.format("C:\\comon\\%s\\%s", imageUserDto.getUserId(), randomNum)));
		
		
		// download 전 download 받은 적이 있는지 확인
		int userIdx = comonMainService.selectUserIdx(imageUserDto.getUserId());
		imageUserDto.setUserIdx(userIdx);

		String deleteYn = comonMainService.checkDownload(imageUserDto);
		// case1. 
		if(deleteYn == null) {
			
			Files.createDirectories(Paths.get(String.format("C:\\comon\\%s\\%s", imageUserDto.getUserId(), randomNum)));
			Files.createDirectories(Paths.get(String.format("C:\\comon\\app\\useryamlfile\\%s", imageUserDto.getUserId())));

			try {
				// YAML 파일 읽기
				InputStream inputStream = new FileInputStream(new File(yamlFilePath));
				Yaml yaml = new Yaml(new Constructor(Map.class));
				Map<String, Object> data = yaml.load(inputStream);

				// 변수 대체1. 사용자 아이디 설정
				updateValue(data, "OUR_APP_USER_ID", "" + imageUserDto.getUserId());
				
				// 변수 대체2. 랜덤 번호 설정
				updateValue(data, "STATIC_NUM", "" + randomNum);

				// 변수 대체2. 스프링부트 포트 설정
				PortDto portDto = new PortDto();

				comonMainService.insertPort(portDto);
				int springbootPort = portDto.getPortNumber();
				imageUserDto.setSpringbootPort(springbootPort);

				updateValue(data, "SPRINGBOOT_PORT", "" + springbootPort);

				// 변수 대체3. 리액트 포트 설정
				PortDto portDto2 = new PortDto();

				comonMainService.insertPort(portDto2);
				int reactPort = portDto2.getPortNumber();
				imageUserDto.setReactPort(reactPort);

				updateValue(data, "REACT_PORT", "" + reactPort);

				String newUserYamlFilePath = UPLOAD_USER_YAMLFILE_PATH + imageUserDto.getUserId() + '\\'
						+ yamlFile.replace(".yaml", "-" + imageUserDto.getUserId() + ".yaml");
				FileWriter writer = new FileWriter(newUserYamlFilePath);
				DumperOptions options = new DumperOptions();

				options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

				yaml = new Yaml(options);
				yaml.dump(data, writer);

			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			
			process = Runtime.getRuntime().exec(command);
			
			log.debug("**************** command  = " + command);
			
			int downloadCount = comonMainService.downloadApp(imageUserDto);
			list.put("exitCode", exitCode);
			list.put("downloadCount", downloadCount);
			
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
		
		// case2. download 내역이 있으나 삭제한 경우
		else if (deleteYn.equals("Y")) {
			int updateCount = comonMainService.toggleDeleteYn(imageUserDto);

			Files.createDirectories(Paths.get(String.format("C:\\comon\\%s\\%s", imageUserDto.getUserId(), randomNum)));
			Files.createDirectories(Paths.get(String.format("C:\\comon\\app\\useryamlfile\\%s", imageUserDto.getUserId())));
			
			process = Runtime.getRuntime().exec(command);
			
			list.put("exitCode", exitCode);
			list.put("updateCount", updateCount);
			
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
		
		// case3. download 내역이 있고 삭제하지 않은 경우
		else{
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	}

	// 리뷰 점수 평균
	@GetMapping("/api/user/applist/average/{imageIdx}")
	public ResponseEntity<Double> openReviewAverage(@PathVariable("imageIdx") int imageIdx) throws Exception {
		Double reviewAvg = comonMainService.openReviewAverage(imageIdx);

		return ResponseEntity.status(HttpStatus.OK).body(reviewAvg);
	}
	
	// 리뷰 별점 점수별 비율 출력
	@GetMapping("/api/user/applist/score/{imageIdx}")
	public ResponseEntity<Map<String, Integer>> selectScoreRatio(@PathVariable("imageIdx") int imageIdx) throws Exception {
		Map<String, Integer> result = new HashMap<>();
		
		int ratio1 = comonMainService.selectScoreRatio(1, imageIdx);
		int ratio2 = comonMainService.selectScoreRatio(2, imageIdx);
		int ratio3 = comonMainService.selectScoreRatio(3, imageIdx);
		int ratio4 = comonMainService.selectScoreRatio(4, imageIdx);
		int ratio5 = comonMainService.selectScoreRatio(5, imageIdx);
		
		result.put("ratio1", ratio1);
		result.put("ratio2", ratio2);
		result.put("ratio3", ratio3);
		result.put("ratio4", ratio4);
		result.put("ratio5", ratio5);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	// 리뷰 목록 출력
	@GetMapping("/api/user/reviewlist/{imageIdx}")
	public ResponseEntity<List<ImageReviewDto>> openReviewList(@PathVariable("imageIdx") int imageIdx)
			throws Exception {
		List<ImageReviewDto> list = comonMainService.openReviewList(imageIdx);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 변수 대체 메서드
	private void updateValue(Map<String, Object> data, String orgString, String newString) {
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			String itemKey = entry.getKey();
			Object itemValue = entry.getValue();
			if (itemValue instanceof Map) {
				updateValue((Map<String, Object>) itemValue, orgString, newString);
			}
			if (itemValue instanceof List) {
				List<Object> newItemValue = new ArrayList<>();
				Iterator<Object> it = ((List) itemValue).iterator();
				while (it.hasNext()) {
					Object s = it.next();
					if (s instanceof String) {
						s = ((String) s).replaceAll(orgString, newString);
					}
					newItemValue.add(s);
				}
				entry.setValue(newItemValue);
			} else {
				String strValue = "" + itemValue;
				String value = "" + itemValue;
				value = value.replaceAll(orgString, newString);

				log.debug(value, strValue);
				if (!value.equals(strValue)) {
					entry.setValue(value);
				}
			}
		}
	}

}
