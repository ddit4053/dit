package kr.or.ddit.books.reviews;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		String ratingStr = req.getParameter("rating");
		int rating = Integer.parseInt(ratingStr);
		System.out.println("평점은 : "+rating);
		
		String revContent = req.getParameter("revContent");
		System.out.println("내용은 : "+revContent);
		
		String bookNoStr = req.getParameter("bookNo");
		int bookNo= Integer.parseInt(bookNoStr);
		
		System.out.println("책번호는 : "+bookNo);
		
		ReviewsVo reviewsVo = new ReviewsVo();
		
		reviewsVo.setRating(rating);
		reviewsVo.setRevContent(revContent);
		reviewsVo.setBookNo(bookNo);
		reviewsVo.setUserNo(1); // 일단 그냥 넣은 데이터
		
		
		
		reviewsService.reviewInsert(reviewsVo);
		
	}
}
