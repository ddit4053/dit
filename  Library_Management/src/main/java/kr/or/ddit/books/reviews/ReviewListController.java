package kr.or.ddit.books.reviews;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

@WebServlet("/books/detail/reviewList")
public class ReviewListController extends HttpServlet{
	IBooksService booksService = BooksServiceImp.getInsatance();
	IReviewsService reviewsService = ReviewsServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookBoStr = req.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookBoStr);
		
		List<ReviewsVo>reviewList =  reviewsService.reviewList(bookNo);
		
		Gson gson = new GsonBuilder().create();
		
		
		resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(reviewList));
		
	}
}
