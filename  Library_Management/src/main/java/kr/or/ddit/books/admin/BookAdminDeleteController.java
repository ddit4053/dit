package kr.or.ddit.books.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;

@WebServlet("/admin/books/delete")
public class BookAdminDeleteController extends HttpServlet {
	
	IBooksService booksService = BooksServiceImp.getInsatance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int bookNo = Integer.parseInt(req.getParameter("bookNo"));
		
		boolean result = booksService.deleteBook(bookNo);
		
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().write(String.valueOf(result));
	}
}
