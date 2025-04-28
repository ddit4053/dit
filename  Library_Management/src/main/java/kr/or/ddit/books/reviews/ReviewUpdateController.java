package kr.or.ddit.books.reviews;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.IReviewsService;
import kr.or.ddit.books.service.ReviewsServiceImpl;
import kr.or.ddit.vo.ReviewsVo;

@WebServlet("/books/detail/reviewUpdate")
public class ReviewUpdateController extends HttpServlet {
	IReviewsService reviewsService = ReviewsServiceImpl.getInstance();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String revNoStr = req.getParameter("revNo");
		int revNo = Integer.parseInt(revNoStr);
		
		String ratingStr = req.getParameter("rating");
		int rating = Integer.parseInt(ratingStr);
		
		String revContent = req.getParameter("revContent");
		
		ReviewsVo reviewsVo = new ReviewsVo();
		reviewsVo.setRevNo(revNo);
		reviewsVo.setRating(rating);
		reviewsVo.setRevContent(revContent);
		
		reviewsService.reviewUpdate(reviewsVo);
	}
}
