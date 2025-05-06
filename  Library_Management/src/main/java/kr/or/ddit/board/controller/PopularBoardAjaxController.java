package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.vo.BookBoardVo;

@WebServlet("/popularBoardAjax")
public class PopularBoardAjaxController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private IBoardService boardService = BoardServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            // 파라미터 받기
            String codeNoStr = req.getParameter("codeNo");
            String sortType = req.getParameter("sortType");
            String limitStr = req.getParameter("limit");
            
            // 기본값 설정
            int codeNo = codeNoStr != null ? Integer.parseInt(codeNoStr) : 1; // 기본값 독후감
            int limit = limitStr != null ? Integer.parseInt(limitStr) : 5; // 기본 5개
            
            if (sortType == null || sortType.isEmpty()) {
                sortType = "latest"; // 기본값 최신순
            }
            
            // 서비스 호출을 위한 파라미터 설정
            Map<String, Object> params = new HashMap<>();
            params.put("codeNo", codeNo);
            params.put("sortType", sortType);
            params.put("limit", limit);
            
            // 서비스 호출 (인기 게시글 가져오기)
            List<BookBoardVo> boardList = boardService.selectPopularBoardList(params);
            
            // JSON 직렬화
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
            String jsonString = gson.toJson(boardList);
            
            PrintWriter out = resp.getWriter();
            out.print(jsonString);
            out.flush();
            
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "인기 게시글을 불러오는데 실패했습니다.");
            errorResponse.put("message", e.getMessage());
            
            Gson gson = new Gson();
            PrintWriter out = resp.getWriter();
            out.print(gson.toJson(errorResponse));
            out.flush();
        }
    }
}