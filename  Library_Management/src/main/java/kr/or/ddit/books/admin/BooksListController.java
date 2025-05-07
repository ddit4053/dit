package kr.or.ddit.books.admin;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class BooksListController
 */
@WebServlet("/admin/books/listall")
public class BooksListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    IBooksService booksService = BooksServiceImp.getInsatance();   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BooksListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String type = request.getParameter("type");
	    int page = Integer.parseInt(request.getParameter("page")); // 페이지 번호
	    int pageSize = 5; // 한 페이지당 도서 수

	    List<BooksVo> bookList = new ArrayList<>();
	    if ("available".equals(type)) {
	        bookList = booksService.listBooks(new HashMap<>());
	    } else if ("deleted".equals(type)) {
	        bookList = booksService.getDeletedBooks();
	    }

	    // 전체 개수와 페이지 계산
	    int totalBooks = bookList.size();
	    int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

	    // 현재 페이지에 해당하는 데이터만 추출
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, totalBooks);
	    List<BooksVo> pageBooks = bookList.subList(startIndex, endIndex);

	    // 응답 데이터 구성
	    Map<String, Object> result = new HashMap<>();
	    result.put("books", pageBooks);
	    result.put("totalPages", totalPages);

	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String jsonResponse = gson.toJson(result);

	    response.setContentType("application/json");
	    response.getWriter().write(jsonResponse);
		
	}



}
