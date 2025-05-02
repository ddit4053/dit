package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.util.RequestDataChange;
import kr.or.ddit.vo.CommentsVo;
import kr.or.ddit.vo.UsersVo;

/**
 * 댓글 관련 요청을 처리하는 서블릿
 * - /comment/write: 댓글 등록
 * - /comment/delete: 댓글 삭제
 * - /comment/update: 댓글 수정
 * - /comment/reply: 답글 등록
 */
@WebServlet("/comment/*")
public class CommentsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IBoardService boardService = BoardServiceImpl.getInstance();
    private Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            if (pathInfo.equals("/delete")) {
                deleteComment(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
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
            if (pathInfo.equals("/write")) {
                writeComment(req, resp);
            } else if (pathInfo.equals("/delete")) {
                deleteComment(req, resp);
            } else if (pathInfo.equals("/update")) {
                updateComment(req, resp);
            } else if (pathInfo.equals("/reply")) {
                writeReply(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 사용자 정보 및 권한 확인을 위한 공통 메서드
     * @param req HTTP 요청
     * @param cmNo 댓글 번호 (권한 확인이 필요한 경우, 0이면 단순 로그인 확인만)
     * @param actionName 수행할 작업 이름 (예: "삭제", "수정")
     * @return 로그인한 사용자 정보
     * @throws IllegalStateException 로그인이 되어있지 않거나 권한이 없는 경우
     * @throws IllegalArgumentException 요청된 댓글이 존재하지 않는 경우
     */
    private UsersVo checkUserAuthority(HttpServletRequest req, int cmNo, String actionName) 
            throws IllegalStateException, IllegalArgumentException {
        // 세션에서 사용자 정보 확인
        HttpSession session = req.getSession();
        UsersVo loginUser = (UsersVo) session.getAttribute("user");
        
        if (loginUser == null) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        
        // 댓글 번호가 0이면 단순 로그인 확인만 수행
        if (cmNo == 0) {
            return loginUser;
        }
        
        // 댓글 정보 조회
        CommentsVo comment = boardService.selectComment(cmNo);
        
        if (comment == null) {
            throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
        }
        
        // 관리자가 아니고 작성자도 아닌 경우 권한 없음
        boolean isAdmin = "ADMIN".equals(loginUser.getRole());
        if (!isAdmin && comment.getUserNo() != loginUser.getUserNo()) {
            throw new IllegalStateException(actionName + " 권한이 없습니다.");
        }
        
        return loginUser;
    }
    
    /**
     * 댓글 등록 처리 - RequestDataChange 활용
     */
    private void writeComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 사용자 권한 확인 (단순 로그인 확인)
            UsersVo loginUser = checkUserAuthority(req, 0, null);
            
            // RequestDataChange를 사용해 JSON 데이터 추출
            String jsonData = RequestDataChange.dataChange(req);
            
            // Gson으로 CommentsVo 객체로 변환
            JsonObject requestData = gson.fromJson(jsonData, JsonObject.class);
            
            // 파라미터 검증
            if (!requestData.has("boardNo") || !requestData.has("cmContent")) {
                throw new IllegalArgumentException("필수 파라미터가 누락되었습니다.");
            }
            
            // 댓글 정보 생성
            CommentsVo comment = new CommentsVo();
            comment.setBoardNo(requestData.get("boardNo").getAsInt());
            comment.setUserNo(loginUser.getUserNo());
            comment.setCmContent(requestData.get("cmContent").getAsString());
            
            if (comment.getCmContent().trim().isEmpty()) {
                throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
            }
            
            // 댓글 저장
            int result = boardService.insertComment(comment);
            
            // 댓글 수 업데이트
            if (result > 0) {
                boardService.updateCommentCount(comment.getBoardNo());
            }
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", result > 0);
            response.put("cmNo", comment.getCmNo());
            
            if (result > 0) {
                response.put("message", "댓글이 성공적으로 등록되었습니다.");
            } else {
                response.put("message", "댓글 등록에 실패했습니다.");
            }
            
            sendJsonResponse(resp, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 댓글 삭제 처리 - RequestDataChange 활용
     */
    private void deleteComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int cmNo;
            
            // POST 요청인 경우 JSON 본문에서 파라미터 추출
            if ("POST".equalsIgnoreCase(req.getMethod())) {
                String jsonData = RequestDataChange.dataChange(req);
                JsonObject requestData = gson.fromJson(jsonData, JsonObject.class);
                
                if (!requestData.has("cmNo")) {
                    throw new IllegalArgumentException("댓글 번호가 제공되지 않았습니다.");
                }
                cmNo = requestData.get("cmNo").getAsInt();
            } else {
                // GET 요청인 경우 URL 파라미터에서 추출
                String cmNoStr = req.getParameter("cmNo");
                if (cmNoStr == null || cmNoStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("댓글 번호가 제공되지 않았습니다.");
                }
                cmNo = Integer.parseInt(cmNoStr);
            }
            
            // 사용자 권한 확인
            checkUserAuthority(req, cmNo, "삭제");
            
            // 댓글 삭제
            int result = boardService.deleteComment(cmNo);
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", result > 0);
            response.put("message", result > 0 ? "댓글이 성공적으로 삭제되었습니다." : "댓글 삭제에 실패했습니다.");
            
            sendJsonResponse(resp, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 댓글 수정 처리 - RequestDataChange 활용
     */
    private void updateComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // RequestDataChange를 사용해 JSON 데이터 추출
            String jsonData = RequestDataChange.dataChange(req);
            
            // Gson으로 JSON 객체로 변환
            JsonObject requestData = gson.fromJson(jsonData, JsonObject.class);
            
            // 파라미터 검증
            if (!requestData.has("cmNo") || !requestData.has("cmContent")) {
                throw new IllegalArgumentException("필수 파라미터가 누락되었습니다.");
            }
            
            int cmNo = requestData.get("cmNo").getAsInt();
            String cmContent = requestData.get("cmContent").getAsString();
            
            if (cmContent.trim().isEmpty()) {
                throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
            }
            
            // 사용자 권한 확인
            checkUserAuthority(req, cmNo, "수정");
            
            // 댓글 정보 조회
            CommentsVo comment = boardService.selectComment(cmNo);
            
            // 댓글 업데이트
            comment.setCmContent(cmContent);
            int result = boardService.updateComment(comment);
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", result > 0);
            response.put("message", result > 0 ? "댓글이 성공적으로 수정되었습니다." : "댓글 수정에 실패했습니다.");
            
            sendJsonResponse(resp, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 대댓글 등록
     */
    private void writeReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 사용자 권한 확인 (단순 로그인 확인)
            UsersVo loginUser = checkUserAuthority(req, 0, null);
            
            // RequestDataChange를 사용해 JSON 데이터 추출
            String jsonData = RequestDataChange.dataChange(req);
            
            // Gson으로 JSON 객체로 변환
            JsonObject requestData = gson.fromJson(jsonData, JsonObject.class);
            
            // 파라미터 검증
            if (!requestData.has("boardNo") || !requestData.has("cmNo2") || !requestData.has("cmContent")) {
                throw new IllegalArgumentException("필수 파라미터가 누락되었습니다.");
            }
            
            int boardNo = requestData.get("boardNo").getAsInt();
            int cmNo2 = requestData.get("cmNo2").getAsInt();
            String cmContent = requestData.get("cmContent").getAsString();
            
            if (cmContent.trim().isEmpty()) {
                throw new IllegalArgumentException("답글 내용을 입력해주세요.");
            }
            
            // 부모 댓글(cmNo2)이 존재하는지 확인
            CommentsVo parentComment = boardService.selectComment(cmNo2);
            if (parentComment == null) {
                throw new IllegalArgumentException("답글을 달 원본 댓글이 존재하지 않습니다.");
            }
            
            // 답글 정보 생성
            CommentsVo reply = new CommentsVo();
            reply.setBoardNo(boardNo);
            reply.setCmNo2(cmNo2);  // 부모 댓글 번호 설정
            reply.setUserNo(loginUser.getUserNo());
            reply.setCmContent(cmContent);
            
            // 답글 저장
            int result = boardService.insertReplyComment(reply);
            
            // 댓글 수 업데이트
            if (result > 0) {
                boardService.updateCommentCount(boardNo);
            }
            
            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", result > 0);
            response.put("cmNo", reply.getCmNo());
            
            if (result > 0) {
                response.put("message", "답글이 성공적으로 등록되었습니다.");
            } else {
                response.put("message", "답글 등록에 실패했습니다.");
            }
            
            sendJsonResponse(resp, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
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