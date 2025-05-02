package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.FileServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.service.IFileService;
import kr.or.ddit.vo.BookBoardCodeVo;
import kr.or.ddit.vo.BookBoardVo;
import kr.or.ddit.vo.UsersVo;

@WebServlet("/insert")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,      // 1MB
	    maxFileSize = 1024 * 1024 * 10,       // 10MB
	    maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class BoardInsertController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private IBoardService boardService = BoardServiceImpl.getInstance();
    private IFileService fileService = FileServiceImpl.getInstance();
    private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
	        // 로그인 확인
	        UsersVo loginUser = checkUserAuthority(req);
	        
	        // 게시판 코드 목록 조회
	        List<BookBoardCodeVo> codeList = boardService.getCodeList(); 
	        
	        // codeNo 파라미터 받기
	        String codeNoStr = req.getParameter("codeNo");
	        int codeNo = 1; // 기본값: 독후감 게시판
	        
	        try {
	            if (codeNoStr != null && !codeNoStr.trim().isEmpty()) {
	                codeNo = Integer.parseInt(codeNoStr);
	            }
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("잘못된 게시판 번호입니다.");
	        }
	        
	        // 뷰로 전달할 데이터 설정
	        req.setAttribute("codeNo", codeNo);
	        req.setAttribute("codeList", codeList);
	        
	        // 글쓰기 페이지로 포워딩
	        req.getRequestDispatcher("/WEB-INF/view/editor.jsp").forward(req, resp);
	        
	    } catch (IllegalStateException e) {
	        // 로그인이 필요한 경우 로그인 페이지로 리다이렉트
	        resp.sendRedirect(req.getContextPath() + "/user/login.do");
	    } catch (IllegalArgumentException e) {
	        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
	    }
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
	        // 로그인 확인
	        UsersVo loginUser = checkUserAuthority(req);
	        
	        // 요청 파라미터 수집
	        String title = req.getParameter("title");
	        String content = req.getParameter("content");
	        String codeNoStr = req.getParameter("codeNo");
	        String fileGroupNumStr = req.getParameter("fileGroupNum");
	        
	        // 유효성 검사
	        if (title == null || title.trim().isEmpty() || 
	            content == null || content.trim().isEmpty() ||
	            codeNoStr == null || codeNoStr.trim().isEmpty()) {
	            throw new IllegalArgumentException("필수 항목이 누락되었습니다.");
	        }
	        
	        int codeNo = Integer.parseInt(codeNoStr);
	        
	        // 파일 그룹 번호 처리
	        int fileGroupNum = fileService.createFileGroup();
	        
	        
	        // 게시글 정보 설정
	        BookBoardVo board = new BookBoardVo();
	        board.setTitle(title);
	        board.setContent(content);
	        board.setUserNo(loginUser.getUserNo());
	        board.setCodeNo(codeNo);
	        board.setFileGroupNum(fileGroupNum);
	        
	        
	        // 게시글 저장
	        int result = boardService.insertBoard(board);
	        
	        // 응답 생성
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", result > 0);
	        if (result > 0) {
	            response.put("message", "게시글이 등록되었습니다.");
	            response.put("boardNo", board.getBoardNo());
	        } else {
	            response.put("message", "게시글 등록에 실패했습니다.");
	        }
	        
	        sendJsonResponse(resp, response);
	    } catch (NumberFormatException e) {
	        sendErrorResponse(resp, "잘못된 형식의 데이터입니다.");
	    } catch (IllegalStateException | IllegalArgumentException e) {
	        sendErrorResponse(resp, e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        sendErrorResponse(resp, "서버 오류가 발생했습니다: " + e.getMessage());
	    }
	}

	// 사용자 로그인 확인 메서드
	private UsersVo checkUserAuthority(HttpServletRequest req) throws IllegalStateException {
	    HttpSession session = req.getSession();
	    UsersVo loginUser = (UsersVo) session.getAttribute("user");
	    
	    if (loginUser == null) {
	        throw new IllegalStateException("로그인이 필요합니다.");
	    }
	    
	    return loginUser;
	}

	// JSON 응답 전송 메서드
	private void sendJsonResponse(HttpServletResponse resp, Map<String, Object> data) throws IOException {
	    resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    
	    PrintWriter out = resp.getWriter();
	    out.print(gson.toJson(data));
	    out.flush();
	}

	// 에러 응답 전송 메서드
	private void sendErrorResponse(HttpServletResponse resp, String message) throws IOException {
	    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    
	    Map<String, Object> errorResponse = new HashMap<>();
	    errorResponse.put("success", false);
	    errorResponse.put("message", message);
	    
	    PrintWriter out = resp.getWriter();
	    out.print(gson.toJson(errorResponse));
	    out.flush();
	}
	
}
