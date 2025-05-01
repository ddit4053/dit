package kr.or.ddit.books.reviews;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.books.service.IReviewsService;
import kr.or.ddit.books.service.ReviewsServiceImpl;
import kr.or.ddit.vo.ReviewsVo;

@WebServlet("/books/detail/reviewInsert")
public class ReviewInsertController extends HttpServlet {
	
	IReviewsService reviewsService = ReviewsServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		String ratingStr = req.getParameter("rating");
		int rating = Integer.parseInt(ratingStr);
		
		String revContent = req.getParameter("revContent");

		String bookNoStr = req.getParameter("bookNo");
		int bookNo= Integer.parseInt(bookNoStr);
		

		
		ReviewsVo reviewsVo = new ReviewsVo();
		
		reviewsVo.setRating(rating);
		reviewsVo.setRevContent(revContent);
		reviewsVo.setBookNo(bookNo);
		int userNo = (int) session.getAttribute("userNo");
		reviewsVo.setUserNo(userNo); // 일단 그냥 넣은 데이터
		
		
		
		reviewsService.reviewInsert(reviewsVo);
		
	}
}
