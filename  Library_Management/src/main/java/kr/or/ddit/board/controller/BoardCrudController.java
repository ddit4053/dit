package kr.or.ddit.board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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
import kr.or.ddit.vo.BookBoardVo;
import kr.or.ddit.vo.File_StorageVo;
import kr.or.ddit.vo.UsersVo;

/**
 * 게시글 CRUD 작업을 처리하는 서블릿
 * - /board/delete: 게시글 삭제
 * - /board/write: 게시글 등록
 * - /board/update: 게시글 수정
 */
@WebServlet("/board/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class BoardCrudController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IBoardService boardService = BoardServiceImpl.getInstance();
    private IFileService fileService = FileServiceImpl.getInstance();
    private Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            // URL에 따라 적절한 메서드 호출
            if (pathInfo.equals("/delete")) {
                deleteBoard(req, resp);
            } else if (pathInfo.equals("/write")) {
                writeBoard(req, resp);
            } else if (pathInfo.equals("/update")) {
                updateBoard(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 게시글 삭제 처리
     */
    private void deleteBoard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // JSON 요청 본문 읽기
            JsonObject requestData = readJsonRequest(req);
            
            // 게시글 번호 파라미터 가져오기
            if (!requestData.has("boardNo")) {
                throw new IllegalArgumentException("게시글 번호가 제공되지 않았습니다.");
            }
            
            int boardNo = requestData.get("boardNo").getAsInt();
            
            // 세션에서 사용자 정보 확인 (권한 체크)
            HttpSession session = req.getSession();
            UsersVo loginUser = (UsersVo) session.getAttribute("user");
            
            if (loginUser == null) {
                throw new IllegalStateException("로그인이 필요한 기능입니다.");
            }
            
            // 게시글 작성자 확인
            BookBoardVo board = boardService.selectBoardDetail(boardNo);
            if (board == null) {
                throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
            }
            
            // 관리자가 아니고 작성자도 아닌 경우 권한 없음
            boolean isAdmin = "ADMIN".equals(loginUser.getRole());
            if (!isAdmin && board.getUserNo() != loginUser.getUserNo()) {
                throw new IllegalStateException("삭제 권한이 없습니다.");
            }
            
            // 게시글 삭제
            boolean result = boardService.deleteBoard(boardNo) > 0;
            
            // 파일 그룹이 있는 경우 파일도 삭제
            if (result && board.getFileGroupNum() > 0) {
                fileService.deleteFilesByGroupNum(board.getFileGroupNum());
            }
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", result);
            response.put("message", result ? "게시글이 성공적으로 삭제되었습니다." : "게시글 삭제에 실패했습니다.");
            
            sendJsonResponse(resp, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 게시글 등록 처리
     */
    private void writeBoard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 세션에서 사용자 정보 확인
            HttpSession session = req.getSession();
            UsersVo loginUser = (UsersVo) session.getAttribute("user");
            
            if (loginUser == null) {
                throw new IllegalStateException("로그인이 필요한 기능입니다.");
            }
            
            // 요청 파라미터 검증
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String codeNoStr = req.getParameter("codeNo");
            
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("제목을 입력해주세요.");
            }
            
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용을 입력해주세요.");
            }
            
            if (codeNoStr == null || codeNoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("게시판 분류를 선택해주세요.");
            }
            
            int codeNo = Integer.parseInt(codeNoStr);
            
            // 파일 그룹 생성 (첨부파일이 있을 경우)
            int fileGroupNum = 0;
            boolean hasAttachments = req.getPart("file") != null && req.getPart("file").getSize() > 0;
            
            if (hasAttachments) {
                fileGroupNum = fileService.createFileGroup();
            }
            
            // 게시글 정보 생성
            BookBoardVo board = new BookBoardVo();
            board.setTitle(title);
            board.setContent(content);
            board.setUserNo(loginUser.getUserNo());
            board.setCodeNo(codeNo);
            board.setFileGroupNum(fileGroupNum);
            
            // 게시글 저장
            int boardNo = boardService.insertBoard(board);
            
            // 파일 업로드 처리 (첨부파일이 있을 경우)
            List<File_StorageVo> uploadedFiles = null;
            if (hasAttachments && boardNo > 0 && fileGroupNum > 0) {
                uploadedFiles = fileService.uploadFiles(req, "BOARD", boardNo);
            }
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", boardNo > 0);
            response.put("boardNo", boardNo);
            
            if (boardNo > 0) {
                response.put("message", "게시글이 성공적으로 등록되었습니다.");
                if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
                    response.put("fileCount", uploadedFiles.size());
                }
            } else {
                response.put("message", "게시글 등록에 실패했습니다.");
            }
            
            sendJsonResponse(resp, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 게시글 수정 처리
     */
    private void updateBoard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 세션에서 사용자 정보 확인
            HttpSession session = req.getSession();
            UsersVo loginUser = (UsersVo) session.getAttribute("user");
            
            if (loginUser == null) {
                throw new IllegalStateException("로그인이 필요한 기능입니다.");
            }
            
            // 요청 파라미터 검증
            String boardNoStr = req.getParameter("boardNo");
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String codeNoStr = req.getParameter("codeNo");
            String fileGroupNumStr = req.getParameter("fileGroupNum");
            
            if (boardNoStr == null || boardNoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("게시글 번호가 제공되지 않았습니다.");
            }
            
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("제목을 입력해주세요.");
            }
            
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용을 입력해주세요.");
            }
            
            if (codeNoStr == null || codeNoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("게시판 분류를 선택해주세요.");
            }
            
            int boardNo = Integer.parseInt(boardNoStr);
            int codeNo = Integer.parseInt(codeNoStr);
            // 기존에 첨부한 파일 그룹넘버가 있을 경우 가져옴
            int fileGroupNum = fileGroupNumStr != null && !fileGroupNumStr.trim().isEmpty() ? 
                    Integer.parseInt(fileGroupNumStr) : 0;
            
            // 게시글 존재 및 권한 확인
            BookBoardVo existingBoard = boardService.selectBoardDetail(boardNo);
            if (existingBoard == null) {
                throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
            }
            
            boolean isAdmin = "ADMIN".equals(loginUser.getRole());
            if (!isAdmin && existingBoard.getUserNo() != loginUser.getUserNo()) {
                throw new IllegalStateException("수정 권한이 없습니다.");
            }
            
            // 파일 그룹이 없고, 첨부파일이 있는 경우 파일 그룹 생성
            boolean hasAttachments = req.getPart("file") != null && req.getPart("file").getSize() > 0;
            if (fileGroupNum == 0 && hasAttachments) {
                fileGroupNum = fileService.createFileGroup();
            }
            
            // 게시글 정보 업데이트
            BookBoardVo board = new BookBoardVo();
            board.setBoardNo(boardNo);
            board.setTitle(title);
            board.setContent(content);
            board.setCodeNo(codeNo);
            board.setFileGroupNum(fileGroupNum);
            
            // 게시글 수정
            boolean result = boardService.updateBoard(board) > 0;
            
            // 파일 업로드 처리 (첨부파일이 있을 경우)
            List<File_StorageVo> uploadedFiles = null;
            if (hasAttachments && fileGroupNum > 0) {
                uploadedFiles = fileService.uploadFiles(req, "BOARD", boardNo);
            }
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", result);
            
            if (result) {
                response.put("message", "게시글이 성공적으로 수정되었습니다.");
                if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
                    response.put("fileCount", uploadedFiles.size());
                }
            } else {
                response.put("message", "게시글 수정에 실패했습니다.");
            }
            
            sendJsonResponse(resp, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * JSON 요청 본문 읽기
     */
    private JsonObject readJsonRequest(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        return gson.fromJson(sb.toString(), JsonObject.class);
    }
    
    /**
     * JSON 응답 전송
     */
    private void sendJsonResponse(HttpServletResponse resp, Map<String, Object> data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(data));
        out.flush();
    }
    
    /**
     * 에러 응답 전송
     */
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