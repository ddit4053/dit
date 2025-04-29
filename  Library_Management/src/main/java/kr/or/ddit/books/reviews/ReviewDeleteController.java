package kr.or.ddit.books.reviews;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.IReviewsService;
import kr.or.ddit.books.service.ReviewsServiceImpl;

@WebServlet("/books/detail/reviewDelete")
public class ReviewDeleteController extends HttpServlet{
		IReviewsService reviewsService = ReviewsServiceImpl.getInstance();
	
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String revNoStr = req.getParameter("revNo");
			int revNo = Integer.parseInt(revNoStr);
			
			reviewsService.reviewDelete(revNo);
			
		}
}
