package kr.or.ddit.books.controller;

import jakarta.servlet.ServletContext;
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

/**
 * Servlet implementation class BookRecommendController
 */
@WebServlet("/books/recommend")
public class BookRecommendController extends HttpServlet {
	IBooksService booksService = BooksServiceImp.getInsatance(); 
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookRecommendController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않음
    	
        if (session == null || session.getAttribute("userNo") == null) {
        	response.sendRedirect(request.getContextPath() + "/user/login.do");
            return;
        }
		int userNo = (int) request.getSession().getAttribute("userNo");
		
		List<Integer> favoriteGenres = booksService.getFavoriteGenres(userNo);
		
		List<BooksVo> recommendedBooks = booksService.getRecommendedBooks(favoriteGenres, userNo);
		System.out.println(recommendedBooks);
		request.setAttribute("recommendedBooks", recommendedBooks);
		request.setAttribute("contentPage", "bookRecommend.jsp");

		ServletContext ctx = request.getServletContext();
		ctx.getRequestDispatcher("/WEB-INF/view/users/book_search/books.jsp").forward(request, response);
	}



}
