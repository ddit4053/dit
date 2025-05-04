package kr.or.ddit.board.controller;

import java.io.BufferedReader;
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
import jakarta.servlet.http.Part;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.FileServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.service.IFileService;
import kr.or.ddit.vo.BookBoardVo;
import kr.or.ddit.vo.File_StorageVo;
import kr.or.ddit.vo.UsersVo;
@WebServlet("/delete")
public class BoardDeleteController extends HttpServlet{
    private IBoardService boardService = BoardServiceImpl.getInstance();
    private IFileService fileService = FileServiceImpl.getInstance();
    private Gson gson = new Gson();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            
            // 결과 플래그 초기화
            boolean result = false;
            
            // 트랜잭션 시작 (서비스 레이어에서 처리하는 것이 좋지만, 여기서는 컨트롤러에서 함께 처리)
            try {
            	    // 파일이 존재할 때만 파일 일괄 삭제
              
                if (board.getFileGroupNum() != null && board.getFileGroupNum() > 0) {
                    // 파일 스토리지 논리적 삭제
                    fileService.deleteFilesByGroupNum(boardNo);
                }
                // 게시글 삭제 
                result = boardService.deleteBoard(boardNo) > 0;
                
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("게시글 삭제 중 오류가 발생했습니다: " + e.getMessage());
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
