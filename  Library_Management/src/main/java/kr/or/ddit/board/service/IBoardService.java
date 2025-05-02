package kr.or.ddit.board.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookBoardCodeVo;
import kr.or.ddit.vo.BookBoardVo;
import kr.or.ddit.vo.CommentsVo;

public interface IBoardService {
	// 인터페이스에 선언된 메서드들은 암시적으로 public abstract 이므로 명기하지 않음.
	
	// 통합된 게시글 목록 조회 (검색, 페이징, 정렬 포함)
    Map<String, Object> selectBoardList(Map<String, Object> params);
    
    // 게시판 목록 조회
    List<BookBoardCodeVo> getCodeList();
    
    // 게시글 상세 조회
    BookBoardVo selectBoardDetail(int boardNo);
    
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
    
    
    // 단일 댓글 조회
    CommentsVo selectComment(int cmNo);
    
    
    // 댓글 목록 조회
    List<CommentsVo> selectCommentsList(int boardNo);
    
    // 기본 댓글 작성
    int insertComment(CommentsVo comment);
    
    // 대댓글 작성
    int insertReplyComment(CommentsVo comment);
    
    
    // 댓글 수정
    int updateComment(CommentsVo comment);
    
    // 댓글 삭제
    int deleteComment(int cmNo);
}
