package comon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import comon.dto.NoticeCategoryDto;
import comon.dto.NoticeDto;

@Service
public interface NoticeService {

	// 검색 조건과 일치하는 게시물의 개수 조회
	int selectNoticeListCount() throws Exception;

	// 검색 조건과 일치하는 게시물 중 offset부터 10개를 조회
	List<NoticeDto> selectNoticeListPage(int offset) throws Exception;

	// 게시물 목록 조회
	public List<NoticeDto> selectNoticeList() throws Exception;

	// 카테고리 선택
	List<NoticeCategoryDto> selectCategory() throws Exception;

	// 선택한 카테고리 게시물 목록 출력
	List<NoticeDto> selectCategoryList(int noticeCategoryIdx) throws Exception;

	// 게시물 상세 조회
	public NoticeDto selectNoticeDetail(int noticeIdx) throws Exception;

	// 게시물 작성
	public void writeNotice(NoticeDto noticeDto) throws Exception;

	// 게시물 수정
	public void updateNotice(NoticeDto noticeDto) throws Exception;

	// 게시물 삭제
	public void deleteNotice(int noticeIdx) throws Exception;

	// 메인 푸터에 노출될 2개 공지
	public List<NoticeDto> selectNoticeForFooter() throws Exception;

}
