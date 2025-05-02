package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.vo.CommentsVo;

public interface ICommentsDao {
	
	// 댓글 목록 조회
	List<CommentsVo> selectCommentsList(int boardno);
	
	// 댓글 한 개 조회(삭제 업데이트용)
	CommentsVo selectComment (int cmNo);
	
	// 기본 댓글 작성
	int insertComments(CommentsVo comment);
	
	// 대댓글 작성
    int insertReplyComment(CommentsVo comment);
	
	// 댓글 수정
	int updateComments(CommentsVo comments);
	
	// 댓글 삭제 (논리삭제)
	int deleteComments(int cmNo);
	
	// 댓글 수 조회
	int getCommentCount(int boardNo);
	
}
