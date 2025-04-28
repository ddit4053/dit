package kr.or.ddit.books.reviews;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;

@WebServlet("/books/detail/reviewInsert")
public class ReviewInsertController extends HttpServlet {
	IBooksService booksService = BooksServiceImp.getInsatance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ratingStr = req.getParameter("rating");
		int rating = Integer.parseInt(ratingStr);
		
		String revContent = req.getParameter("reviewContent");
		
		String bookNoStr = req.getParameter("bookNo");
		int bookNo= Integer.parseInt(bookNoStr);
		
		
		
	}
}
