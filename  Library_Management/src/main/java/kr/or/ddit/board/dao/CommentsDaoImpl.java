package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.CommentsVo;

public class CommentsDaoImpl extends MybatisDao implements ICommentsDao {
	private static CommentsDaoImpl instance;

	private CommentsDaoImpl() {

	}

	public static CommentsDaoImpl getInstance() {
		if (instance == null) {
			instance = new CommentsDaoImpl();
		}
		return instance;
	}

	// 댓글 목록 조회
	@Override
	public List<CommentsVo> selectCommentsList(int boardNo) {
		List<CommentsVo> comments = selectList("comments.selectCommentList", boardNo);
		 
		// 각 댓글의 대댓글 조회
        for(CommentsVo comment : comments) {
            List<CommentsVo> cm2 = selectList("comments.selectCm2List", comment.getCmNo());
            comment.setCm2List(cm2);
        }
        
        return comments;
	}
	
	// 댓글 작성
	@Override
	public int insertComments(CommentsVo comment) {
		return insert("comments.insertComments", comment);
	}
	
	// 대댓글 작성
	@Override
	public int insertReplyComment(CommentsVo comment) {
		return insert("comments.insertReplyComment", comment);
	}
	
	// 댓글 수정
	@Override
	public int updateComments(CommentsVo comment) {
		return update("comments.updateComments", comment);
	}
	
	// 댓글 삭제 (논리삭제)
	@Override
	public int deleteComments(int cmNo) {
		return update("comments.deleteComment", cmNo);
	}
	
	// 특정 댓글 조회 (삭제 업데이트용)
	@Override
	public CommentsVo selectComment(int cmNo) {
		return selectOne("comments.selectComment",cmNo);
	}
		
	// 댓글 수 조회
	@Override
	public int getCommentCount(int boardNo) {
		return selectOne("comments.getCommentCount", boardNo);
	}

}
