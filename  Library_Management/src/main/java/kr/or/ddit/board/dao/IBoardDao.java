package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookBoardCodeVo;
import kr.or.ddit.vo.BookBoardVo;

public interface IBoardDao {
	
	// 통합된 게시글 목록 조회
    List<BookBoardVo> selectBoardList(Map<String, Object> params);
    
    // 게시판 목록 조회
    List<BookBoardCodeVo> selectCodeList();
    
    // 게시글 상세 조회
    BookBoardVo selectBoardDetail(int boardNo);
    
    // 공지사항 목록 조회
    List<BookBoardVo> selectNoticeList();
    
    // 파일 그룹 번호 사용 여부 조회
    boolean isFileGroupInUse(int fileGroupNum);
    
    int updateBoardFileGroup(Map<String, Object> params);
    
    // 게시글 등록
    int insertBoard(BookBoardVo board);
    
    // 게시글 수정
    int updateBoard(BookBoardVo board);
    
    // 게시글 삭제
    int deleteBoard(int boardNo);
    
    // 조회수 증가
    int updateViewCount(int boardNo);
    
    // 전체 게시글 수 조회
    int getTotalBoardCount(Map<String, Object> params);
}
