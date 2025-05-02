package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.BookBoardCodeVo;
import kr.or.ddit.vo.BookBoardVo;

public class BoardDaoImpl extends MybatisDao implements IBoardDao {
	
	private static BoardDaoImpl instance;

	private BoardDaoImpl() {

	}

	public static BoardDaoImpl getInstance() {
		if (instance == null) {
			instance = new BoardDaoImpl();
		}
		return instance;
	}

	// 통합된 게시글 목록 조회 - 검색, 페이징, 정렬 모두 처리
    @Override
    public List<BookBoardVo> selectBoardList(Map<String, Object> params) {
        // 모든 검색 조건, 정렬, 페이징을 파라미터로 받아 처리
        // 일반 게시글, 작성자별 게시글, 정렬된 게시글 모두 처리 가능
        return selectList("board.selectBoardList", params);
    }
    
    @Override
    public List<BookBoardCodeVo> selectCodeList() {
    	return selectList("board.selectCodeList");
    }
    
    // 게시글 상세 조회
    @Override
    public BookBoardVo selectBoardDetail(int boardNo) {
    	return selectOne("board.selectBoardDetail", boardNo);
    }
    
    // 공지사항만 별도로 조회 (상단 고정용)
    @Override
    public List<BookBoardVo> selectNoticeList() {
        return selectList("board.selectNoticeList");
    }

    // 조회수 증가
    @Override
    public int updateViewCount(int boardNo) {
    	return update("board.updateViewCount", boardNo);
    }
    
    // 게시글 수 조회
    @Override
    public int getTotalBoardCount(Map<String, Object> params) {
    	return selectOne("board.getTotalBoardCount", params);
    }
    
    @Override
    public int insertBoard(BookBoardVo board) {
        return insert("board.insertBoard", board);
    }

    @Override
    public int updateBoard(BookBoardVo board) {
        return update("board.updateBoard", board);
    }

    @Override
    public int deleteBoard(int boardNo) {
        return update("board.deleteBoard", boardNo);
    }
    

}
