package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.vo.BookBoardCodeVo;
import kr.or.ddit.vo.BookBoardVo;
import kr.or.ddit.vo.UsersVo;

@WebServlet("/admin/board/*")
public class AdminBoardViewController extends HttpServlet {
	
	IBoardService boardService = BoardServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo(); // '/*' 부분을 가져옴
		String contentPage = null;
		String forwardPage = null;
		boolean isAdmin = false;
		
		
		
		// 게시판 코드 목록 조회
        List<BookBoardCodeVo> codeList = boardService.getCodeList(); 
        
        // 세션에서 로그인 사용자 정보 확인
        HttpSession session = req.getSession();
        UsersVo loginUser = (UsersVo) session.getAttribute("user");
        String userId = null;
        int userNo = 0;
        
        if (loginUser != null) {
	        userId = loginUser.getUserId();
	        userNo = (int) session.getAttribute("userNo");
	        if("ADMIN".equals(loginUser.getRole())) {
	            isAdmin = true;
	        }
	    }
        req.setAttribute("userId", userId);
        req.setAttribute("userNo", userNo);
        req.setAttribute("isAdmin", isAdmin);
		
		// 기본 경로 처리
		if(pathInfo == null || "/".equals(pathInfo)) {
			// 게시판 현황 페이지로 이동 (추후 랜딩페이지 수정) 
	        contentPage = "/WEB-INF/view/boardList.jsp";
	        forwardPage = "/WEB-INF/view/board.jsp";
	        
		} else if("/list".equals(pathInfo)) {
			// 게시판 목록 페이지로 이동
			contentPage = "/WEB-INF/view/boardList.jsp";
	        forwardPage = "/WEB-INF/view/board.jsp";
	        
		} else if("/Detail".equals(pathInfo)) {
			// 게시글 상세보기 처리
			try {
				int boardNo = Integer.parseInt(req.getParameter("boardNo"));
                BookBoardVo board = boardService.selectBoardDetail(boardNo);
               
                // 로그인 사용자와 작성자 일치 여부 확인 (수정/삭제 권한)
                boolean isAuthor = false;
                if(loginUser != null && board.getUserNo() == userNo) {
                	isAuthor = true;
                }
                
                contentPage = "/WEB-INF/view/boardDetail.jsp";
                forwardPage = "/WEB-INF/view/board.jsp";
                req.setAttribute("board", board);
                req.setAttribute("isAuthor", isAuthor);
                
                // 이전 페이지 정보 저장 (목록으로 돌아가기 용)
                String prevPage = req.getHeader("referer");
                req.setAttribute("prevPage", prevPage);
                
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	        return;
		}
		
		if (contentPage != null && forwardPage != null) {
			req.setAttribute("contentPage", contentPage);
			req.setAttribute("codeList", codeList);
			req.getRequestDispatcher(forwardPage).forward(req, resp);
		}
		
	}
}

