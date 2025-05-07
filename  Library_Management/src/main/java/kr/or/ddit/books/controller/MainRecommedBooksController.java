package kr.or.ddit.books.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.BooksVo;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

/**
 * Servlet implementation class MainNewBooksController
 */
@WebServlet("/api/books/recommended")
public class MainRecommedBooksController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IBooksService booksService = BooksServiceImp.getInsatance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainRecommedBooksController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않음
		
	       response.setContentType("application/json; charset=UTF-8");
   	
	        if (session == null || session.getAttribute("userNo") == null) {
	        	response.sendRedirect(request.getContextPath() + "/user/login.do");
	            return;
	        }
			int userNo = (int) request.getSession().getAttribute("userNo");
			
			List<Integer> favoriteGenres = booksService.getFavoriteGenres(userNo);
			
			List<BooksVo> recommendedBooks = booksService.getRecommendedBooks(favoriteGenres, userNo);
	        Gson gson = new Gson();
	        String json = gson.toJson(recommendedBooks);

	        response.getWriter().write(json);
	}


}
