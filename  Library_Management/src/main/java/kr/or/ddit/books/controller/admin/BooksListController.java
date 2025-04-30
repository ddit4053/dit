package kr.or.ddit.books.controller.admin;

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
 * Servlet implementation class BooksListController
 */
@WebServlet("/admin/books/list")
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

		
		Map<String, Object> map = new HashMap<>();

		List<BooksVo> bookList = booksService.listBooks(map);
		
		request.setAttribute("contentPage", "bookList.jsp");
		request.setAttribute("bookList", bookList);
		
		ServletContext ctx = request.getServletContext();
		ctx.getRequestDispatcher("/WEB-INF/view/admin/book/book.jsp").forward(request, response);
	}



}
