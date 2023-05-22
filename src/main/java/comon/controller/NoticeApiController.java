package comon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comon.dto.NoticeCategoryDto;
import comon.dto.NoticeDto;
import comon.service.NoticeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class NoticeApiController {

	@Autowired
	private NoticeService noticeService;
	private static final int pageSize = 10;

	// 전체 notice 리스트 출력
	@GetMapping("/api/notice")
	public ResponseEntity<Map<String, Object>> selectNoticeList(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage)
			throws Exception {

		int totalCount = noticeService.selectNoticeListCount();
		int pageCount = (int) Math.ceil(totalCount / (double)pageSize);

		List<NoticeDto> list = noticeService.selectNoticeListPage((currentPage - 1) * pageSize);

		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("totalCount", totalCount);
		result.put("pageCount", pageCount);
		result.put("currentPage", currentPage);

		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}
	
	// 카테고리 목록 조회
	@GetMapping("/api/notice/category")
	public ResponseEntity<List<NoticeCategoryDto>> selectCategory() throws Exception {
		List<NoticeCategoryDto> list = noticeService.selectCategory();
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
	
	
	// 카테고리별 게시글 출력
	@GetMapping("/api/notice/category/{noticeCategoryIdx}")
	public ResponseEntity<List<NoticeDto>> selectCategoryList(@PathVariable("noticeCategoryIdx") int noticeCategoryIdx)
			throws Exception {
		List<NoticeDto> list = noticeService.selectCategoryList(noticeCategoryIdx);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 게시글 상세조회
	@GetMapping("/api/notice/detail/{noticeIdx}")
	public ResponseEntity<NoticeDto> selectNoticeDetail(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
		NoticeDto noticeDto = noticeService.selectNoticeDetail(noticeIdx);
		if (noticeDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(noticeDto);
		}
	}

	// 게시글 작성
	@PostMapping("/api/notice/write")
	public ResponseEntity<String> writeNotice(@RequestBody NoticeDto noticeDto) throws Exception {
		try {
			noticeService.writeNotice(noticeDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록오류");
		}
		return ResponseEntity.status(HttpStatus.OK).body("정상처리");
	}

	// 게시글 수정
	@PutMapping("/api/notice/edit/{noticeIdx}")
	public ResponseEntity<Integer> updateNotice(@PathVariable("noticeIdx") int noticeIdx, @RequestBody NoticeDto noticeDto)
			throws Exception {
		try {
			noticeDto.setNoticeIdx(noticeIdx);
			noticeService.updateNotice(noticeDto);
			return ResponseEntity.status(HttpStatus.OK).body(1);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
		}
	}
	
	// 게시글 삭제
	@DeleteMapping("/api/notice/detail/{noticeIdx}")
	public ResponseEntity<Integer> deleteNotice(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
		try {
			noticeService.deleteNotice(noticeIdx);
			return ResponseEntity.status(HttpStatus.OK).body(1);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
		}
	}
	
	// 메인 푸터에 노출될 2개 공지
	@GetMapping("/api/notice/main")
	public ResponseEntity<List<NoticeDto>> selectNoticeForFooter() throws Exception {
		List<NoticeDto> list = noticeService.selectNoticeForFooter();
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}
