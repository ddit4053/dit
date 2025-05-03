package kr.or.ddit.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.board.dao.BoardDaoImpl;
import kr.or.ddit.board.dao.CommentsDaoImpl;
import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.dao.ICommentsDao;
import kr.or.ddit.vo.BookBoardCodeVo;
import kr.or.ddit.vo.BookBoardVo;
import kr.or.ddit.vo.CommentsVo;
import kr.or.ddit.vo.PagingVo;

public class BoardServiceImpl implements IBoardService {
	
	private static BoardServiceImpl instance;

	private BoardServiceImpl() {

	}

	public static BoardServiceImpl getInstance() {
		if (instance == null) {
			instance = new BoardServiceImpl();
		}
		return instance;
	}

	IBoardDao boardDao = BoardDaoImpl.getInstance();
	ICommentsDao commentsDao = CommentsDaoImpl.getInstance();
	
	// 통합된 게시글 목록 조회 - 검색, 페이징, 정렬 모두 처리
    @Override
    public Map<String, Object> selectBoardList(Map<String, Object> params) {
        // 파라미터 처리
        String searchType = (String) params.get("searchType");
        String searchKeyword = (String) params.get("searchKeyword");
        String sortType = (String) params.get("sortType");
        Integer codeNo = params.get("codeNo") != null
        		? Integer.parseInt(params.get("codeNo").toString()) : null;
        String writer = (String) params.get("writer");  // 작성자 검색 추가
        
        int currentPage = params.get("currentPage") != null 
                ? Integer.parseInt(params.get("currentPage").toString()) : 1;
        int pageSize = params.get("pageSize") != null
                ? Integer.parseInt(params.get("pageSize").toString()) : 10;    
        
        // 검색 조건 설정
        Map<String, Object> searchParams = new HashMap<>();
        if(searchType != null && searchKeyword != null) {
            searchParams.put("searchType", searchType);
            searchParams.put("searchKeyword", searchKeyword);
        }
        if(codeNo != null && codeNo > 0) searchParams.put("codeNo", codeNo);
        if(writer != null) searchParams.put("writer", writer);  // 작성자 조건 추가
        
        // 전체 게시글 수 조회
        int totalCount = boardDao.getTotalBoardCount(searchParams);
        
        // 페이징 계산
        PagingVo paging = new PagingVo(currentPage, pageSize, totalCount);
        
        // 쿼리 파라미터 설정
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(searchParams);
        queryParams.put("startRow", paging.getStartRow());
        queryParams.put("endRow", paging.getEndRow());
        queryParams.put("sortType", sortType);
        
        // 게시글 목록 조회 (통합 메서드 사용)
        List<BookBoardVo> boardList = boardDao.selectBoardList(queryParams);
        
        // 첫 페이지 상단 공지사항 조회
        List<BookBoardVo> noticeList = null;
        if(currentPage == 1 && codeNo != 4) {
            noticeList = boardDao.selectNoticeList();
        }
        
        // 결과 반환
        Map<String, Object> result = new HashMap<>();
        result.put("boardList", boardList);
        result.put("noticeList", noticeList);
        result.put("paging", paging);
        
        return result;
    }
    
    // 게시판 목록 조회
    @Override
	public List<BookBoardCodeVo> getCodeList() {
		return boardDao.selectCodeList();
	}
    
    // 게시글 상세 조회
    @Override
    public BookBoardVo selectBoardDetail(int boardNo) {
        // 조회수 증가 후 상세 조회
        boardDao.updateViewCount(boardNo);
        
        // 게시글 조회
        BookBoardVo board = boardDao.selectBoardDetail(boardNo);
        
        // 댓글 목록 조회
        List<CommentsVo> comments = commentsDao.selectCommentsList(boardNo);
        board.setComments(comments);
        
        return board;
    }
    
    // 조회 수 증가
    @Override
    public int updateViewCount(int boardNo) {
        return boardDao.updateViewCount(boardNo);
    }
    
   
    
    // 총 게시글 수 조회
    @Override
    public int getTotalBoardCount(Map<String, Object> params) {
        return boardDao.getTotalBoardCount(params);
    }
    
    // 게시글 등록
    @Override
    public int insertBoard(BookBoardVo board) {
        return boardDao.insertBoard(board);
    }
    
    // 파일 첨부 없이 게시글 등록 시 파일 그룹 번호 수정
    @Override
    public int updateBoardFileGroup(int boardNo, Integer fileGroupNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("boardNo", boardNo);
        params.put("fileGroupNum", fileGroupNum);
        return boardDao.updateBoardFileGroup(params);
    }

    @Override
    public int updateBoard(BookBoardVo board) {
        return boardDao.updateBoard(board);
    }

    @Override
    public int deleteBoard(int boardNo) {
        return boardDao.deleteBoard(boardNo);
    }
    
    
    // 파일 그룹 번호 사용 여부 조회
 	@Override
 	public boolean isFileGroupInUser(int fileGroupNum) {
 		return boardDao.isFileGroupInUse(fileGroupNum);
 	}
    
    
    // 단일 댓글 조회
    @Override
    public CommentsVo selectComment(int cmNo) {
    	return commentsDao.selectComment(cmNo);
    }
    
    // 댓글 목록 조회
	@Override
	public List<CommentsVo> selectCommentsList(int boardNo) {
		return commentsDao.selectCommentsList(boardNo);
	}
	
	// 댓글 작성
	@Override
	public int insertComment(CommentsVo comment) {
		return commentsDao.insertComments(comment);
	}
	
	// 대댓글 작성
	@Override
	public int insertReplyComment(CommentsVo comment) {
		return commentsDao.insertReplyComment(comment);
	}
	
	// 댓글 수정
	@Override
	public int updateComment(CommentsVo comment) {
		return commentsDao.updateComments(comment);
	}
	
	// 댓글 삭제
	@Override
	public int deleteComment(int cmNo) {
		CommentsVo comment = commentsDao.selectComment(cmNo);
		
		if(comment != null) {
			int result = commentsDao.deleteComments(cmNo);
			
			return result;
		}
		return 0;
	}

}
