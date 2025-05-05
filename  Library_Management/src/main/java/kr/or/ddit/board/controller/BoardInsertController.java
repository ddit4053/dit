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
import jakarta.servlet.http.Part;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.FileServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.service.IFileService;
import kr.or.ddit.vo.BookBoardCodeVo;
import kr.or.ddit.vo.BookBoardVo;
import kr.or.ddit.vo.File_StorageVo;
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
            
            // 공지사항(codeNo=4)인 경우 관리자 권한 확인
            if (codeNo == 4) {
                String userRole = (String) req.getSession().getAttribute("role");
                if (!"ADMIN".equals(userRole)) {
                    throw new IllegalStateException("공지사항은 관리자만 작성할 수 있습니다.");
                }
            }
            
            // 뷰로 전달할 데이터 설정
            req.setAttribute("codeNo", codeNo);
            req.setAttribute("codeList", codeList);
            req.setAttribute("mode", "write");
            req.setAttribute("breadcrumbTitle", "작성하기");
            
            // 에디터 페이지로 포워딩
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
            
            // 유효성 검사
            if (title == null || title.trim().isEmpty() || 
                content == null || content.trim().isEmpty() ||
                codeNoStr == null || codeNoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("필수 항목이 누락되었습니다.");
            }
            
            int codeNo = Integer.parseInt(codeNoStr);
            
            // 공지사항(codeNo=4)인 경우 관리자 권한 확인
            if (codeNo == 4) {
                String userRole = loginUser.getRole();;
                if (!"ADMIN".equals(userRole)) {
                    throw new IllegalStateException("공지사항은 관리자만 작성할 수 있습니다.");
                }
            }
            
            
            // 게시글 정보 설정
            BookBoardVo board = new BookBoardVo();
            board.setTitle(title);
            board.setContent(content);
            board.setUserNo(loginUser.getUserNo());
            board.setCodeNo(codeNo);
            
            // 게시글 저장 (파일 그룹번호 없이)
            board.setFileGroupNum(null);
            int result = boardService.insertBoard(board);
            
            if (result <= 0) {
            		throw new RuntimeException("게시글 등록 실패");
            }
            
            // 게시글 저장 성공 후, 게시글 번호와 동일한 파일그룹번호 생성
            int boardNo = board.getBoardNo();
            int fileGroupNum = fileService.createFileGroup(boardNo, codeNo);
            
            // 게시글의 파일그룹번호 업데이트
            board.setFileGroupNum(fileGroupNum);
            boardService.updateBoardFileGroup(boardNo, fileGroupNum);
            
            
            // 파일이 있는지 확인 및 업로드
            boolean hasFiles = false;
            for (Part part : req.getParts()) {
                String fileName = part.getSubmittedFileName();
                if (fileName != null && !fileName.isEmpty()) {
                    hasFiles = true;
                    break;
                }
            }
            
            // 파일이 있는 경우 업로드 처리
            List<File_StorageVo> uploadedFiles = null;
            if (hasFiles) {
            		uploadedFiles = fileService.uploadFiles(req, "BOARD", boardNo);
            }
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "게시글이 등록되었습니다.");
            response.put("boardNo", boardNo);
            
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
        // 관리자 권한 확인
        boolean isAdmin = false;
        if(loginUser != null && "ADMIN".equals(loginUser.getRole())) {
        		isAdmin = true;
        }
        req.setAttribute("isAdmin", isAdmin);
        
        return loginUser;
    }

    // JSON 응답 전송 메서드
    private void sendJsonResponse(HttpServletResponse resp, Map<String, Object> data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(data));
        }
    }

    // 에러 응답 전송 메서드
    private void sendErrorResponse(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        
        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(errorResponse));
        }
    }
}