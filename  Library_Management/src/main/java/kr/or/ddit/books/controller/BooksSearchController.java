package kr.or.ddit.books.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.BooksVo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Servlet implementation class BooksSearchController
 */
@WebServlet("/books/search/result")
public class BooksSearchController extends HttpServlet {
	
	IBooksService booksService = BooksServiceImp.getInsatance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//자료검색에 들어가서 검색할시
		String searchType =  req.getParameter("searchType");
		String keyword = req.getParameter("keyword");
		String year =req.getParameter("year");
		String pageParam = req.getParameter("page");
		
		//System.out.println(searchType);
		int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
	    int pageSize = 3;
	    int offset = (currentPage - 1) * pageSize;
	    
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("keyword",keyword);
		map.put("pubdate", year);
		
		String categoryIdStr = req.getParameter("selectedCategoryId");

		 int categoryId = Integer.parseInt(categoryIdStr);
		 map.put("categoryId", categoryId);
		
		
	    map.put("limit", pageSize);
	    map.put("offset", offset);
	    
	    Map<String, Object> countmap = new HashMap<String, Object>();
	    countmap.put("searchType", searchType);
	    countmap.put("keyword",keyword);
	    countmap.put("pubdate", year);
	    countmap.put("categoryId", categoryId);
		
		//System.out.println(map.get("searchType"));
		//System.out.println(map.get("keyword"));
		//System.out.println(map.get("pubdate"));
		
		List<BooksVo> SearchBookList = null;
		String query = req.getParameter("query"); // 메인화면에서 검색
		System.out.println(query);
		if(query != null) {
			Map<String, Object> mapMain = new HashMap<String, Object>();
			mapMain.put("query", query);
			mapMain.put("limit", pageSize);
		    mapMain.put("offset", offset);
		    
		    
			SearchBookList = booksService.mainSearchBookList(mapMain);
			System.out.println(SearchBookList);
		    int totalCount = booksService.countMainSearchBook(mapMain);
		    System.out.println(totalCount);
		    
		    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
			
			req.setAttribute("SearchBookList", SearchBookList);
			req.setAttribute("currentPage", currentPage);
		    req.setAttribute("totalPages", totalPages);
			
		}
		else {
			
			SearchBookList = booksService.searchBookList(map);	//검색한 책리스트 limit와 offset포함
			
			int totalCount = booksService.countSearchBook(countmap); //검색한책 갯수
			
			int totalPages = (int) Math.ceil((double) totalCount / pageSize);
			
			req.setAttribute("SearchBookList", SearchBookList);
			req.setAttribute("currentPage", currentPage);
		    req.setAttribute("totalPages", totalPages);
		}
		
		//System.out.println(SearchBookList);
		
//		Gson gson = new Gson();
//		
//		
//		resp.setContentType("application/json;charset=UTF-8");
//        resp.getWriter().write(gson.toJson(SearchBookList));
        
		
		req.setAttribute("contentPage", "search_result.jsp");
		
		ServletContext ctx = req.getServletContext();
		ctx.getRequestDispatcher("/WEB-INF/view/users/book_search/books.jsp").forward(req, resp);
	}

}
