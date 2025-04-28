package kr.or.ddit.books.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.ReviewsVo;

@WebServlet("/books/detail/reviewList")
public class ReviewListController extends HttpServlet{
	IBooksService booksService = BooksServiceImp.getInsatance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookBoStr = req.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookBoStr);
		
		List<ReviewsVo>reviewList =  booksService.reviewList(bookNo);
		
	}
}
