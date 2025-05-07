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
import kr.or.ddit.vo.PagingVo;

@WebServlet(urlPatterns = {
		"/reviewsListAjax",
		"/discussionsListAjax",
		"/recommendationsListAjax",
		"/noticesListAjax",
		"/qaListAjax"
})
// 게시판 목록 데이터 AJAX 처리
public class BoardListAjaxController extends HttpServlet {
	
	private static final long serialVersionUTD = 1L;
	private IBoardService boardService = BoardServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		try {
			// 파라미터 받기
			String pageStr = req.getParameter("page");
			String searchType = req.getParameter("searchType");
			String searchKeyword = req.getParameter("searchKeyword");
            String hideNoticeStr = req.getParameter("hideNotice");
            String orderType = req.getParameter("orderType");
            String blockSizeStr = req.getParameter("blockSize");
            
            // 기본값 설정
            int currentPage = pageStr != null ? Integer.parseInt(pageStr) : 1;
            boolean hideNotice = "true".equals(hideNoticeStr);
            int pageSize = blockSizeStr != null ? Integer.parseInt(blockSizeStr) : 10;
			
            // 정렬 타입 변환 (최신순, 과거순, 조회수순, 댓글순)
            String sortType = "latest"; // 기본값
            if("latest".equals(orderType)) {
            	sortType = "latest";
            } else if ("oldest".equals(orderType)) {
            	sortType = "oldest";
            } else if ("views".equals(orderType)) {
            	sortType = "views";
            } else if ("comments".equals(orderType)) {
                sortType = "comments";
            }
            
            // URL 패턴에서 게시판 타입 판별
            String requestURI = req.getRequestURI();
            int codeNo = 1; // 기본값 독후감 
            
            if (requestURI.contains("discussionsListAjax"))  codeNo = 2;  // 토론
            if (requestURI.contains("recommendationsListAjax"))   codeNo = 3;  // 회원도서추천
            if (requestURI.contains("noticesListAjax")) 		codeNo = 4;  // 공지사항
            if (requestURI.contains("qaListAjax")) 			codeNo = 6;  // QA
            
            // 검색 조건 설정
            Map<String, Object> params = new HashMap<>();
            params.put("currentPage", currentPage);
            params.put("pageSize", pageSize);
            params.put("sortType", sortType);
            params.put("codeNo", codeNo);
            
            // 검색 조건이 존재할 경우
            if(searchType != null && !searchType.isEmpty() && 
               searchKeyword != null && !searchKeyword.isEmpty()) {
               // searchType이 "both"인 경우 "titleContent"로 변환
               if ("both".equals(searchType)) {
                   searchType = "titleContent";
               }
               params.put("searchType", searchType);
               params.put("searchKeyword", searchKeyword);
            }
            
            // 서비스 호출
            Map<String, Object> result = boardService.selectBoardList(params);
            @SuppressWarnings("unchecked")
			List<BookBoardVo> boardList = (List<BookBoardVo>) result.get("boardList");
            @SuppressWarnings("unchecked")
			List<BookBoardVo> noticeList = (List<BookBoardVo>) result.get("noticeList");
            PagingVo paging = (PagingVo) result.get("paging");
            
            // 공지사항 숨기기 옵션 처리
            if (!hideNotice && noticeList != null && currentPage ==1) {
            	// 공지사항 게시글 최상단 표시
            	boardList.addAll(0, noticeList);
            }
            // JSON 응답 생성
            Map<String, Object> jsonResp = new HashMap<>();
            jsonResp.put("boardList", boardList);
            jsonResp.put("paging", paging);
            
            // 직렬화 전에 boardList의 각 항목을 확인
            if (boardList != null && !boardList.isEmpty()) {
                System.out.println("첫 번째 게시글 정보:");
                BookBoardVo firstBoard = boardList.get(0);
                System.out.println("boardNo: " + firstBoard.getBoardNo());
                System.out.println("title: " + firstBoard.getTitle());
                System.out.println("writtenDate: " + firstBoard.getWrittenDate()); // 이 값이 null인지 확인
            }
            
            // 직렬화
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
            String jsonString = gson.toJson(jsonResp);
            
            PrintWriter out = resp.getWriter();
            out.print(jsonString);
            out.flush();
            
		} catch (Exception e) {
			e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "게시글 목록을 불러오는데 실패했습니다.");
            errorResponse.put("message", e.getMessage());
            
            Gson gson = new Gson();
            PrintWriter out = resp.getWriter();
            out.print(gson.toJson(errorResponse));
            out.flush();
		}
	}
}
