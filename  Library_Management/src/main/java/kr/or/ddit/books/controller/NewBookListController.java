package kr.or.ddit.books.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.BooksVo;

@WebServlet("/books/new")
public class NewBookListController extends HttpServlet{
	IBooksService booksService = BooksServiceImp.getInsatance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<BooksVo> newBookList = booksService.newBookList();
		
		
		
		req.setAttribute("contentPage", "newBooks.jsp");
		req.setAttribute("newBookList", newBookList);
		

		ServletContext ctx = req.getServletContext();
		ctx.getRequestDispatcher("/WEB-INF/view/users/book_search/books.jsp").forward(req, resp);
	}
}
