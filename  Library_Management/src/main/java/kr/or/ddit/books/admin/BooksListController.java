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
		
		Map<String, Object> map = new HashMap<>();

		List<BooksVo> bookList= new ArrayList<BooksVo>();
		
		  if ("available".equals(type)) {
	            bookList = booksService.listBooks(map);
        } else if ("deleted".equals(type)) {
	            bookList = booksService.getDeletedBooks();
        }
		
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResponse = gson.toJson(bookList);

        // JSON 응답 설정
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
		
	}



}
