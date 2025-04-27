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

/**
 * Servlet implementation class BooksDetailController
 */
@WebServlet("/books/detail")
public class BooksDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	IBooksService booksService = BooksServiceImp.getInsatance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BooksDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String bookNoStr =request.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookNoStr);
		
		BooksVo bookvo = new BooksVo();
		
		bookvo.setBookNo(bookNo);
		
		bookvo = booksService.booksDetail(bookvo);
		
		request.setAttribute("contentPage", "booksDetail.jsp");
		request.setAttribute("bookDetail", bookvo);
		
		System.out.println(bookvo);
		
		ServletContext ctx = request.getServletContext();
		ctx.getRequestDispatcher("/WEB-INF/view/users/book_search/books.jsp").forward(request, response);
	}



}
