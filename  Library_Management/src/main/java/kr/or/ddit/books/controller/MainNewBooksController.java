package kr.or.ddit.books.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.BooksVo;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

/**
 * Servlet implementation class MainNewBooksController
 */
@WebServlet("/api/books/new")
public class MainNewBooksController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IBooksService booksService = BooksServiceImp.getInsatance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainNewBooksController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	       response.setContentType("application/json; charset=UTF-8");

	        List<BooksVo> newBookList = booksService.newBookList();  // 최근 등록된 도서 목록
	        Gson gson = new Gson();
	        String json = gson.toJson(newBookList);

	        response.getWriter().write(json);
	}


}
