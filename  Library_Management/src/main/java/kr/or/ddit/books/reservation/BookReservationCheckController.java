package kr.or.ddit.books.reservation;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BookReservationServiceImpl;
import kr.or.ddit.books.service.IBookReservationService;

@WebServlet("/books/detail/reserCheck")
public class BookReservationCheckController extends HttpServlet {
	IBookReservationService bookReservationService = BookReservationServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String bookNoStr =	req.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookNoStr);
		
		int reservationcheck = bookReservationService.reservationcheck(bookNo);
		
		if(reservationcheck == 0) {
			String res = "reservation";
		    resp.setContentType("text/plain;charset=UTF-8");
			resp.getWriter().write(res);
		}
	}
}
