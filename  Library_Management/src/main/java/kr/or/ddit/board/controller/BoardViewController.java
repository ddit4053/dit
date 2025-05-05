package kr.or.ddit.board.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.BookBoardVo;
import kr.or.ddit.vo.BooksVo;
import kr.or.ddit.vo.UsersVo;

@WebServlet(urlPatterns = {
        "/community/*",
        "/support/*",
})
// 게시판 화면 라우팅
public class BoardViewController extends HttpServlet {
    
    IBoardService boardService = BoardServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String servletPath = req.getServletPath(); // /community 또는 /support를 가져옴
        String pathInfo = req.getPathInfo(); // /reviews, /discussions, /recommendations 등을 가져옴
        String contentPage = null;
        String forwardPage = null;
        String pageTitle = "";
        String pageDescription = "";
        
        // null 체크
        if(servletPath == null ||  pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Detail 페이지 처리 로직 추가
        if(pathInfo.endsWith("/Detail")) {
            // Detail 페이지 처리 로직
            try {
                int boardNo = Integer.parseInt(req.getParameter("boardNo"));
                BookBoardVo board = boardService.selectBoardDetail(boardNo);
                
        		// 세션에서 로그인 사용자 정보 확인
        		HttpSession session = req.getSession();
        		UsersVo loginUser = (UsersVo) session.getAttribute("user");
        		String userId = null;
        		int userNo = 0;
        		
        		if (loginUser != null) {
        			userId = loginUser.getUserId();
        			userNo = (int) session.getAttribute("userNo");
        		}
        		req.setAttribute("userId", userId);
        		req.setAttribute("userNo", userNo);
        		
                
    			// 로그인 사용자와 작성자 일치 여부 확인 (수정/삭제 권한)
    			boolean isAuthor = false;
    			if(loginUser != null && board.getUserNo() == userNo) {
    				isAuthor = true;
    			}
    			boolean isAdmin = false;
    			if(loginUser != null && "ADMIN".equals(loginUser.getRole())) {
    				isAdmin = true;
    			}
                
                contentPage = "/WEB-INF/view/boardDetail.jsp";
                forwardPage = "/WEB-INF/view/board.jsp";
                req.setAttribute("board", board);
                req.setAttribute("isAuthor", isAuthor);
                req.setAttribute("isAdmin", isAdmin);
                
                // 이전 페이지 정보 저장 (목록으로 돌아가기 용)
    			String prevPage = req.getHeader("referer");
    			req.setAttribute("prevPage", prevPage);
                
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        // 기존 게시판 목록 페이지 처리
        else if("/community".equals(servletPath)) {
            switch (pathInfo) {
            case "/reviews":
                contentPage = "/WEB-INF/view/boardList.jsp";
                forwardPage = "/WEB-INF/view/board.jsp";
                pageTitle = "독후감 게시판";
                pageDescription = "회원들이 읽은 책에 대한 독후감을 공유하는 공간입니다. 독서 경험과 생각을 자유롭게 나누어 보세요.";
                break;
                
            case "/discussions":
                contentPage = "/WEB-INF/view/boardList.jsp";
                forwardPage = "/WEB-INF/view/board.jsp";
                pageTitle = "토론 게시판";
                pageDescription = "다양한 독서 주제에 대해 토론하고 의견을 나누는 공간입니다. 활발한 의견 교환을 통해 새로운 시각을 발견해보세요.";
                break;    
                
            case "/recommendations":
                contentPage = "/WEB-INF/view/boardList.jsp";
                forwardPage = "/WEB-INF/view/board.jsp";
                pageTitle = "회원 도서 추천 게시판";
                pageDescription = "회원들이 추천하는 도서 정보를 공유하는 공간입니다. 좋은 책을 발견하고 다른 회원들과 함께 나누어 보세요.";
                break;
                
            default:
                if(!pathInfo.contains("/Detail")) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
            
        } else if ("/support".equals(servletPath)) {
            switch (pathInfo) {
                case "/notices":
                    contentPage = "/WEB-INF/view/boardList.jsp";
                    forwardPage = "/WEB-INF/view/board.jsp";
                    pageTitle = "공지사항 게시판";
                    pageDescription = "도서관의 중요 공지사항과 안내를 확인할 수 있는 공간입니다. 최신 소식과 변경사항을 확인하세요.";
                    break;
                    
                case "/events":
                    contentPage = "/WEB-INF/view/boardList.jsp";
                    forwardPage = "/WEB-INF/view/board.jsp";
                    pageTitle = "교육/행사 게시판";
                    pageDescription = "도서관에서 진행되는 다양한 교육 프로그램과 문화행사 정보를 안내하는 공간입니다. 참여하고 싶은 행사를 확인해보세요.";
                    break;
                    
                case "/faq":
                    contentPage = "/WEB-INF/view/users/users_faq.jsp";
                    forwardPage = "/WEB-INF/view/board.jsp";
                    pageTitle = "자주 묻는 질문";
                    pageDescription = "도서관 이용에 관한 자주 묻는 질문과 답변을 모아놓은 공간입니다.";
                    break;
                    
                case "/qa":
                    contentPage = "/WEB-INF/view/boardList.jsp";
                    forwardPage = "/WEB-INF/view/board.jsp";
                    pageTitle = "1:1 문의 게시판";
                    pageDescription = "도서관 이용에 관한 질문과 답변을 주고받는 공간입니다. 궁금한 점이나 건의사항을 자유롭게 문의해주세요.";
                    break;
                    
                default:
                    if(!pathInfo.contains("/Detail")) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
            }
                    
        } 
        
        if(contentPage != null) {
            // contentPage 설정
            req.setAttribute("contentPage", contentPage);
        }
        
        req.setAttribute("pageTitle", pageTitle);
        req.setAttribute("breadcrumbTitle", pageTitle);
        req.setAttribute("pageDescription", pageDescription);
        
        req.getServletContext().getRequestDispatcher(forwardPage).forward(req, resp);
    }    
}