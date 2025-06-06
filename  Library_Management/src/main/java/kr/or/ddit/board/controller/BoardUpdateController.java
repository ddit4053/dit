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
import kr.or.ddit.vo.FileGroupVo;
import kr.or.ddit.vo.File_StorageVo;
import kr.or.ddit.vo.UsersVo;

@WebServlet("/update")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class BoardUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IBoardService boardService = BoardServiceImpl.getInstance();
    private IFileService fileService = FileServiceImpl.getInstance();
    private Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 로그인 확인
            UsersVo loginUser = checkUserAuthority(req);
            
            // 게시글 번호 파라미터 받기
            String boardNoStr = req.getParameter("boardNo");
            if (boardNoStr == null || boardNoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("게시글 번호가 필요합니다.");
            }
            
            int boardNo = Integer.parseInt(boardNoStr);
            
            // 게시글 정보 조회
            BookBoardVo board = boardService.selectBoardDetail(boardNo);
            
            if (board == null) {
                throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
            }
            
            // 공지사항인 경우 관리자 권한 확인
            if (board.getCodeNo() == 4 || board.getCodeNo() == 5) {
                String userRole = loginUser.getRole();
                if (!"ADMIN".equals(userRole)) {
                    throw new IllegalStateException("관리자만 수정할 수 있습니다.");
                }
            } else {
                // 작성자 확인 (본인 글만 수정 가능)
                if (board.getUserNo() != loginUser.getUserNo()) {
                    throw new IllegalStateException("자신의 게시글만 수정할 수 있습니다.");
                }
            }
            
            // 게시판 코드 목록 조회
            List<BookBoardCodeVo> codeList = boardService.getCodeList();
            
            // 뷰로 전달할 데이터 설정
            req.setAttribute("board", board);
            req.setAttribute("codeList", codeList);
            req.setAttribute("mode", "update"); // 수정 모드 설정
            req.setAttribute("breadcrumbTitle", "수정하기");
            
            // 에디터 페이지로 포워딩
            req.getRequestDispatcher("/WEB-INF/view/editor.jsp").forward(req, resp);
            
        } catch (IllegalStateException e) {
            // 로그인이 필요한 경우 로그인 페이지로 리다이렉트
            resp.sendRedirect(req.getContextPath() + "/user/login.do");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 게시글 번호입니다.");
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
            String boardNoStr = req.getParameter("boardNo");
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String codeNoStr = req.getParameter("codeNo");
            
            // 유효성 검사
            if (boardNoStr == null || boardNoStr.trim().isEmpty() ||
                title == null || title.trim().isEmpty() || 
                content == null || content.trim().isEmpty() ||
                codeNoStr == null || codeNoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("필수 항목이 누락되었습니다.");
            }
            
            int boardNo = Integer.parseInt(boardNoStr);
            int codeNo = Integer.parseInt(codeNoStr);
            
            // 게시글 정보 조회
            BookBoardVo originalBoard = boardService.selectBoardDetail(boardNo);
            
            if (originalBoard == null) {
                throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
            }
            
            // 권한 확인
            String userRole = loginUser.getRole();
            boolean isAdmin = "ADMIN".equals(userRole);
            
            // 공지사항 관련 권한 체크 (기존이 공지사항이거나 수정 후 공지사항이 될 경우 + 교육행사 게시판)
            if (originalBoard.getCodeNo() == 4 || codeNo == 4  || codeNo == 5) {
                if (!isAdmin) {
                    throw new IllegalStateException("관리자만 수정할 수 있습니다.");
                }
            } else {
                // 일반 게시판인 경우 작성자 확인
                if (originalBoard.getUserNo() != loginUser.getUserNo()) {
                    throw new IllegalStateException("자신의 게시글만 수정할 수 있습니다.");
                }
            }
            
            // 게시글 정보 업데이트
            BookBoardVo board = new BookBoardVo();
            board.setBoardNo(boardNo);
            board.setTitle(title);
            board.setContent(content);
            board.setCodeNo(codeNo);
            board.setUserNo(loginUser.getUserNo());
            
            // 파일그룹 번호는 게시글 번호와 동일하게 설정
            board.setFileGroupNum(boardNo);
            
            
            
            // 파일 업로드 처리 (새 파일이 있는 경우)
            boolean hasNewFiles = false;
            for (Part part : req.getParts()) {
                String fileName = part.getSubmittedFileName();
                if (fileName != null && !fileName.isEmpty()) {
                    hasNewFiles = true;
                    break;
                }
            }
            
            // 파일 처리
            if (hasNewFiles) {
                // 파일 업로드 - 게시글 번호를 파일그룹 번호로 사용
                List<File_StorageVo> uploadedFiles = fileService.uploadFiles(req, "BOARD", boardNo);
            }  
            
            if (originalBoard.getCodeNo() != codeNo){
            	// 게시판 이동시 파일그룹의 코드 번호 수정
                FileGroupVo fileGroup = fileService.selectFileGroup(boardNo);
                fileGroup.setFileGroupNum(boardNo);
                fileGroup.setCodeNo(codeNo);
                boolean result = fileService.updateFileGroupCodeNo(fileGroup);
                if (!result) {
                	throw new RuntimeException("파일 게시판 이동 처리 실패");
                }
            }
            
            // 게시글 업데이트
            boolean result = boardService.updateBoard(board) > 0;
            
            if (!result) {
                throw new RuntimeException("게시글 수정에 실패했습니다.");
            }
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "게시글이 수정되었습니다.");
            response.put("boardNo", boardNo);
            System.out.println("수정 요청 수신: boardNo=" + boardNo + ", codeNo=" + codeNo);
            
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