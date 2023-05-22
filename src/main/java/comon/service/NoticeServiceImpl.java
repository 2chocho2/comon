package comon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comon.dto.NoticeCategoryDto;
import comon.dto.NoticeDto;
import comon.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	// 검색 조건과 일치하는 게시물의 개수 조회
	@Override
	public int selectNoticeListCount() throws Exception {
		return noticeMapper.selectNoticeListCount();
	}

	// 검색 조건과 일치하는 게시물 중 offset부터 10개를 조회
	@Override
	public List<NoticeDto> selectNoticeListPage(int offset) throws Exception {
		return noticeMapper.selectNoticeListPage(offset);
	}

	// 게시물 목록 조회
	@Override
	public List<NoticeDto> selectNoticeList() throws Exception {
		return noticeMapper.selectNoticeList();
	}

	// 카테고리 선택
	@Override
	public List<NoticeCategoryDto> selectCategory() throws Exception {
		return noticeMapper.selectCategory();
	}

	// 선택한 카테고리 게시물 목록 출력
	@Override
	public List<NoticeDto> selectCategoryList(int noticeCategoryIdx) throws Exception {
		return noticeMapper.selectCategoryList(noticeCategoryIdx);
	}

	// 게시물 상세 조회
	@Override
	public NoticeDto selectNoticeDetail(int noticeIdx) throws Exception {
		return noticeMapper.selectNoticeDetail(noticeIdx);
	}

	// 게시물 작성
	@Override
	public void writeNotice(NoticeDto noticeDto) throws Exception {
		noticeMapper.writeNotice(noticeDto);
	}

	// 게시물 수정
	@Override
	public void updateNotice(NoticeDto noticeDto) throws Exception {
		noticeMapper.updateNotice(noticeDto);
	}

	// 게시물 삭제
	@Override
	public void deleteNotice(int noticeIdx) throws Exception {
		noticeMapper.deleteNotice(noticeIdx);
	}

	// 메인 푸터에 노출될 2개 공지
	public List<NoticeDto> selectNoticeForFooter() throws Exception {
		return noticeMapper.selectNoticeForFooter();
	}

}
